package frc.robot.Buttons;

import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Controller;
import frc.robot.commands.ScoreCommands;
import java.util.function.BooleanSupplier;

public class SubButton {
  public static BooleanSupplier test = () -> false;

  public static void loadButtons(Controller controller, ScoreCommands scoreCommands) {
    controller.subcontroller.square().onTrue(scoreCommands.ScoreL1().until(test));
    controller
        .subcontroller
        .cross()
        .onTrue(scoreCommands.ScoreL2(controller.subcontroller.cross()).until(test));
    // controller
    //     .subcontroller
    //     .povLeft()
    //     .whileTrue(scoreCommands.getClosestLeftRightPose(sideScore.left).until(test));
    // controller
    //     .subcontroller
    //     .povRight()
    // .whileTrue(scoreCommands.getClosestLeftRightPose(sideScore.right).until(test));
    controller
        .subcontroller
        .circle()
        .onTrue(scoreCommands.ScoreL3(controller.subcontroller.circle()).until(test));
    controller
        .subcontroller
        .triangle()
        .onTrue(scoreCommands.ScoreL4(controller.subcontroller.triangle()).until(test));
    controller
        .subcontroller
        .L2()
        .onTrue(
            Commands.runOnce(() -> test = () -> true)
                .andThen(
                    scoreCommands
                        .resetCommandsuper()
                        .andThen(Commands.runOnce(() -> test = () -> false))));
    controller.subcontroller.povUp().onTrue(scoreCommands.alegehighCommand().until(test));
    controller.subcontroller.povDown().onTrue(scoreCommands.alegelowCommand().until(test));
    controller
        .subcontroller
        .povUp()
        .onTrue(scoreCommands.netScore(controller.subcontroller.povRight()).until(test));
  }
}
