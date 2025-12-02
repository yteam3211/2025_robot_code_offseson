package frc.robot.subsystems.elevatorsim;

import static edu.wpi.first.units.Units.Centimeter;
import static edu.wpi.first.units.Units.Meter;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Millimeter;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.robot.subsystems.elvetor.elvetorconstants.MotorCurrentLimits;
import java.util.function.DoubleSupplier;

public class elevatorIOsim implements elevatorIO {
  private final DCMotor elevatorMotor = DCMotor.getKrakenX60(1);
  private ElevatorSim elevatorSim =
      new ElevatorSim(
          LinearSystemId.createElevatorSystem(
              elevatorMotor,
              17636.98 / 1000,
              Millimeter.of(elvetorconstants.DRUM_RADIOS).in(Meter),
              elvetorconstants.GEAR_RATIO),
          elevatorMotor,
          0,
          2.7,
          true,
          0);
  private MotionMagicVoltage motionMagicVoltage = new MotionMagicVoltage(0);
  private TalonFX m_master = new TalonFX(elvetorconstants.masterid);
  Mechanism2d mec = new Mechanism2d(4, 4);

  MechanismRoot2d root = mec.getRoot("elevaotr", 2, 0);
  MechanismLigament2d ligament2d =
      new MechanismLigament2d("elevaotr", 2, 0, 0, new Color8Bit(Color.kWhite));

  public elevatorIOsim() {
    root.append(ligament2d);
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
    m_master.setPosition(0);
  }

  @Override
  public void updateinputs(elevatorInputs inputs) {
    inputs.height = Meter.of(elevatorSim.getPositionMeters());
    inputs.speed = MetersPerSecond.of(elevatorSim.getVelocityMetersPerSecond());
    if (elevatorSim.getPositionMeters() * 100 < 5) {
      inputs.is_close = true;
    } else {
      inputs.is_close = false;
    }
  }

  double postorot = elvetorconstants.POSITION_CONVERSION_FACTOR;

  @Override
  public void updateElevator() {
    elevatorSim.setInput(m_master.getSimState().getMotorVoltage());
    elevatorSim.update(0.02);

    m_master
        .getSimState()
        .setRawRotorPosition(Meters.of(elevatorSim.getPositionMeters()).in(Centimeter) * postorot);
    m_master
        .getSimState()
        .setRotorVelocity(
            Meters.of(elevatorSim.getVelocityMetersPerSecond()).in(Centimeter) * postorot);
    RoboRioSim.setVInVoltage(
        BatterySim.calculateDefaultBatteryLoadedVoltage(elevatorSim.getCurrentDrawAmps()));
    ligament2d.setLength(elevatorSim.getPositionMeters() * 100);
    SmartDashboard.putData("mec", mec);
  }

  @Override
  public void setheight(DoubleSupplier height) {
    m_master.setControl(motionMagicVoltage.withPosition(height.getAsDouble()));
  }

  @Override
  public void setSpeed(DoubleSupplier speed) {
    elevatorSim.setInputVoltage(speed.getAsDouble() * RobotController.getBatteryVoltage());
  }

  @Override
  public void setMotorHeight(double height) {
    elevatorSim.setState(height, 0);
  }
}
