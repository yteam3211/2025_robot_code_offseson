package frc.robot.subsystems.climb;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.states.ClimbPosition;
import frc.robot.subsystems.climb.ClimbConstants.MotorCurrentLimits;

public class ClimbSubsystem extends SubsystemBase {

  public ClimbPosition climbstate = ClimbPosition.Hold;
  private TalonFX m_crankMotor;
  private final MotionMagicVoltage motionMagicVoltage = new MotionMagicVoltage(0);

  public ClimbSubsystem() {
    m_crankMotor = new TalonFX(ClimbConstants.CLIMB_MOTOR_ID, "canv");
    TalonFXConfiguration talonFXConfiguration = new TalonFXConfiguration();

    FeedbackConfigs feedbackConfigs = talonFXConfiguration.Feedback;
    feedbackConfigs.FeedbackSensorSource = ClimbConstants.SensorSource;
    feedbackConfigs.SensorToMechanismRatio = ClimbConstants.POSITION_CONVERSION_FACTOR;

    MotorOutputConfigs motorOutputConfigs = talonFXConfiguration.MotorOutput;
    motorOutputConfigs.NeutralMode = ClimbConstants.NeutralMode;

    CurrentLimitsConfigs limitConfigs = talonFXConfiguration.CurrentLimits;
    limitConfigs.SupplyCurrentLimit =
        frc.robot.subsystems.climb.ClimbConstants.MotorCurrentLimits.SUPPLY_CURRENT_LIMIT;
    limitConfigs.SupplyCurrentLowerLimit = MotorCurrentLimits.SUPPLY_CURRENT_LOWER_LIMIT;
    limitConfigs.SupplyCurrentLimitEnable = MotorCurrentLimits.SUPPLY_CURRENT_LIMIT_ENABLE;

    MotionMagicConfigs motionMagicConfigs = talonFXConfiguration.MotionMagic;
    motionMagicConfigs.MotionMagicCruiseVelocity =
        ClimbConstants.MotionMagicConstants.MOTION_MAGIC_VELOCITY;
    motionMagicConfigs.MotionMagicAcceleration =
        ClimbConstants.MotionMagicConstants.MOTION_MAGIC_ACCELERATION;
    motionMagicConfigs.MotionMagicJerk = ClimbConstants.MotionMagicConstants.MOTION_MAGIC_JERK;

    Slot0Configs slot0 = talonFXConfiguration.Slot0;
    slot0.kS = ClimbConstants.MotionMagicConstants.MOTOR_KS;
    slot0.kG = ClimbConstants.MotionMagicConstants.MOTOR_KG;
    slot0.kV = ClimbConstants.MotionMagicConstants.MOTOR_KV;
    slot0.kA = ClimbConstants.MotionMagicConstants.MOTOR_KA;
    slot0.kP = ClimbConstants.MotionMagicConstants.MOTOR_KP;
    slot0.kI = ClimbConstants.MotionMagicConstants.MOTOR_KI;
    slot0.kD = ClimbConstants.MotionMagicConstants.MOTOR_KD;
    slot0.GravityType = ClimbConstants.MotionMagicConstants.GravityType;

    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 5; ++i) {
      status = m_crankMotor.getConfigurator().apply(talonFXConfiguration);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not configure device. Error: " + status.toString());
    }
    m_crankMotor.setPosition(0);
    this.setDefaultCommand(this.run(() -> movetopos(climbstate.getTarget())));
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("climb voltage", m_crankMotor.getMotorVoltage().getValueAsDouble());
  }

  public void setpos(double pos) {
    m_crankMotor.setPosition(pos);
  }

  public void movetopos(double pos) {
    m_crankMotor.setControl(motionMagicVoltage.withPosition(pos).withSlot(0));
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

  public double getPosition() {
    return m_crankMotor.getPosition().getValueAsDouble();
  }
}
