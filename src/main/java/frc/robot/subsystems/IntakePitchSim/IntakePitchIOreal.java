// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakePitchSim;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.DegreesPerSecond;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;

public class IntakePitchIOreal implements IntakePitchIO {

  public TalonFX m_Pitch = new TalonFX(IntakePitchConstantsSim.PITCH_MOTOR_ID, "rio");
  private DigitalInput m_DigitalInput = new DigitalInput(IntakePitchConstantsSim.CLOSE_SWITCH_PORT);
  private final MotionMagicVoltage motionMagicVoltage = new MotionMagicVoltage(0);

  public IntakePitchIOreal() {

    TalonFXConfiguration talonFXConfigurationspin = new TalonFXConfiguration();
    FeedbackConfigs feedbackConfigsspin = talonFXConfigurationspin.Feedback;
    feedbackConfigsspin.SensorToMechanismRatio = IntakePitchConstantsSim.POSITION_CONVERSION_FACTOR;
    MotorOutputConfigs motorOutputConfigsspin = talonFXConfigurationspin.MotorOutput;
    motorOutputConfigsspin.NeutralMode = IntakePitchConstantsSim.NeutralMode;
    motorOutputConfigsspin.Inverted = IntakePitchConstantsSim.MOTOR_INVERTED;
    MotionMagicConfigs motionMagicConfigsspin = talonFXConfigurationspin.MotionMagic;
    motionMagicConfigsspin.MotionMagicCruiseVelocity =
        IntakePitchConstantsSim.MotionMagicConstants.MOTION_MAGIC_VELOCITY;
    motionMagicConfigsspin.MotionMagicAcceleration =
        IntakePitchConstantsSim.MotionMagicConstants.MOTION_MAGIC_ACCELERATION;
    motionMagicConfigsspin.MotionMagicJerk =
        IntakePitchConstantsSim.MotionMagicConstants.MOTION_MAGIC_JERK;

    Slot0Configs slot0spin = talonFXConfigurationspin.Slot0;
    slot0spin.kS = IntakePitchConstantsSim.MotionMagicConstants.MOTOR_KS;
    slot0spin.kG = IntakePitchConstantsSim.MotionMagicConstants.MOTOR_KG;
    slot0spin.kV = IntakePitchConstantsSim.MotionMagicConstants.MOTOR_KV;
    slot0spin.kA = IntakePitchConstantsSim.MotionMagicConstants.MOTOR_KA;
    slot0spin.kP = IntakePitchConstantsSim.MotionMagicConstants.MOTOR_KP;
    slot0spin.kI = IntakePitchConstantsSim.MotionMagicConstants.MOTOR_KI;
    slot0spin.kD = IntakePitchConstantsSim.MotionMagicConstants.MOTOR_KD;
    slot0spin.GravityType = IntakePitchConstantsSim.MotionMagicConstants.GravityType;

    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 5; ++i) {
      status = m_Pitch.getConfigurator().apply(talonFXConfigurationspin);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not configure device. Error: " + status.toString());
    }
    m_Pitch.setPosition(0);
  }

  @Override
  public void updateArmPitch() {}

  @Override
  public void updateInputs(IntakePitchInputs inputs) {
    inputs.pos = m_Pitch.getPosition().getValue().in(Degree);
    inputs.speed = m_Pitch.getVelocity().getValue().in(DegreesPerSecond);
    inputs.isClosed = m_DigitalInput.get();
  }

  @Override
  public void setSpeedPos(double speed) {
    m_Pitch.set(speed);
  }

  @Override
  public void setPos(Double pos) {
    m_Pitch.setControl(motionMagicVoltage.withPosition(pos));
  }
}
