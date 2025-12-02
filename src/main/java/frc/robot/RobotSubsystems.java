package frc.robot;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.RobotBase;
import frc.robot.subsystems.Aempitch.armPitch;
import frc.robot.subsystems.IntakeIndexer.IntakeIndexer;
import frc.robot.subsystems.IntakePitchSim.IntakePitchIOSim;
import frc.robot.subsystems.IntakePitchSim.IntakePitchIOreal;
import frc.robot.subsystems.IntakePitchSim.IntakePitchSim;
import frc.robot.subsystems.armGruper.ArmGriper;
import frc.robot.subsystems.climb.ClimbSubsystem;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIOCanAndGyro;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.drive.ModuleIOTalonFX;
import frc.robot.subsystems.drive.TunerConstants;
import frc.robot.subsystems.elevatorsim.elevator2;
import frc.robot.subsystems.elevatorsim.elevatorIOreal;
import frc.robot.subsystems.elevatorsim.elevatorIOsim;
import frc.robot.subsystems.intakeGriper.IntakeGriper;
import frc.robot.subsystems.vision.Vision;
import frc.robot.subsystems.vision.VisionIOLimelight;
import frc.robot.subsystems.vision.VisionIOPhotonVisionSim;

public class RobotSubsystems {
  // subsystems
  // public final elevator elevator;
  public final elevator2 elevator2;
  public final ArmGriper ArmGriper;
  public final armPitch armpitch;
  public final IntakePitchSim IntakePitchSim;
  public final IntakeGriper IntakeGriper;
  // public final IntakePitch intakepitch;
  public final IntakeIndexer intakeindexer;
  // public final SwerveSubsystem swerve;
  public final Drive drive;
  public final ClimbSubsystem ClimbSubsystem;
  public final Vision vision;

  public RobotSubsystems() {
    if (RobotBase.isReal()) {
      drive =
          new Drive(
              new GyroIOCanAndGyro(),
              new ModuleIOTalonFX(TunerConstants.FrontLeft),
              new ModuleIOTalonFX(TunerConstants.FrontRight),
              new ModuleIOTalonFX(TunerConstants.BackLeft),
              new ModuleIOTalonFX(TunerConstants.BackRight));
      vision =
          new Vision(
              drive::addVisionMeasurement,
              new VisionIOLimelight("limelight-right", drive.getPose()::getRotation),
              new VisionIOLimelight("limelight-left", drive.getPose()::getRotation));
      elevator2 = new elevator2(new elevatorIOreal());
      IntakePitchSim = new IntakePitchSim(new IntakePitchIOreal());
    } else {
      drive =
          new Drive(
              new GyroIO() {},
              new ModuleIOSim(TunerConstants.FrontLeft),
              new ModuleIOSim(TunerConstants.FrontRight),
              new ModuleIOSim(TunerConstants.BackLeft),
              new ModuleIOSim(TunerConstants.BackRight));
      vision =
          new Vision(
              drive::addVisionMeasurement,
              new VisionIOPhotonVisionSim(
                  "limelight-right", new Transform3d(new Pose3d(), new Pose3d()), drive::getPose),
              new VisionIOPhotonVisionSim(
                  "limelight-left", new Transform3d(new Pose3d(), new Pose3d()), drive::getPose));
      IntakePitchSim = new IntakePitchSim(new IntakePitchIOSim());
      elevator2 = new elevator2(new elevatorIOsim());
    }
    ClimbSubsystem = new ClimbSubsystem();
    ArmGriper = new ArmGriper();
    armpitch = new armPitch();
    // swerve = new SwerveSubsystem();
    IntakeGriper = new IntakeGriper();
    intakeindexer = new IntakeIndexer();
    // swerve = new SwerveSubsystem();
  }
}
