package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.arm.armsubsystem;
import frc.robot.subsystems.elvetor.elevatorsubsystem;
import frc.robot.subsystems.intake.IntakeSubsystem;

public class ArmCommands {
  private armsubsystem armSubsystem;
  private elevatorsubsystem elevatorSubsystem;
  private IntakeSubsystem intakeSubsystem;
  private IntakePitch intakePitch;

  public ArmCommands(
      armsubsystem armSubsystem,
      elevatorsubsystem elevatorSubsystem,
      IntakeSubsystem intakeSubsystem,
      IntakePitch intakePitch) {
    this.armSubsystem = armSubsystem;
    this.intakePitch = intakePitch;
    this.intakeSubsystem = intakeSubsystem;
    this.elevatorSubsystem = elevatorSubsystem;
  }

  public Command armAndElevatorToIntakeMode() {
    return elevatorSubsystem
        .setHeightCommand(() -> 1.2)
        .until(() -> elevatorSubsystem.getHeight() >= 1.2)
        .andThen(() -> elevatorsubsystem.currentHeight = 1.2)
        .andThen(armSubsystem.setToIntakePos().asProxy())
        .andThen(intakePitch.setIntakeToZeroPosCommand().asProxy());
  }

  private Command gripperToArm() {
    return intakeSubsystem
        .setGripperToEjectCommand()
        .alongWith(armSubsystem.setGriperToCollectCommand())
        .asProxy();
  }

  public Command coralFromIntakeToArmCommand() {
    return armAndElevatorToIntakeMode().andThen(gripperToArm());
  }
}
