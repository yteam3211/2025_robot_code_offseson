package frc.robot;

import frc.robot.subsystems.Aempitch.armPitch;
import frc.robot.subsystems.IntakeIndexer.IntakeIndexer;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.armGruper.ArmGriper;
import frc.robot.subsystems.climb.ClimbSubsystem;
import frc.robot.subsystems.elvetor.elevator;
import frc.robot.subsystems.intakeGriper.IntakeGriper;
import frc.robot.subsystems.swerve.SwerveSubsystem;

public class RobotSubsystems {
  // subsystems
  public final elevator elevator;
  public final ArmGriper ArmGriper;
  public final armPitch armpitch;
  public final IntakeGriper IntakeGriper;
  public final IntakePitch intakepitch;
  public final IntakeIndexer intakeindexer;
  public final SwerveSubsystem swerve;
  public final ClimbSubsystem ClimbSubsystem;

  public RobotSubsystems() {
    if (RobotBase.isReal()) {
      ArmGriper = new ArmGriper();
      armpitch = new armPitch();
      swerve = new SwerveSubsystem();
      IntakeGriper = new IntakeGriper();
      intakepitch = new IntakePitch();
      intakeindexer = new IntakeIndexer();
      elevator = new elevator2(new elevatorIOreal());
      ClimbSubsystem = new ClimbSubsystem();
    } else {
      ArmGriper = new ArmGriper();
      armpitch = new armPitch();
      swerve = new SwerveSubsystem();
      IntakeGriper = new IntakeGriper();
      intakepitch = new IntakePitch();
      intakeindexer = new IntakeIndexer();
      elevator = new elevator2(new elevatorIOsim());
      ClimbSubsystem = new ClimbSubsystem();
    }
  }
}
