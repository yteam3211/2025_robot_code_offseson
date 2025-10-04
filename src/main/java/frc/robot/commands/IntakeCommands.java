package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.states.IntakeIndexerState;
import frc.robot.states.IntakePitchstate;
import frc.robot.states.inakegriperstate;
import frc.robot.subsystems.IntakeIndexer.IntakeIndexer;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.intakeGriper.IntakeGriper;
import java.util.function.BooleanSupplier;

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
        .alongWith(Intakegriper.changestateCommandMustHaveUntil(inakegriperstate.Collect));
  }

  public Command downTakeIndexunil(BooleanSupplier intkaeup) {
    return downTakeIndex().until(() -> intkaeup.getAsBoolean() && intakePitch.getPos() > 80);
  }

  public Command upNOTakeNOIndex() {
    return intakeIndexer
        .changestateCommand(IntakeIndexerState.STOP)
        .alongWith(Intakegriper.changeStateCommand(inakegriperstate.KeepItIn))
        .alongWith(intakePitch.changestateCommand(IntakePitchstate.ZERO_POSITION));
  }

  public Command upNOTakeIndex() {
    return intakeIndexer
        .changestateCommand(IntakeIndexerState.RUN)
        .alongWith(Intakegriper.changeStateCommand(inakegriperstate.KeepItIn))
        .alongWith(intakePitch.changestateCommand(IntakePitchstate.ZERO_POSITION));
  }

  public Command upTakeIndex() {
    return intakeIndexer
        .changestateCommand(IntakeIndexerState.RUN)
        .alongWith(Intakegriper.changeStateCommand(inakegriperstate.Collect))
        .alongWith(intakePitch.changestateCommandMustHaveUntil(IntakePitchstate.ZERO_POSITION));
  }

  public Command intakeCommand(BooleanSupplier intkaeup) {
    return downTakeIndexunil(intkaeup).andThen(upTakeIndex().until(() -> intakePitch.getPos() < 6));
  }

  public Command scoreL1Command() {
    return intakePitch
        .changestateCommand(IntakePitchstate.L1)
        .alongWith(intakeIndexer.changestateCommand(IntakeIndexerState.STOP))
        .withTimeout(5)
        .andThen(Intakegriper.changeStateCommand(inakegriperstate.Eject));
  }

  public Command resetCommand() {
    return intakePitch
        .changestateCommand(IntakePitchstate.ZERO_POSITION)
        .alongWith(intakeIndexer.changestateCommand(IntakeIndexerState.STOP))
        .andThen(Intakegriper.changeStateCommand(inakegriperstate.KeepItIn));
  }
}
