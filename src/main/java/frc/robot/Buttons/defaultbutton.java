package frc.robot.Buttons;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.robot.Controller;
import frc.robot.RobotSubsystems;
import frc.robot.commands.swerve.DriveCommands2;

public class defaultbutton {

  public static void loadButtons(Controller Controller, RobotSubsystems robotSubsystems) {
    swerve(Controller.swervecontroller, robotSubsystems);
  }

  public static void swerve(
      CommandPS5Controller driverController, RobotSubsystems robotSubsystems) {
    // robotSubsystems.swerve.setDefaultCommand(
    //     new DriveCommand(
    //         robotSubsystems.swerve,
    //         () -> -driverController.getLeftY(),
    //         () -> -driverController.getLeftX(),
    //         () -> -driverController.getRightX(),
    //         () -> false));
    robotSubsystems.drive.setDefaultCommand(
        DriveCommands2.joystickDrive(
            robotSubsystems.drive,
            () -> -driverController.getLeftY(),
            () -> -driverController.getLeftX(),
            () -> -driverController.getRightX()));
    driverController
        .triangle()
        .whileTrue(
            DriveCommands2.joystickDriveAtAngle(
                robotSubsystems.drive, () -> 1, () -> 0, () -> Rotation2d.fromDegrees(0)));
  }
}
