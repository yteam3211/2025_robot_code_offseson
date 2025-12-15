package frc.robot.Buttons;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Controller;
import frc.robot.Robot;
import frc.robot.RobotSubsystems;
import frc.robot.commands.swerve.DriveCommands2;
import org.ironmaple.simulation.SimulatedArena;
import org.ironmaple.simulation.seasonspecific.reefscape2025.ReefscapeCoralOnField;

public class defaultbutton {
  private static boolean isHome = true;

  public static void loadButtons(Controller Controller, RobotSubsystems robotSubsystems) {
    swerve(Controller, robotSubsystems);
  }

  public static void swerve(Controller Controller, RobotSubsystems robotSubsystems) {
    // robotSubsystems.swerve.setDefaultCommand(
    //     new DriveCommand(
    //         robotSubsystems.swerve,
    //         () -> -driverController.getLeftY(),
    //         () -> -driverController.getLeftX(),
    //         () -> -driverController.getRightX(),
    //         () -> false));
    if (isHome && !Robot.isReal()) {
      robotSubsystems.drive.setDefaultCommand(
          DriveCommands2.joystickDrive(
              robotSubsystems.drive,
              () -> -Controller.test.getLeftY(),
              () -> -Controller.test.getLeftX(),
              () -> -Controller.test.getRightX()));
      Controller.test
          .a()
          .onTrue(
              Commands.runOnce(
                  () ->
                      SimulatedArena.getInstance()
                          .addGamePiece(new ReefscapeCoralOnField(new Pose2d()))));
    } else {
      robotSubsystems.drive.setDefaultCommand(
          DriveCommands2.joystickDrive(
              robotSubsystems.drive,
              () -> -Controller.swervecontroller.getLeftY(),
              () -> -Controller.swervecontroller.getLeftX(),
              () -> -Controller.swervecontroller.getRightX()));
    }
  }
}
