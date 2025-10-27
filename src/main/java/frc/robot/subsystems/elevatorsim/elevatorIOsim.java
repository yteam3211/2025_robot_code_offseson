package frc.robot.subsystems.elevatorsim;

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

import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import frc.robot.subsystems.elvetor.elvetorconstants;
import frc.robot.subsystems.elvetor.elvetorconstants.MotorCurrentLimits;
import java.util.function.DoubleSupplier;

public class elevatorIOsim implements elevatorIO {

  private ElevatorSim ElevatorSim;
  private final DCMotor elevatorMotor = DCMotor.getKrakenX60(2);
  //   private final DCMotorSim dcMotorSim =
  //       new DCMotorSim(LinearSystemId.createDCMotorSystem(elevatorMotor,
  //        0, 0), elevatorMotor);
  TalonFX talonFX = new TalonFX(00);

  TalonFXSimState motorSim;
  MotionMagicVoltage motionMagicVoltage = new MotionMagicVoltage(0);

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
      status = talonFX.getConfigurator().apply(talonFXConfiguration);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not configure device. Error: " + status.toString());
    }
    ElevatorSim =
        new ElevatorSim(
            elevatorMotor,
            elvetorconstants.POSITION_CONVERSION_FACTOR * 2,
            4.271 + 9.5,
            0.5,
            0,
            2.7,
            true,
            0);
    motorSim = talonFX.getSimState();
  }

  @Override
  public void updateinputs(elevatorInputs inputs) {
    inputs.height = ElevatorSim.getPositionMeters();
    inputs.speed = ElevatorSim.getVelocityMetersPerSecond();
    if (ElevatorSim.getPositionMeters() < 0.05) {
      inputs.is_close = true;
    } else {
      inputs.is_close = false;
    }
  }

  @Override
  public void updateElevator() {
    motorSim.setRawRotorPosition(
        (ElevatorSim.getPositionMeters() / 100) * elvetorconstants.POSITION_CONVERSION_FACTOR);
    ElevatorSim.setInputVoltage(motorSim.getMotorVoltage());
  }

  @Override
  public void setheight(DoubleSupplier height) {
    talonFX.setControl(motionMagicVoltage.withPosition(height.getAsDouble()).withSlot(0));
  }

  @Override
  public void setSpeed(DoubleSupplier speed) {
    talonFX.set(speed.getAsDouble());
  }
}
