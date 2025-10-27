package frc.robot.subsystems.elevatorsim;

import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.sim.TalonFXSimState;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import frc.robot.subsystems.elvetor.elvetorconstants;
import frc.robot.subsystems.elvetor.elvetorconstants.MotorCurrentLimits;
import java.util.function.DoubleSupplier;

public class elevatorIOsim implements elevatorIO {
  private final DCMotor elevatorMotor = DCMotor.getKrakenX60(2);
  private final DCMotorSim dcMotorSim =
      new DCMotorSim(
          LinearSystemId.createElevatorSystem(
              elevatorMotor,
              17636.98 / 1000,
              elvetorconstants.DRUM_RADIOS,
              elvetorconstants.GEAR_RATIO),
          elevatorMotor);
  private TalonFX m_master = new TalonFX(elvetorconstants.masterid);

  private TalonFXSimState m_master_sim;
  private MotionMagicVoltage motionMagicVoltage = new MotionMagicVoltage(0);

  public elevatorIOsim() {

    TalonFXConfiguration talonFXConfiguration = new TalonFXConfiguration();

    FeedbackConfigs feedbackConfigs = talonFXConfiguration.Feedback;
    feedbackConfigs.FeedbackSensorSource = elvetorconstants.SensorSource;
    feedbackConfigs.SensorToMechanismRatio = elvetorconstants.POSITION_CONVERSION_FACTOR;

    MotorOutputConfigs motorOutputConfigs = talonFXConfiguration.MotorOutput;
    motorOutputConfigs.NeutralMode = elvetorconstants.NeutralMode;

    CurrentLimitsConfigs limitConfigs = talonFXConfiguration.CurrentLimits;
    limitConfigs.SupplyCurrentLimit = MotorCurrentLimits.SUPPLY_CURRENT_LIMIT;
    limitConfigs.SupplyCurrentLowerLimit = MotorCurrentLimits.SUPPLY_CURRENT_LOWER_LIMIT;
    limitConfigs.SupplyCurrentLimitEnable = MotorCurrentLimits.SUPPLY_CURRENT_LIMIT_ENABLE;

    MotionMagicConfigs motionMagicConfigs = talonFXConfiguration.MotionMagic;
    motionMagicConfigs.MotionMagicCruiseVelocity =
        elvetorconstants.MotionMagicConstants.MOTION_MAGIC_VELOCITY;
    motionMagicConfigs.MotionMagicAcceleration =
        elvetorconstants.MotionMagicConstants.MOTION_MAGIC_ACCELERATION;
    motionMagicConfigs.MotionMagicJerk = elvetorconstants.MotionMagicConstants.MOTION_MAGIC_JERK;

    Slot0Configs slot0 = talonFXConfiguration.Slot0;
    slot0.kS = elvetorconstants.MotionMagicConstants.MOTOR_KS;
    slot0.kG = elvetorconstants.MotionMagicConstants.MOTOR_KG;
    slot0.kV = elvetorconstants.MotionMagicConstants.MOTOR_KV;
    slot0.kA = elvetorconstants.MotionMagicConstants.MOTOR_KA;
    slot0.kP = elvetorconstants.MotionMagicConstants.MOTOR_KP;
    slot0.kI = elvetorconstants.MotionMagicConstants.MOTOR_KI;
    slot0.kD = elvetorconstants.MotionMagicConstants.MOTOR_KD;
    slot0.GravityType = elvetorconstants.MotionMagicConstants.GravityType;

    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 5; ++i) {
      status = m_master.getConfigurator().apply(talonFXConfiguration);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not configure device. Error: " + status.toString());
    }
  }

  @Override
  public void updateinputs(elevatorInputs inputs) {
    inputs.height = m_master.getPosition().getValueAsDouble();
    inputs.speed = m_master.get();
    if (dcMotorSim.getAngularPositionRotations() < 0.05) {
      inputs.is_close = true;
    } else {
      inputs.is_close = false;
    }
  }

  @Override
  public void updateElevator() {
    m_master_sim = m_master.getSimState();

    m_master_sim.setSupplyVoltage(RobotController.getBatteryVoltage());
    var MotorVoltage = m_master_sim.getMotorVoltageMeasure();
    dcMotorSim.setInputVoltage(MotorVoltage.in(Volts));
    dcMotorSim.update(0.02);
    m_master_sim.setRawRotorPosition(
        dcMotorSim.getAngularPosition().times(elvetorconstants.GEAR_RATIO));
    m_master_sim.setRotorVelocity(
        dcMotorSim.getAngularVelocity().times(elvetorconstants.GEAR_RATIO));
  }

  @Override
  public void setheight(DoubleSupplier height) {
    m_master.setControl(motionMagicVoltage.withPosition(height.getAsDouble()).withSlot(0));
  }

  @Override
  public void setSpeed(DoubleSupplier speed) {
    m_master.set(speed.getAsDouble());
  }
}
