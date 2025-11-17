package frc.robot.subsystems.elevatorsim;

import static edu.wpi.first.units.Units.Centimeter;
import static edu.wpi.first.units.Units.MetersPerSecond;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.subsystems.elvetor.elvetorconstants;
import frc.robot.subsystems.elvetor.elvetorconstants.MotorCurrentLimits;
import java.util.function.DoubleSupplier;

public class elevatorIOreal implements elevatorIO {
  private TalonFX motor = new TalonFX(elvetorconstants.masterid, "canv");
  private TalonFX m_slave = new TalonFX(elvetorconstants.slaveid, "canv");
  private DigitalInput m_closeSwitch =
      new DigitalInput(elvetorconstants.ELEVATOR_CLOSE_SWITCH_PORT);

  private final MotionMagicVoltage motionMagicVoltage = new MotionMagicVoltage(0);
  private boolean limitSwitchPressedAfterLestCommand = true;

  public elevatorIOreal() {
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

    m_slave.setControl(new Follower(motor.getDeviceID(), false));

    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 5; ++i) {
      status = motor.getConfigurator().apply(talonFXConfiguration);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not configure device. Error: " + status.toString());
    }
    motor.setPosition(0);
  }

  @Override
  public void updateinputs(elevatorInputs new_Inputs) {
    new_Inputs.height = Centimeter.of(motor.getPosition().getValueAsDouble());
    new_Inputs.speed = MetersPerSecond.of(motor.getVelocity().getValueAsDouble() * 100);
    new_Inputs.is_close = m_closeSwitch.get();
  }

  @Override
  public void updateElevator() {}

  @Override
  public void setheight(DoubleSupplier height) {
    motor.setControl(motionMagicVoltage.withPosition(height.getAsDouble()).withSlot(0));
  }

  @Override
  public void setMotorHeight(double height) {
    motor.setPosition(height);
  }

  @Override
  public void setSpeed(DoubleSupplier speed) {
    motor.set(speed.getAsDouble());
  }
}
