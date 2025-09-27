package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.intakeGriper.IntakeGriper;

public class IntakeCommands {
  private IntakePitch intakePitch;
  private IntakeGriper intakeSubsystem;

  public IntakeCommands(IntakeGriper intakeSubsystem, IntakePitch intakePitch) {
    this.intakePitch = intakePitch;
    this.intakeSubsystem = intakeSubsystem;
  }
}
