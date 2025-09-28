package frc.robot.commands;

import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.arm.ArmGriper;
import frc.robot.subsystems.elvetor.elevator;
import frc.robot.subsystems.intakeGriper.IntakeGriper;

public class ArmCommands {
  private ArmGriper armSubsystem;
  private elevator elevatorSubsystem;
  private IntakeGriper intakeSubsystem;
  private IntakePitch intakePitch;

  public ArmCommands(
      ArmGriper armSubsystem,
      elevator elevatorSubsystem,
      IntakeGriper intakeSubsystem,
      IntakePitch intakePitch) {
    this.armSubsystem = armSubsystem;
    this.intakePitch = intakePitch;
    this.intakeSubsystem = intakeSubsystem;
    this.elevatorSubsystem = elevatorSubsystem;
  }
}
