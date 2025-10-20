package frc.robot.Buttons;

import frc.robot.Controller;
import frc.robot.commands.ScoreCommands;
import frc.robot.commands.ScoreCommands.sideScore;

public class SubButton {

  public static void loadButtons(Controller controller, ScoreCommands scoreCommands) {
    controller.subcontroller.square().onTrue(scoreCommands.ScoreL1());
    controller
        .subcontroller
        .cross()
        .onTrue(scoreCommands.ScoreL2(controller.subcontroller.cross()));
    controller
        .subcontroller
        .povLeft()
        .whileTrue(scoreCommands.getClosestLeftRightPose(sideScore.left));
    controller
        .subcontroller
        .povRight()
        .whileTrue(scoreCommands.getClosestLeftRightPose(sideScore.right));
    controller
        .subcontroller
        .circle()
        .onTrue(scoreCommands.ScoreL3(controller.subcontroller.circle()));
    controller
        .subcontroller
        .triangle()
        .onTrue(scoreCommands.ScoreL4(controller.subcontroller.triangle()));
    controller.subcontroller.L1().onTrue(scoreCommands.resetCommandsuper());
    controller.subcontroller.povUp().onTrue(scoreCommands.alegehighCommand());
    controller.subcontroller.povDown().onTrue(scoreCommands.alegelowCommand());
    controller
        .subcontroller
        .povUp()
        .onTrue(scoreCommands.netScore(controller.subcontroller.povRight()));
  }
}
