package frc.robot.subsystems.swerve;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SwerveConstants;
import frc.robot.LimelightHelpers;


// https://chatgpt.com/share/68d6235c-2e5c-8005-810e-ac7aaeb0b93f YOU MAY NEED TO EDIT THE CODE BUT THE IDEAS ARE THERE. MAKE SURE THE SWERVE DOESNT DRIVE LIKE A TANK (that you can drive and spin while moving straight)

public class SwerveSubsystem extends SubsystemBase {

  private final SwerveModule[] m_swerveMods;
  private final AHRS m_gyro;

  private final SwerveDrivePoseEstimator m_poseEstimator;

  public SwerveSubsystem() {
    m_gyro = new AHRS(NavXComType.kMXP_SPI);

    m_swerveMods = new SwerveModule[] {
        new SwerveModule(0, SwerveConstants.Mod0.constants),
        new SwerveModule(1, SwerveConstants.Mod1.constants),
        new SwerveModule(2, SwerveConstants.Mod2.constants),
        new SwerveModule(3, SwerveConstants.Mod3.constants)
    };

    m_poseEstimator =
        new SwerveDrivePoseEstimator(
            SwerveConstants.SWERVE_KINEMATICS,
            getGyroYaw(),
            getModulePositions(),
            new Pose2d());

    try {
      AutoBuilder.configureHolonomic(
          this::getPose,
          this::resetPose,
          this::getChassisSpeeds,
          this::driveRobotRelative,
          new PPHolonomicDriveController(
              new PIDConstants(5.0, 0.0, 0.0),
              new PIDConstants(5.0, 0.0, 0.0)),
          RobotConfig.fromGUISettings(),
          () -> false,
          this);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /* ----------------- Basic getters ----------------- */

  public Pose2d getPose() {
    return m_poseEstimator.getEstimatedPosition();
  }

  public void resetPose(Pose2d pose) {
    m_poseEstimator.resetPosition(getGyroYaw(), getModulePositions(), pose);
  }

  public Rotation2d getGyroYaw() {
    // WPILib convention: CCW+ ; NavX yaw is CW+
    return Rotation2d.fromDegrees(-m_gyro.getYaw());
  }

  public SwerveModuleState[] getModuleStates() {
    SwerveModuleState[] states = new SwerveModuleState[m_swerveMods.length];
    for (SwerveModule mod : m_swerveMods) {
      states[mod.moduleNumber] = mod.getState();
    }
    return states;
  }

  public SwerveModulePosition[] getModulePositions() {
    SwerveModulePosition[] positions = new SwerveModulePosition[m_swerveMods.length];
    for (SwerveModule mod : m_swerveMods) {
      positions[mod.moduleNumber] = mod.getPosition();
    }
    return positions;
  }

  /* ----------------- Driving ----------------- */

  public void drive(
      Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {

    Rotation2d robotYaw = getGyroYaw(); // USE GYRO, not odometry rotation

    ChassisSpeeds speeds =
        fieldRelative
            ? ChassisSpeeds.fromFieldRelativeSpeeds(
                translation.getX(), translation.getY(), rotation, robotYaw)
            : new ChassisSpeeds(translation.getX(), translation.getY(), rotation);

    SwerveModuleState[] swerveModuleStates =
        SwerveConstants.SWERVE_KINEMATICS.toSwerveModuleStates(speeds);
    SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, SwerveConstants.MAX_SPEED);
    for (SwerveModule mod : m_swerveMods) {
      mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
    }
  }

  public void driveRobotRelative(ChassisSpeeds speeds) {
    SwerveModuleState[] swerveModuleStates =
        SwerveConstants.SWERVE_KINEMATICS.toSwerveModuleStates(speeds);
    SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, SwerveConstants.MAX_SPEED);
    for (SwerveModule mod : m_swerveMods) {
      mod.setDesiredState(swerveModuleStates[mod.moduleNumber], false);
    }
  }

  public ChassisSpeeds getChassisSpeeds() {
    return SwerveConstants.SWERVE_KINEMATICS.toChassisSpeeds(getModuleStates());
  }

  /* ----------------- Vision (MegaTag2) ----------------- */

  public void updateMegaTag2Pose() {
    double robotYawDeg = getGyroYaw().getDegrees();

    // supply gyro yaw so Limelights know current robot orientation
    LimelightHelpers.SetRobotOrientation("limelight-left", robotYawDeg, 0, 0, 0, 0, 0);
    LimelightHelpers.SetRobotOrientation("limelight-right", robotYawDeg, 0, 0, 0, 0, 0);

    LimelightHelpers.PoseEstimate mt2Left =
        LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight-left");
    LimelightHelpers.PoseEstimate mt2Right =
        LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight-right");

    // skip if spinning too fast
    if (Math.abs(m_gyro.getRate()) > 200.0) return;

    var visionStdDevs = VecBuilder.fill(0.7, 0.7, 0.05);

    if (mt2Left != null && mt2Left.tagCount > 0) {
      m_poseEstimator.setVisionMeasurementStdDevs(visionStdDevs);
      m_poseEstimator.addVisionMeasurement(mt2Left.pose, mt2Left.timestampSeconds);
      SmartDashboard.putNumber("LLLeftYaw", mt2Left.pose.getRotation().getDegrees());
    }

    if (mt2Right != null && mt2Right.tagCount > 0) {
      m_poseEstimator.setVisionMeasurementStdDevs(visionStdDevs);
      m_poseEstimator.addVisionMeasurement(mt2Right.pose, mt2Right.timestampSeconds);
      SmartDashboard.putNumber("LLRightYaw", mt2Right.pose.getRotation().getDegrees());
    }
  }

  /* ----------------- Periodic ----------------- */

  @Override
  public void periodic() {
    // update odometry
    m_poseEstimator.update(getGyroYaw(), getModulePositions());

    // update vision if tags are visible
    updateMegaTag2Pose();

    // debug outputs
    SmartDashboard.putNumber("GyroYaw", getGyroYaw().getDegrees());
    SmartDashboard.putNumber("GyroRate", m_gyro.getRate());
    SmartDashboard.putNumber("PoseYaw", getPose().getRotation().getDegrees());
    SmartDashboard.putNumber("PoseX", getPose().getX());
    SmartDashboard.putNumber("PoseY", getPose().getY());
  }
}