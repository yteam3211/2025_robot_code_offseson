// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakePitch;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakePitch extends SubsystemBase {
  /** Creates a new IntakePitch. */
    public TalonFX m_spinintake = new TalonFX(IntakePitchConstants.PITCH_MOTOR_ID);
    public DigitalInput m_intakeswitch = new DigitalInput(IntakePitchConstants.CLOSE_SWITCH_PORT);
    private final MotionMagicVoltage motionMagicVoltage = new MotionMagicVoltage(0);

  public IntakePitch() {
    
    TalonFXConfiguration talonFXConfigurationspin = new TalonFXConfiguration();
    FeedbackConfigs feedbackConfigsspin = talonFXConfigurationspin.Feedback;
    feedbackConfigsspin.SensorToMechanismRatio = IntakePitchConstants.POSITION_CONVERSION_FACTOR;

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

    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 5; ++i) {
      status = m_spinintake.getConfigurator().apply(talonFXConfigurationspin);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not configure device. Error: " + status.toString());
    }
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("intakesiwtch", m_intakeswitch.get());
    // This method will be called once per scheduler run
  }
}
