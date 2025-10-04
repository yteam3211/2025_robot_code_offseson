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
  private ArmGriper Armgriper;
  private armPitch armpitch;
  private elevator elevator;
  private IntakeGriper intakegriper;
  private IntakePitch intakePitch;

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
    return elevator
        .changestateCommandMustHaveUntil(Elevatorstates.INTAKE_MODE_FIRST)
        .until(elevator.isAtLestHight(50));
  }

  public Command elevatorUphDwonArmTopos() {
    return elevatorUpDwon()
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
    Commands.runOnce(() -> System.out.println("restAfterPass")).repeatedly();
    return armpitch
        .chengestateCommandMustHaveUntil(armPitchState.rest)
        .until(armpitch.isAtLestpos(-10))
        .andThen(
            elevator
                .setToZeroPosion()
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
        .changeStateCommand(Elevatorstates.L2)
        .alongWith(armpitch.chengestateCommand(armPitchState.L2));
  }

  public Command resetcommand() {
    return armpitch
        .chengestateCommandMustHaveUntil(armPitchState.rest)
        .until(() -> armpitch.getArmPosition() > -30)
        .andThen(
            elevator
                .setToZeroPosion()
                .alongWith(Armgriper.changestateCommand(armgriperstate.KeepItIn)));
  }
}
