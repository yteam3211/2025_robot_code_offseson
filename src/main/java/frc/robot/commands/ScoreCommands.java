package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

public class ScoreCommands {
  private ArmCommands armCommands;
  private IntakeCommands intakeCommands;

  public ScoreCommands(ArmCommands armCommands, IntakeCommands intakeCommands) {
    this.armCommands = armCommands;
    this.intakeCommands = intakeCommands;
  }

  public Command intakeStraitToArm() {
    return armCommands
        .elevatorUpDwon()
        .andThen(
            intakeCommands
                .intakeCommand()
                .andThen(armCommands.passToArmFromintake().andThen(armCommands.restAfterPass())));
  }

  public Command intkeToArmUpSide() {
    return armCommands.elevatorUpDwon().andThen(intakeCommands.downTakeIndexunil());
  }
}
