package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.util.DriveToPointFactory;
import frc.robot.FieldConstants;

public class ScoreCommands {
  private ArmCommands armCommands;
  private IntakeCommands intakeCommands;
  private DriveToPointFactory driveToPointFactory;

  public ScoreCommands(
      ArmCommands armCommands,
      IntakeCommands intakeCommands,
      DriveToPointFactory driveToPointFactory) {
    this.armCommands = armCommands;
    this.intakeCommands = intakeCommands;
    this.driveToPointFactory = driveToPointFactory;
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
    return intakeStraitToArm()
        .andThen(
            driveToPointFactory.driveToPose(
                new Pose2d(-999999, -9999, new Rotation2d())
                    .nearest(FieldConstants.Reef.centerFacesList)))
        .andThen(armCommands.scoreL2());
  }

  public Command resetCommand() {
    return armCommands.resetcommand().alongWith(intakeCommands.resetCommand());
  }

  public Command ScoreL3() {
    // return driveToPointFactory
    // .driveToPose(
    // new Pose2d(-999999, -9999, new Rotation2d())
    // .nearest(FieldConstants.Reef.centerFacesList))
    return armCommands.scoreL3();
  }

  private boolean isCommandWOrking = false;

  public boolean isCommandWOrking() {
    if (isCommandWOrking) {
      isCommandWOrking = false;
      return true;
    }
    return false;
  }
}
