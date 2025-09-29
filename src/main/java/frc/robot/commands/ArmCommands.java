package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.states.Elevatorstates;
import frc.robot.states.IntakePitchstate;
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

  public Command elevatorUpAfterPitchDwon() {
    return intakePitch
        .changestateCommand(IntakePitchstate.L1)
        .alongWith(elevator.changestaeCommand(Elevatorstates.INTAKE_MODE));
  }
}
