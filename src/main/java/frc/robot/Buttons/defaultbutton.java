package frc.robot.Buttons;

import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.robot.Controller;
import frc.robot.RobotSubsystems;
import frc.robot.commands.swerve.DriveCommand;

public class defaultbutton {
  public static void loadButtons(Controller Controller, RobotSubsystems robotSubsystems) {
    swerve(Controller.swervecontroller, robotSubsystems);
  }

  public static void swerve(
      CommandPS5Controller driverController, RobotSubsystems robotSubsystems) {
    robotSubsystems.swerve.setDefaultCommand(
        new DriveCommand(
            robotSubsystems.swerve,
            () -> -driverController.getLeftY(),
            () -> -driverController.getLeftX(),
            () -> driverController.getRightX(),
            () -> false));
  }
}
