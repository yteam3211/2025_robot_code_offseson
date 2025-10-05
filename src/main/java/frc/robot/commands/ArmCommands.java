package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.states.Elevatorstates;
import frc.robot.states.IntakePitchstate;
import frc.robot.states.armPitchState;
import frc.robot.states.armgriperstate;
import frc.robot.states.inakegriperstate;
import frc.robot.subsystems.Aempitch.armPitch;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.armGruper.ArmGriper;
import frc.robot.subsystems.elvetor.elevator;
import frc.robot.subsystems.intakeGriper.IntakeGriper;

public class ArmCommands {
  public ArmGriper Armgriper;
  public armPitch armpitch;
  public elevator elevator;
  public IntakeGriper intakegriper;
  public IntakePitch intakePitch;

  public ArmCommands(
      ArmGriper armSubsystem,
      armPitch armpitch,
      elevator elevatorSubsystem,
      IntakeGriper IntakeGriper,
      IntakePitch intakePitch) {
    this.Armgriper = armSubsystem;
    this.intakePitch = intakePitch;
    this.intakegriper = IntakeGriper;
    this.elevator = elevatorSubsystem;
    this.armpitch = armpitch;
  }

  public Command armAndelEvatorCommand() {
    return elevatorUpDwon().andThen(passToArmFromintake());
  }

  public Command elevatorUpDwon() {
    elevator.changeStateCommand(Elevatorstates.INTAKE_MODE_FIRST);
    return elevator
        .changestateCommandMustHaveUntil(Elevatorstates.INTAKE_MODE_FIRST)
        .until(elevator.isAtLestHight(50));
  }

  public Command elevatorUphDwonArmTopos() {
    return /*Commands.runOnce(() -> elevator.stopElevatorcommand())
           .andThen( */ elevatorUpDwon()
        .andThen(armpitch.chengestateCommand(armPitchState.COLLECT))
        .alongWith(intakePitch.changestateCommand(IntakePitchstate.ZERO_POSITION));
  }

  public Command passToArmFromintakeUntil() {
    return elevator
        .changeStateCommand(Elevatorstates.INTAKE_MODE_SECOND)
        .alongWith(
            Armgriper.changestateCommand(armgriperstate.Collect)
                .alongWith(intakegriper.changestateCommandMustHaveUntil(inakegriperstate.Eject)))
        .until(() -> elevator.getHeight() < 102 || Armgriper.isCorakIn().getAsBoolean());
  }

  public Command passToArmFromintake() {
    return passToArmFromintakeUntil().until(Armgriper.isCorakIn());
  }

  public Command restArm() {
    return armpitch.chengestateCommand(armPitchState.rest);
  }

  public Command restAfterPass() {
    return armpitch
        .chengestateCommandMustHaveUntil(armPitchState.rest)
        .until(armpitch.isAtLestpos(-10))
        .andThen(
            elevator
                .changeStateCommand(Elevatorstates.REST_MODE)
                .alongWith(
                    intakegriper
                        .changeStateCommand(inakegriperstate.KeepItIn)
                        .alongWith(Armgriper.changestateCommand(armgriperstate.KeepItIn))));
  }

  public Command fullArmMotion() {
    return elevatorUpDwon()
        .andThen(passToArmFromintake())
        .until(Armgriper.isCorakIn())
        .andThen(restAfterPass());
  }

  public Command scoreL2() {
    return elevator
        .changestateCommandMustHaveUntil(Elevatorstates.L2)
        .until(elevator.isAtLestHight(3))
        .alongWith(
            armpitch
                .chengestateCommandMustHaveUntil(armPitchState.L2)
                .until(armpitch.isLesspos(-45))
                .andThen(Armgriper.changestateCommand(armgriperstate.Eject)));
  }

  public Command resetcommand() {
    return Commands.runOnce(() -> stop())
        .andThen(
            armpitch
                .chengestateCommandMustHaveUntil(armPitchState.rest)
                .until(() -> armpitch.getArmPosition() > -30)
                .andThen(
                    Armgriper.changestateCommand(armgriperstate.KeepItIn)
                        .andThen(elevator.setToZeroPosionCommand())));
  }

  public void stop() {
    armpitch.getCurrentCommand().cancel();
    Armgriper.getCurrentCommand().cancel();
    elevator.getCurrentCommand().cancel();
  }

  public Command scoreL3() {
    return elevator
        .changestateCommandMustHaveUntil(Elevatorstates.L3)
        .until(elevator.isAtLestHight(43))
        .alongWith(
            armpitch
                .chengestateCommandMustHaveUntil(armPitchState.L3)
                .until(armpitch.isLesspos(-45))
                .andThen(Armgriper.changestateCommand(armgriperstate.Eject)));
  }

  public Command scoreL4() {
    return elevator
        .changestateCommandMustHaveUntil(Elevatorstates.L4)
        .until(elevator.isAtLestHight(106))
        .andThen(
            armpitch
                .chengestateCommandMustHaveUntil(armPitchState.L4)
                .until(armpitch.isLesspos(-45))
                .andThen(Armgriper.changestateCommand(armgriperstate.Eject)));
  }

  public Command alegeCommanLow() {
    return elevator
        .changestateCommandMustHaveUntil(Elevatorstates.algelow)
        .until(elevator.isAtLestHight(50))
        .andThen(
            armpitch
                .chengestateCommandMustHaveUntil(armPitchState.alge)
                .until(armpitch.isLesspos(-80))
                .andThen(
                    Armgriper.changestateCommandMustHaveUntil(armgriperstate.Collect)
                        .until(
                            () ->
                                Armgriper.isCorakIn().getAsBoolean()
                                    && armpitch.getArmPosition() < 40)));
  }

  public Command alegeCommanhgih() {
    return elevator
        .changestateCommandMustHaveUntil(Elevatorstates.algehigh)
        .until(elevator.isAtLestHight(50))
        .andThen(
            armpitch
                .chengestateCommandMustHaveUntil(armPitchState.alge)
                .until(armpitch.isLesspos(-80))
                .andThen(
                    Armgriper.changestateCommandMustHaveUntil(armgriperstate.Collect)
                        .until(
                            () ->
                                Armgriper.isCorakIn().getAsBoolean()
                                    && armpitch.getArmPosition() < 40)));
  }
}
