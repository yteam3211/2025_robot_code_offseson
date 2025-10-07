package frc.robot.Buttons;

import frc.robot.Controller;
import frc.robot.commands.ScoreCommands;
import frc.robot.commands.ScoreCommands.sideScore;

public class driverButtom {

  public static void loadButtons(Controller controller, ScoreCommands scoreCommands) {
    controller.swervecontroller.povUp().onTrue(scoreCommands.intakeStraitToArm());
    controller.swervecontroller.povDown().onTrue(scoreCommands.intakeStayOnIntake());
    controller
        .swervecontroller
        .povRight()
        .onTrue(scoreCommands.getClosestLeftRightPose(sideScore.right));
    controller
        .swervecontroller
        .povLeft()
        .onTrue(scoreCommands.getClosestLeftRightPose(sideScore.left));
  }
}
