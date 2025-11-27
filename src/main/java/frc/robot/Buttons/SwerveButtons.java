package frc.robot.Buttons;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Controller;
import frc.robot.RobotSubsystems;

public class SwerveButtons {

  public static void loadButtons(Controller robotControllers, RobotSubsystems robotSubsystems) {
    robotControllers
        .swervecontroller
        .touchpad()
        .onTrue(new InstantCommand(() -> robotSubsystems.drive.zeroHeading()));
  }
}
