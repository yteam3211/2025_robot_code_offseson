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
import frc.robot.subsystems.arm.ArmGriper;
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
    return elevatorUpAfterPitchDwon().andThen(passToArmFromintake());
  }

  public Command elevatorUpAfterPitchDwon() {
    return intakePitch
        .changestateCommand(IntakePitchstate.L1)
        .alongWith(
            elevator
                .changestateCommandMustHaveUntil(Elevatorstates.INTAKE_MODE)
                .until(() -> elevator.getHeight() > 50)
                .andThen(
                    armpitch
                        .chengestateCommand(armPitchState.COLLECT)
                        .alongWith(
                            intakePitch.changestateCommandMustHaveUntil(
                                IntakePitchstate.ZERO_POSITION)))
                .until(() -> armpitch.getArmPosition() < 160));
  }

  public Command passToArmFromintake() {
    return Armgriper.changestateCommand(armgriperstate.Collect)
        .alongWith(intakegriper.changeStateCommand(inakegriperstate.Eject));
  }

  public Command restArm() {
    return armpitch.chengestateCommand(armPitchState.rest);
  }

  public Command restAfterPass() {
    return restArm()
        .alongWith(Commands.waitSeconds(3))
        .andThen(
            intakegriper
                .changeStateCommand(inakegriperstate.KeepItIn)
                .alongWith(Armgriper.changestateCommand(armgriperstate.KeepItIn))
                .andThen(elevator.changeStateCommand(Elevatorstates.REST_MODE)));
  }
}
