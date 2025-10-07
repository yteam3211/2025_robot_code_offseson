package frc.robot.Buttons;

import frc.robot.Controller;
import frc.robot.commands.ScoreCommands;

public class SubButton {

  public static void loadButtons(Controller controller, ScoreCommands scoreCommands) {
    controller.subcontroller.square().onTrue(scoreCommands.ScoreL1());
    controller.subcontroller.cross().onTrue(scoreCommands.ScoreL2());
    controller.subcontroller.circle().onTrue(scoreCommands.ScoreL3());
    controller.subcontroller.triangle().onTrue(scoreCommands.ScoreL4());
    controller.subcontroller.L1().onTrue(scoreCommands.resetCommand());
  }
}
