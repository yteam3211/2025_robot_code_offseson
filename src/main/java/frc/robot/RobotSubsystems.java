package frc.robot;

import static edu.wpi.first.units.Units.Kilogram;
import static edu.wpi.first.units.Units.Meter;
import static edu.wpi.first.units.Units.Meters;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.system.plant.DCMotor;
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
import frc.robot.subsystems.drive.ModuleIOMaple;
import frc.robot.subsystems.drive.ModuleIOTalonFX;
import frc.robot.subsystems.drive.TunerConstants;
import frc.robot.subsystems.elevatorsim.elevator2;
import frc.robot.subsystems.elevatorsim.elevatorIOreal;
import frc.robot.subsystems.elevatorsim.elevatorIOsim;
import frc.robot.subsystems.intakeGriper.IntakeGriper;
import frc.robot.subsystems.vision.Vision;
import frc.robot.subsystems.vision.VisionIOLimelight;
import frc.robot.subsystems.vision.VisionIOPhotonVisionSim;
import org.ironmaple.simulation.SimulatedArena;
import org.ironmaple.simulation.drivesims.COTS;
import org.ironmaple.simulation.drivesims.SwerveDriveSimulation;
import org.ironmaple.simulation.drivesims.configs.DriveTrainSimulationConfig;

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
  private DriveTrainSimulationConfig driveTrainSimulationConfig = null;

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
      driveTrainSimulationConfig =
          DriveTrainSimulationConfig.Default()
              .withGyro(COTS.ofPigeon2())
              .withRobotMass(Kilogram.of(Drive.ROBOT_MASS_KG))
              .withBumperSize(
                  Meter.of(Math.abs(TunerConstants.FrontRight.LocationX) + 0.04),
                  Meter.of(Math.abs(TunerConstants.FrontRight.LocationY) + 0.04))
              .withSwerveModule(
                  COTS.ofMark4i(
                      DCMotor.getFalcon500(1), DCMotor.getKrakenX60(1), Drive.WHEEL_COF, 2))
              .withTrackLengthTrackWidth(
                  Meters.of(Math.abs(TunerConstants.FrontRight.LocationX)),
                  Meter.of(Math.abs(TunerConstants.FrontRight.LocationY)));
      SwerveDriveSimulation driveSimulation =
          new SwerveDriveSimulation(driveTrainSimulationConfig, new Pose2d(2, 2, new Rotation2d()));
      drive =
          new Drive(
              new GyroIO() {},
              new ModuleIOMaple(driveSimulation.getModules()[0]),
              new ModuleIOMaple(driveSimulation.getModules()[1]),
              new ModuleIOMaple(driveSimulation.getModules()[2]),
              new ModuleIOMaple(driveSimulation.getModules()[3]));
      SimulatedArena.getInstance().addDriveTrainSimulation(driveSimulation);
      //   drive =
      //       new Drive(
      //           new GyroIO() {},
      //           new ModuleIOSim(TunerConstants.FrontLeft),
      //           new ModuleIOSim(TunerConstants.FrontRight),
      //           new ModuleIOSim(TunerConstants.BackLeft),
      //           new ModuleIOSim(TunerConstants.BackRight));
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
    IntakeGriper = new IntakeGriper();
    intakeindexer = new IntakeIndexer();
    // swerve = new SwerveSubsystem();
  }
}
