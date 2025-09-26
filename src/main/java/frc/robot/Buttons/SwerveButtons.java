package frc.robot.Buttons;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Controller;
import frc.robot.RobotSubsystems;

public class SwerveButtons {

  public static void loadButtons(Controller robotControllers, RobotSubsystems robotSubsystems) {
    robotControllers
        .swervecontroller
        .touchpad()
        .onTrue(new InstantCommand(() -> robotSubsystems.swerve.zeroHeading()));
    // robotControllers
    //     .swervecontroller
    //     .R1()
    //     .onTrue(Commands.runOnce(() -> robotSubsystems.swerve.resetPose(),
    // robotSubsystems.swerve));
    robotControllers
        .swervecontroller
        .R2()
        .onTrue(
            Commands.runOnce(
                () -> robotSubsystems.swerve.resetDriveEncoders(), robotSubsystems.swerve));
    robotControllers
        .swervecontroller
        .circle()
        .whileTrue(
            Commands.runOnce(() -> robotSubsystems.swerve.moveFrontLeft(), robotSubsystems.swerve));
  }
}
