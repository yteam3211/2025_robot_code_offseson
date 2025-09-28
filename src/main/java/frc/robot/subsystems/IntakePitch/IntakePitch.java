// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakePitch;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.states.IntakePitchstate;
import java.util.function.DoubleSupplier;

public class IntakePitch extends SubsystemBase {
  public TalonFX m_spinintake = new TalonFX(IntakePitchConstants.PITCH_MOTOR_ID);
  public DigitalInput m_limiteswitch = new DigitalInput(IntakePitchConstants.CLOSE_SWITCH_PORT);
  public IntakePitchstate state = IntakePitchstate.INTAKE_POSITION;
  private final MotionMagicVoltage motionMagicVoltage = new MotionMagicVoltage(0);

  public IntakePitch() {
    TalonFXConfiguration talonFXConfigurationspin = new TalonFXConfiguration();
    FeedbackConfigs feedbackConfigsspin = talonFXConfigurationspin.Feedback;
    feedbackConfigsspin.SensorToMechanismRatio = IntakePitchConstants.POSITION_CONVERSION_FACTOR;
    feedbackConfigsspin.FeedbackRotorOffset = IntakePitchConstants.FeedbackRotorOffset;
    MotorOutputConfigs motorOutputConfigsspin = talonFXConfigurationspin.MotorOutput;
    motorOutputConfigsspin.Inverted = IntakePitchConstants.MOTOR_INVERTED;
    motorOutputConfigsspin.NeutralMode = IntakePitchConstants.MOTOR_NEUTRAL_MODE;
    MotionMagicConfigs motionMagicConfigsspin = talonFXConfigurationspin.MotionMagic;
    motionMagicConfigsspin.MotionMagicCruiseVelocity =
        IntakePitchConstants.MotionMagicConstants.MOTION_MAGIC_VELOCITY;
    motionMagicConfigsspin.MotionMagicAcceleration =
        IntakePitchConstants.MotionMagicConstants.MOTION_MAGIC_ACCELERATION;
    motionMagicConfigsspin.MotionMagicJerk =
        IntakePitchConstants.MotionMagicConstants.MOTION_MAGIC_JERK;

    Slot0Configs slot0spin = talonFXConfigurationspin.Slot0;
    slot0spin.kS = IntakePitchConstants.MotionMagicConstants.MOTOR_KS;
    slot0spin.kG = IntakePitchConstants.MotionMagicConstants.MOTOR_KG;
    slot0spin.kV = IntakePitchConstants.MotionMagicConstants.MOTOR_KV;
    slot0spin.kA = IntakePitchConstants.MotionMagicConstants.MOTOR_KA;
    slot0spin.kP = IntakePitchConstants.MotionMagicConstants.MOTOR_KP;
    slot0spin.kI = IntakePitchConstants.MotionMagicConstants.MOTOR_KI;
    slot0spin.kD = IntakePitchConstants.MotionMagicConstants.MOTOR_KD;
    slot0spin.GravityType = IntakePitchConstants.MotionMagicConstants.GravityType;

    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 5; ++i) {
      status = m_spinintake.getConfigurator().apply(talonFXConfigurationspin);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not configure device. Error: " + status.toString());
    }
    this.setDefaultCommand(SetIntakePitchDefualtCommand());
  }

  public double intakeGetPos() {
    return m_spinintake.getPosition().getValueAsDouble();
  }

  public void setdefualt() {
    setIntakePosition(state.getTarget());
  }

  public Command SetIntakePitchDefualtCommand() {
    return this.runOnce(() -> setdefualt());
  }

  public boolean isClose() {
    return !m_limiteswitch.get();
  }

  public void setIntakePosition(double position) {
    m_spinintake.setControl(motionMagicVoltage.withPosition(position).withSlot(0));
  }

  public void stopIntake() {
    m_spinintake.set(0);
  }

  public Command stopIntakeCommand() {
    return this.runOnce(this::stopIntake);
  }

  public Command setIntakePositionCommand(DoubleSupplier position) {
    System.out.println("IntakePitch: Setting position to " + position.getAsDouble());
    return this.run(() -> setIntakePosition(position.getAsDouble()));
  }

  public Command changestateCommand(IntakePitchstate new_state) {
    return Commands.runOnce(() -> setstate(new_state));
  }

  public void setstate(IntakePitchstate new_state) {
    state = new_state;
  }

  public Command settozerodposCommand() {
    return changestateCommand(IntakePitchstate.ZERO_POSITION)
        .andThen(setspeddCommand(() -> -0.15))
        .until(this::isClose);
  }

  public void setSpeed(double speed) {
    m_spinintake.set(speed);
  }

  public Command setspeddCommand(DoubleSupplier speed) {
    return this.runOnce(() -> setSpeed(speed.getAsDouble()));
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("IntakePitch: isClose", isClose());
    // This method will be called once per scheduler run
    if (isClose()) {
      resetPos();
    }
  }

  public void resetPos() {

    m_spinintake.setPosition(0);
  }
}
