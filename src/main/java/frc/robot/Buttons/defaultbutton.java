package frc.robot.Buttons;

import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.robot.Controller;
import frc.robot.RobotSubsystems;
import frc.robot.commands.swerve.DriveCommands;

public class defaultbutton {
  public static void loadButtons(Controller Controller, RobotSubsystems robotSubsystems) {
    swerve(Controller.swervecontroller, robotSubsystems);
  }

  public static void swerve(
      CommandPS5Controller driverController, RobotSubsystems robotSubsystems) {
    robotSubsystems.drive.setDefaultCommand(
        DriveCommands.joystickDrive(
            robotSubsystems.drive,
            () -> -driverController.getLeftY(),
            () -> -driverController.getLeftX(),
            () -> -driverController.getRightX()));
  }
}
