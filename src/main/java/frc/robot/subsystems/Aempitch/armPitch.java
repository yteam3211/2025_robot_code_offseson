// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Aempitch;

import com.ctre.phoenix6.StatusCode;
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
import frc.lib.util.LimelightHelpers;
import frc.robot.states.armPitchState;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

public class armPitch extends SubsystemBase {

  public TalonFX m_Pitch = new TalonFX(ArmPItchConstants.m_PitchID, "rio");
  public armPitchState state = armPitchState.firtstinit;
  private final MotionMagicVoltage motionMagicVoltage = new MotionMagicVoltage(0);

  // armioinputsautologged input = new armioinputsautolog;
  // armio m_io;

  /** Creates a new armsubsystem. */
  public armPitch(/* armio io*/ ) {
    // m_io = io;
    m_Pitch.setPosition(0);
    TalonFXConfiguration talonFXConfigurationspin = new TalonFXConfiguration();
    FeedbackConfigs feedbackConfigsspin = talonFXConfigurationspin.Feedback;
    feedbackConfigsspin.SensorToMechanismRatio = ArmPItchConstants.POSITION_CONVERSION_FACTOR;
    MotorOutputConfigs motorOutputConfigsspin = talonFXConfigurationspin.MotorOutput;
    motorOutputConfigsspin.NeutralMode = ArmPItchConstants.NeutralMode;
    MotionMagicConfigs motionMagicConfigsspin = talonFXConfigurationspin.MotionMagic;
    motionMagicConfigsspin.MotionMagicCruiseVelocity =
        ArmPItchConstants.MotionMagicConstantsspin.MOTION_MAGIC_VELOCITY;
    motionMagicConfigsspin.MotionMagicAcceleration =
        ArmPItchConstants.MotionMagicConstantsspin.MOTION_MAGIC_ACCELERATION;
    motionMagicConfigsspin.MotionMagicJerk =
        ArmPItchConstants.MotionMagicConstantsspin.MOTION_MAGIC_JERK;

    Slot0Configs slot0spin = talonFXConfigurationspin.Slot0;
    slot0spin.kS = ArmPItchConstants.MotionMagicConstantsspin.MOTOR_KS;
    slot0spin.kG = ArmPItchConstants.MotionMagicConstantsspin.MOTOR_KG;
    slot0spin.kV = ArmPItchConstants.MotionMagicConstantsspin.MOTOR_KV;
    slot0spin.kA = ArmPItchConstants.MotionMagicConstantsspin.MOTOR_KA;
    slot0spin.kP = ArmPItchConstants.MotionMagicConstantsspin.MOTOR_KP;
    slot0spin.kI = ArmPItchConstants.MotionMagicConstantsspin.MOTOR_KI;
    slot0spin.kD = ArmPItchConstants.MotionMagicConstantsspin.MOTOR_KD;
    slot0spin.GravityType = ArmPItchConstants.MotionMagicConstantsspin.GravityType;

    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 5; ++i) {
      status = m_Pitch.getConfigurator().apply(talonFXConfigurationspin);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not configure device. Error: " + status.toString());
    }
    this.setDefaultCommand(setDefualArmPitchCommand());
  }

  public Command changestateCommandMustHaveUntil(armPitchState new_state) {
    return Commands.run(() -> chengestate(new_state));
  }

  public void setdefualt() {
    setRotation(state.getTarget());
  }

  public Command setDefualArmPitchCommand() {
    return this.runOnce(() -> setdefualt());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Arm Position", getArmPosition());
    SmartDashboard.putString("Arm Pitch", state.name() + state.getTarget());
    SmartDashboard.putBoolean("arm al least 10 ", isAtLestpos(-10).getAsBoolean());
    SmartDashboard.putNumber("flip", flip.getAsInt());
    // io.updateinputs(inputs);
  }

  public void resetPos() {
    m_Pitch.setPosition(0);
  }

  public double getArmPosition() {
    return m_Pitch.getPosition().getValueAsDouble();
  }

  public BooleanSupplier isLesspos(double pos) {
    return () -> getArmPosition() < pos;
  }

  public void needflipreef() {
    LimelightHelpers.PoseEstimate mt1right =
        LimelightHelpers.getBotPoseEstimate_wpiBlue("limelight-right");
    boolean doRejectUpdate_right = false;
    if (mt1right != null) {
      if (mt1right.tagCount >= 1) {
        if (mt1right.rawFiducials[0].ambiguity > .7) {
          doRejectUpdate_right = true;
        }
        if (mt1right.rawFiducials[0].distToCamera > 3) {
          doRejectUpdate_right = true;
        }
      }
      if (mt1right.tagCount == 0) {
        doRejectUpdate_right = true;
      }

      if (!doRejectUpdate_right) {
        flip = () -> 1;
      }
    }
    LimelightHelpers.PoseEstimate mt1left =
        LimelightHelpers.getBotPoseEstimate_wpiBlue("limelight-left");
    boolean doRejectUpdate_left = false;
    if (mt1left != null) {
      if (mt1left.tagCount >= 1) {
        if (mt1left.rawFiducials[0].ambiguity > .7) {
          doRejectUpdate_left = true;
        }
        if (mt1left.rawFiducials[0].distToCamera > 3) {
          doRejectUpdate_left = true;
        }
      }
      if (mt1left.tagCount == 0) {
        doRejectUpdate_left = true;
      }
      if (!doRejectUpdate_left) {
        flip = () -> -1;
      }
    }
  }

  IntSupplier flip = () -> 1;

  public IntSupplier flip() {
    return flip;
  }

  public BooleanSupplier isAtLestpos(double pos) {
    return () -> getArmPosition() > pos;
  }

  public BooleanSupplier isAtLestPosdouble(double pos) {
    return () -> Math.abs(getArmPosition()) > Math.abs(pos);
  }

  public BooleanSupplier islessthenPosdouble(double pos) {
    return () -> Math.abs(getArmPosition()) < Math.abs(pos);
  }

  public void chengestate(armPitchState new_state) {
    state = new_state;
  }

  public Command chengestateCommand(armPitchState new_state) {
    return Commands.runOnce(() -> chengestate(new_state));
  }

  public Command chengestateCommandMustHaveUntil(armPitchState new_state) {
    return Commands.run(() -> chengestate(new_state));
  }

  public void setRotation(Double targetPos) {
    needflipreef();
    m_Pitch.setControl(motionMagicVoltage.withPosition(targetPos * flip().getAsInt()).withSlot(0));
  }

  public Command setRotationCommand(Double targetPos) {
    return Commands.runOnce(() -> setRotation(targetPos));
  }

  public double getarmspin() {
    return m_Pitch.getPosition().getValueAsDouble();
  }

  public Command settoZeroSuper() {
    return chengestateCommand(armPitchState.rest).andThen(this.run(() -> setRotation(0.0)));
  }
}
