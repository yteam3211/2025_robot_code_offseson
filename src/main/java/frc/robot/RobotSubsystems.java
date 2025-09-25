package frc.robot;

import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.arm.armsubsystem;
import frc.robot.subsystems.elvetor.elevatorsubsystem;
import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.swerve.SwerveSubsystem;

public class RobotSubsystems {
  // subsystems
  public final elevatorsubsystem elevator;
  public final armsubsystem arm;
  public final IntakeSubsystem intake;
  public final SwerveSubsystem swerve;
  public final IntakePitch intakepitch;

  public RobotSubsystems() {
    arm = new armsubsystem();
    swerve = new SwerveSubsystem();
    intake = new IntakeSubsystem();
    intakepitch = new IntakePitch();
    elevator = new elevatorsubsystem();
    // Configure subsystems based on mode

  }
}
