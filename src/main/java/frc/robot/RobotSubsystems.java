package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;
import frc.robot.subsystems.Aempitch.armPitch;
import frc.robot.subsystems.ArmPitchSim.ArmPitchIOSim;
import frc.robot.subsystems.ArmPitchSim.ArmPitchSim;
import frc.robot.subsystems.ArmPitchSim.armPitchIOreal;
import frc.robot.subsystems.IntakeIndexer.IntakeIndexer;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.armGruper.ArmGriper;
import frc.robot.subsystems.climb.ClimbSubsystem;
import frc.robot.subsystems.elevatorsim.elevator2;
import frc.robot.subsystems.elevatorsim.elevatorIOreal;
import frc.robot.subsystems.elevatorsim.elevatorIOsim;
import frc.robot.subsystems.elvetor.elevator;
import frc.robot.subsystems.intakeGriper.IntakeGriper;
import frc.robot.subsystems.swerve.SwerveSubsystem;

public class RobotSubsystems {
  // subsystems
  public final elevator elevator;
  // public final elevator2 elevator2;
  public final ArmGriper ArmGriper;
  public final armPitch armpitch;
  public final ArmPitchSim armPitchSim;
  public final IntakeGriper IntakeGriper;
  public final IntakePitch intakepitch;
  public final IntakeIndexer intakeindexer;
  public final SwerveSubsystem swerve;
  public final ClimbSubsystem ClimbSubsystem;

  public RobotSubsystems() {
    if (RobotBase.isReal()) {
      // elevator2 = new elevator2(new elevatorIOreal());
      armPitchSim = new ArmPitchSim(new armPitchIOreal());
    } else {
      armPitchSim = new ArmPitchSim(new ArmPitchIOSim());
      // elevator2 = new elevator2(new elevatorIOsim());
    }
    elevator = new elevator();
    ClimbSubsystem = new ClimbSubsystem();
    ArmGriper = new ArmGriper();
    armpitch = new armPitch();
    swerve = new SwerveSubsystem();
    IntakeGriper = new IntakeGriper();
    intakepitch = new IntakePitch();
    intakeindexer = new IntakeIndexer();
  }
}
