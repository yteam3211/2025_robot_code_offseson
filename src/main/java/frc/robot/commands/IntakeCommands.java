package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.intake.IntakeSubsystem;

public class IntakeCommands {
  private IntakePitch intakePitch;
  private IntakeSubsystem intakeSubsystem;

  public IntakeCommands(IntakeSubsystem intakeSubsystem, IntakePitch intakePitch) {
    this.intakePitch = intakePitch;
    this.intakeSubsystem = intakeSubsystem;
  }

  private ParallelCommandGroup downCenterInake() {
    return intakePitch
        .setIntakeToIntakePosCommand()
        .alongWith(intakeSubsystem.setGripperToCollectCommand().asProxy())
        .alongWith(intakeSubsystem.setIndexerToRunCommand().asProxy());
  }

  private Command intakeUntilcoral() {
    // return downCenterInake().until(intakeSubsystem::isCoralIn).withTimeout(10);
    return downCenterInake().withTimeout(10);
  }

  public ParallelCommandGroup UpCenterIntake() {
    return intakeUntilcoral()
        .andThen(intakePitch.setIntakeToMiddlePosCommand())
        .alongWith(intakeSubsystem.setGripperToKeepItInCommand().asProxy())
        .alongWith(intakeSubsystem.setIndexerToRunCommand().asProxy());
  }

  public ParallelCommandGroup intakeCommand() {
    return UpCenterIntake();
  }
}
