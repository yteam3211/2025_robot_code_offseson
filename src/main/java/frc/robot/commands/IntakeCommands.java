package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.states.IntakeIndexerState;
import frc.robot.states.IntakePitchstate;
import frc.robot.states.inakegriperstate;
import frc.robot.subsystems.IntakeIndexer.IntakeIndexer;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.intakeGriper.IntakeGriper;

public class IntakeCommands {
  private IntakePitch intakePitch;
  private IntakeGriper Intakegriper;
  private IntakeIndexer intakeIndexer;

  public IntakeCommands(
      IntakeGriper intakeSubsystem, IntakePitch intakePitch, IntakeIndexer intakeIndexer) {
    this.intakePitch = intakePitch;
    this.Intakegriper = intakeSubsystem;
    this.intakeIndexer = intakeIndexer;
  }

  public Command downTakeIndex() {
    return intakePitch
        .changestateCommand(IntakePitchstate.INTAKE_POSITION)
        .alongWith(intakeIndexer.changestateCommand(IntakeIndexerState.RUN))
        .alongWith(Intakegriper.changeStateCommand(inakegriperstate.Collect));
  }

  public Command downTakeIndexunilCommand() {
    return downTakeIndex();
  }

  public Command upTakeIndex() {
    return intakeIndexer
        .changestateCommand(IntakeIndexerState.STOP)
        .alongWith(Intakegriper.changeStateCommand(inakegriperstate.KeepItIn))
        .alongWith(intakePitch.changestateCommand(IntakePitchstate.ZERO_POSITION));
  }

  public Command intakeCommand() {
    return downTakeIndexunilCommand();
  }

  public Command scoreL1Command() {
    return intakePitch
        .changestateCommand(IntakePitchstate.L1)
        .alongWith(intakeIndexer.changestateCommand(IntakeIndexerState.STOP))
        .withTimeout(5)
        .andThen(Intakegriper.changeStateCommand(inakegriperstate.Eject));
  }
}
