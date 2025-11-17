// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.ArmPitchSim;

import static edu.wpi.first.units.Units.Degree;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.util.LimelightHelpers;
import frc.robot.states.armPitchState;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

public class ArmPitchSim extends SubsystemBase {
  @AutoLogOutput armPitchState state = armPitchState.firtstinit;
  ArmPitchInputsAutoLogged inputs = new ArmPitchInputsAutoLogged();
  ArmPitchIO io;
  /** Creates a new ArmPitchSim. */
  public ArmPitchSim(ArmPitchIO io) {
    this.io = io;
    this.setDefaultCommand(defualtCommand());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    io.updateInputs(inputs);
    Logger.processInputs("Arm Pitch", inputs);
    Logger.recordOutput("arm Ptihc/state", state);
  }

  @Override
  public void simulationPeriodic() {
    io.updateArmPitch();
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

  public Angle getAngle() {
    return inputs.pos;
  }

  public AngularVelocity getVelocity() {
    return inputs.speed;
  }

  public BooleanSupplier isLesspos(double pos) {
    return () -> getAngle().in(Degree) < pos;
  }

  public BooleanSupplier isAtLestpos(double pos) {
    return () -> getAngle().in(Degree) > pos;
  }

  public BooleanSupplier isAtLestPosdouble(double pos) {
    return () -> Math.abs(getAngle().in(Degree)) > Math.abs(pos);
  }

  public BooleanSupplier islessthenPosdouble(double pos) {
    return () -> Math.abs(getAngle().in(Degree)) < Math.abs(pos);
  }

  public Command setRotationCommand(Double targetPos) {
    return Commands.runOnce(() -> io.setPos(targetPos));
  }

  public void changeState(armPitchState new_State) {
    state = new_State;
  }

  public Command changeStateCommand(armPitchState new_State) {
    return Commands.runOnce(() -> changeState(new_State));
  }

  public Command defualtCommand() {
    return this.run(() -> io.setPos(state.getTarget() * flip().getAsInt()));
  }
}
