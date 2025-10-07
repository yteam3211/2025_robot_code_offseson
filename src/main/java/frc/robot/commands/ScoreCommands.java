package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.lib.util.DriveToPointFactory;
import frc.robot.states.ClimbPosition;
import frc.robot.subsystems.climb.ClimbSubsystem;
import frc.robot.subsystems.swerve.SwerveSubsystem;
import java.util.function.BooleanSupplier;

public class ScoreCommands {
  public static final Command ScoreL4 = null;
  private ArmCommands armCommands;
  private IntakeCommands intakeCommands;
  private DriveToPointFactory driveToPointFactory;
  private SwerveSubsystem swerveSubsystem;
  private ClimbSubsystem ClimbSubsystem;

  public ScoreCommands(
      ArmCommands armCommands,
      IntakeCommands intakeCommands,
      DriveToPointFactory driveToPointFactory,
      SwerveSubsystem swerveSubsystem,
      ClimbSubsystem ClimbSubsystem) {
    this.armCommands = armCommands;
    this.intakeCommands = intakeCommands;
    this.driveToPointFactory = driveToPointFactory;
    this.swerveSubsystem = swerveSubsystem;
    this.ClimbSubsystem = ClimbSubsystem;
  }

  public Command intakeStraitToArm() {
    return armCommands
        .elevatorUphDwonArmTopos()
        .andThen(
            intakeCommands
                .intakeCommand()
                .andThen(armCommands.passToArmFromintake().andThen(resetCommand())));
  }

  // public Command intkeToArmUpSide() {
  // return armCommands.elevatorUpDwon().andThen(intakeCommands.downTakeIndexunil());
  // }

  public Command ScoreL2() {
    return armCommands.scoreL2().andThen(resetCommand());
  }

  public enum sideScore {
    left,
    right;
  }

  public Command centreToWitchPos(sideScore side) {
    return driveToPointFactory.fineAlign(swerveSubsystem.getClosestLeftRightPose(side));
  }

  public Command testPidAuto(Pose2d newpose) {
    return driveToPointFactory.fineAlign(newpose);
  }

  public Command alegelowCommand() {
    return armCommands
        .alegeCommanLow()
        .andThen(Commands.waitSeconds(1))
        .andThen(resetCommandalge());
  }

  public Command alegehighCommand() {
    return armCommands
        .alegeCommanhgih()
        .andThen(Commands.waitSeconds(1))
        .andThen(resetCommandalge());
  }

  public Command netScore(BooleanSupplier plot) {
    return armCommands.netScore(plot);
  }

  public Command resetCommandalge() {
    return armCommands.resetcommandalge().alongWith(intakeCommands.resetCommand());
  }

  public Command resetCommand() {
    return armCommands.resetcommand().alongWith(intakeCommands.resetCommand());
  }

  public Command ScoreL3() {
    return armCommands.scoreL3().andThen(resetCommand());
  }

  public Command climbslow() {
    return Commands.run(() -> ClimbSubsystem.setOutput(ClimbPosition.Move.getTarget()));
  }

  public Command climbfast() {
    return Commands.run(() -> ClimbSubsystem.setOutput(ClimbPosition.MoveFast.getTarget()));
  }

  public Command climbstop() {
    return Commands.run(() -> ClimbSubsystem.setOutput(ClimbPosition.Hold.getTarget()));
  }

  private boolean isCommandWOrking = false;

  public boolean isCommandWOrking() {
    if (isCommandWOrking) {
      isCommandWOrking = false;
      return true;
    }
    return false;
  }

  public Command ScoreL4() {
    return armCommands.scoreL4().andThen(resetCommand());
  }
}
