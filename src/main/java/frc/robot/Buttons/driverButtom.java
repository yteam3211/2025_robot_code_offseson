package frc.robot.Buttons;

import frc.robot.Controller;
import frc.robot.commands.ScoreCommands;

public class driverButtom {

  public static void loadButtons(Controller controller, ScoreCommands scoreCommands) {
    // controller
    //     .swervecontroller
    //     .povUp()
    //     .onTrue(scoreCommands.intakeStraitToArm().until(SubButton.test));
    controller
        .swervecontroller
        .povDown()
        .onTrue(scoreCommands.intakeStayOnIntake().until(SubButton.test));
    controller.swervecontroller.triangle().onTrue(scoreCommands.ScoreL1());
    // controller
    //     .swervecontroller
    //     .povRight()
    //     .onTrue(scoreCommands.getClosestLeftRightPose(sideScore.right).until(SubButton.test));
    // controller
    //     .swervecontroller
    //     .povLeft()
    //     .onTrue(scoreCommands.getClosestLeftRightPose(sideScore.left).until(SubButton.test));
    controller.swervecontroller.cross().whileTrue(scoreCommands.climbslow().until(SubButton.test));
    controller.swervecontroller.circle().whileTrue(scoreCommands.climbfast().until(SubButton.test));
  }
}
