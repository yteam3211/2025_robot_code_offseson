package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import java.util.function.BooleanSupplier;

public class ScoreCommands {
  private ArmCommands armCommands;
  private IntakeCommands intakeCommands;

  public ScoreCommands(ArmCommands armCommands, IntakeCommands intakeCommands) {
    this.armCommands = armCommands;
    this.intakeCommands = intakeCommands;
  }

  public Command intakeStraitToArm(BooleanSupplier intkaeup) {
    return armCommands
        .elevatorUphDwonArmTopos()
        .andThen(
            intakeCommands
                .intakeCommand(intkaeup)
                .andThen(armCommands.passToArmFromintake().andThen(resetCommand())));
  }

  // public Command intkeToArmUpSide() {
  // return armCommands.elevatorUpDwon().andThen(intakeCommands.downTakeIndexunil());
  // }

  public Command ScoreL2() {
    return armCommands.scoreL2();
  }

  public Command resetCommand() {
    return armCommands.resetcommand().alongWith(intakeCommands.resetCommand());
  }
}
