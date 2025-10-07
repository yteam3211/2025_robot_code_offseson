package frc.robot.subsystems.climb;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.states.ClimbPosition;

public class ClimbSubsystem extends SubsystemBase {

  public ClimbPosition climbstate = ClimbPosition.Hold;
  private TalonFX m_crankMotor;
  private PositionDutyCycle m_positionDutyCycle;

  public ClimbSubsystem() {
    m_crankMotor = new TalonFX(ClimbConstants.CLIMB_MOTOR_ID, "canv");

    TalonFXConfiguration talonFXConfigs = new TalonFXConfiguration();
    CurrentLimitsConfigs limitConfigs = new CurrentLimitsConfigs();
    MotionMagicConfigs motionMagicConfigs = talonFXConfigs.MotionMagic;
    FeedbackConfigs feedbackConfigs = new FeedbackConfigs();
    m_positionDutyCycle = new PositionDutyCycle(0);

    Slot0Configs slot0Configs = talonFXConfigs.Slot0;

    slot0Configs.kS = slot0Configs.kV = ClimbConstants.MotionMagicConstants.MOTOR_KV;
    slot0Configs.kA = ClimbConstants.MotionMagicConstants.MOTOR_KA;

    slot0Configs.kP = ClimbConstants.MotionMagicConstants.MOTOR_KP;
    slot0Configs.kI = ClimbConstants.MotionMagicConstants.MOTOR_KI;
    slot0Configs.kD = ClimbConstants.MotionMagicConstants.MOTOR_KD;

    limitConfigs.SupplyCurrentLimit = ClimbConstants.MotorCurrentLimits.SUPPLY_CURRENT_LIMIT;
    limitConfigs.SupplyCurrentLowerLimit =
        ClimbConstants.MotorCurrentLimits.SUPPLY_CURRENT_LOWER_LIMIT;
    limitConfigs.SupplyCurrentLimitEnable =
        ClimbConstants.MotorCurrentLimits.SUPPLY_CURRENT_LIMIT_ENABLE;

    motionMagicConfigs.MotionMagicCruiseVelocity =
        ClimbConstants.MotionMagicConstants.MOTION_MAGIC_VELOCITY;
    motionMagicConfigs.MotionMagicAcceleration =
        ClimbConstants.MotionMagicConstants.MOTION_MAGIC_ACCELERATION;
    motionMagicConfigs.MotionMagicJerk = ClimbConstants.MotionMagicConstants.MOTION_MAGIC_JERK;
    talonFXConfigs.MotorOutput.NeutralMode = NeutralModeValue.Brake;

    feedbackConfigs.withSensorToMechanismRatio(ClimbConstants.POSITION_CONVERSION_FACTOR);

    m_crankMotor.getConfigurator().apply(talonFXConfigs);
    this.setDefaultCommand(Commands.run(() -> setOutput(0.0), this));
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("climb voltage", m_crankMotor.getMotorVoltage().getValueAsDouble());
  }

  public void setOutput(Double output) {
    m_crankMotor.set(output);
  }

  public void chengestate(ClimbPosition ClimbPosition) {
    climbstate = ClimbPosition;
  }

  public Command chengestatecCommand(ClimbPosition ClimbPosition) {
    return Commands.runOnce(() -> chengestate(ClimbPosition));
  }

  public void setPosition(double angle) {
    m_crankMotor.setControl(m_positionDutyCycle.withPosition(angle).withSlot(0));
  }

  public double getPosition() {
    return m_crankMotor.getPosition().getValueAsDouble();
  }
}
