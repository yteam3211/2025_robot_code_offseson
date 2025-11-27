package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.states.IntakeIndexerState;
import frc.robot.states.IntakePitchstate;
import frc.robot.states.inakegriperstate;
import frc.robot.subsystems.IntakeIndexer.IntakeIndexer;
import frc.robot.subsystems.IntakePitchSim.IntakePitchSim;
import frc.robot.subsystems.intakeGriper.IntakeGriper;

public class IntakeCommands {
  private IntakePitchSim intakePitch;
  private IntakeGriper Intakegriper;
  private IntakeIndexer intakeIndexer;

  public IntakeCommands(
      IntakeGriper intakeSubsystem, IntakePitchSim intakePitch, IntakeIndexer intakeIndexer) {
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

  public Command downTakeIndexunil() {
    return downTakeIndex().until(() -> intakeIndexer.isCorakIn() && intakePitch.getAngle() > 60);
  }

  public Command upNOTakeNOIndex() {
    return intakeIndexer
        .changestateCommand(IntakeIndexerState.STOP)
        .alongWith(Intakegriper.changeStateCommand(inakegriperstate.KeepItIn))
        .alongWith(intakePitch.changestateCommand(IntakePitchstate.ZERO_POSITION));
  }

  public Command upNOTakeNOIndexuntil() {
    return intakeIndexer
        .changestateCommand(IntakeIndexerState.STOP)
        .alongWith(Intakegriper.changeStateCommand(inakegriperstate.KeepItIn))
        .alongWith(intakePitch.changestateCommandMustHaveUntil(IntakePitchstate.ZERO_POSITION));
  }

  public Command upNOTakeIndex() {
    return intakeIndexer
        .changestateCommand(IntakeIndexerState.RUN)
        .alongWith(Intakegriper.changeStateCommand(inakegriperstate.KeepItIn))
        .alongWith(intakePitch.changestateCommand(IntakePitchstate.ZERO_POSITION));
  }

  public Command upTakeIndex() {
    return intakeIndexer
        .changestateCommandMustHaveUntil(IntakeIndexerState.RUN)
        .alongWith(Intakegriper.changeStateCommand(inakegriperstate.Collect))
        .alongWith(intakePitch.changestateCommand(IntakePitchstate.ZERO_POSITION));
  }

  public Command intakeCommand() {

    return downTakeIndexunil()
        .andThen(upTakeIndex().until(() -> intakePitch.getAngle() < 40))
        .andThen(upNOTakeNOIndexuntil().until(() -> intakePitch.getAngle() < 20));
  }

  public Command scoreL1Command() {
    return intakePitch
        .changestateCommand(IntakePitchstate.L1)
        .alongWith(
            intakeIndexer
                .changestateCommandMustHaveUntil(IntakeIndexerState.STOP)
                .until(() -> intakePitch.getAngle() > 30))
        .andThen(
            Intakegriper.changestateCommandMustHaveUntil(inakegriperstate.Eject)
                .until(() -> !intakePitch.isClosed())
                .andThen(Commands.waitSeconds(0.5))
                .andThen(resetCommand()));
  }

  public Command resetCommand() {
    return intakePitch
        .changestateCommand(IntakePitchstate.ZERO_POSITION)
        .alongWith(intakeIndexer.changestateCommand(IntakeIndexerState.STOP))
        .andThen(Intakegriper.changeStateCommand(inakegriperstate.KeepItIn));
  }
}
