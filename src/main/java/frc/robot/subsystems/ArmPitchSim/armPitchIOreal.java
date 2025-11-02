// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.ArmPitchSim;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.util.LimelightHelpers;
import frc.robot.states.armPitchState;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

public class armPitchIOreal implements ArmPitchIO {

  public TalonFX m_Pitch = new TalonFX(ArmPItchConstantsSim.m_PitchID, "rio");
  private final MotionMagicVoltage motionMagicVoltage = new MotionMagicVoltage(0);

  // armioinputsautologged input = new armioinputsautolog;
  // armio m_io;


  public armPitchIOreal() {
    m_Pitch.setPosition(0);
    TalonFXConfiguration talonFXConfigurationspin = new TalonFXConfiguration();
    FeedbackConfigs feedbackConfigsspin = talonFXConfigurationspin.Feedback;
    feedbackConfigsspin.SensorToMechanismRatio = ArmPItchConstantsSim.POSITION_CONVERSION_FACTOR;
    MotorOutputConfigs motorOutputConfigsspin = talonFXConfigurationspin.MotorOutput;
    motorOutputConfigsspin.NeutralMode = ArmPItchConstantsSim.NeutralMode;
    MotionMagicConfigs motionMagicConfigsspin = talonFXConfigurationspin.MotionMagic;
    motionMagicConfigsspin.MotionMagicCruiseVelocity =
    ArmPItchConstantsSim.MotionMagicConstantsspin.MOTION_MAGIC_VELOCITY;
    motionMagicConfigsspin.MotionMagicAcceleration =
    ArmPItchConstantsSim.MotionMagicConstantsspin.MOTION_MAGIC_ACCELERATION;
    motionMagicConfigsspin.MotionMagicJerk =
    ArmPItchConstantsSim.MotionMagicConstantsspin.MOTION_MAGIC_JERK;

    Slot0Configs slot0spin = talonFXConfigurationspin.Slot0;
    slot0spin.kS = ArmPItchConstantsSim.MotionMagicConstantsspin.MOTOR_KS;
    slot0spin.kG = ArmPItchConstantsSim.MotionMagicConstantsspin.MOTOR_KG;
    slot0spin.kV = ArmPItchConstantsSim.MotionMagicConstantsspin.MOTOR_KV;
    slot0spin.kA = ArmPItchConstantsSim.MotionMagicConstantsspin.MOTOR_KA;
    slot0spin.kP = ArmPItchConstantsSim.MotionMagicConstantsspin.MOTOR_KP;
    slot0spin.kI = ArmPItchConstantsSim.MotionMagicConstantsspin.MOTOR_KI;
    slot0spin.kD = ArmPItchConstantsSim.MotionMagicConstantsspin.MOTOR_KD;
    slot0spin.GravityType = ArmPItchConstantsSim.MotionMagicConstantsspin.GravityType;

    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 5; ++i) {
      status = m_Pitch.getConfigurator().apply(talonFXConfigurationspin);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not configure device. Error: " + status.toString());
    }
  }
  @Override
  public void updateInputs(ArmPitchInputs inputs){
    inputs.pos = m_Pitch.getPosition().getValue();
    inputs.speed = m_Pitch.getVelocity().getValue();
    inputs.acce = m_Pitch.getAcceleration().getValue();
  }
  @Deprecated
  @Override
  public Command setSpeedPos(double speed){
    return Commands.runOnce(()-> m_Pitch.set(speed));
  }
  @Override
  public Command setPos(Angle pos){
    return Commands.runOnce(()->m_Pitch.setControl(motionMagicVoltage.withPosition(pos)));
  }


}
