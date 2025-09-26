package frc.lib.util;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swerve.SwerveSubsystem;

public class DriveToPointFactory {
  private final SwerveSubsystem swerve;

  public DriveToPointFactory(SwerveSubsystem swerve) {
    this.swerve = swerve;
  }

  /** PathPlanner constraints for auto-driving */
  private PathConstraints getConstraints() {
    return new PathConstraints(
        3.0,
        4.0, // max vel, accel (m/s, m/s^2)
        Units.Degrees.of(540).in(Units.Radians), // max ang vel rad/s
        Units.Degrees.of(720).in(Units.Radians) // max ang accel rad/s^2
        );
  }

  /** Builds a fine PID settle command after pathfinding */
  private Command fineAlign(Pose2d target) {
    PIDController xPID = new PIDController(0, 0, 0);
    PIDController yPID = new PIDController(0, 0, 0);
    PIDController rotPID = new PIDController(0, 0, 0);
    rotPID.enableContinuousInput(-Math.PI, Math.PI);

    return swerve
        .run(
            () -> {
              Pose2d current = swerve.getPose();
              double xOut = xPID.calculate(current.getX(), target.getX());
              double yOut = yPID.calculate(current.getY(), target.getY());
              double rotOut =
                  rotPID.calculate(
                      current.getRotation().getRadians(), target.getRotation().getRadians());

              swerve.drive(new Translation2d(xOut, yOut), rotOut, true, false);
            })
        .until(
            () -> {
              Pose2d error = target.relativeTo(swerve.getPose());
              return Math.abs(error.getX()) < 0.05
                  && Math.abs(error.getY()) < 0.05
                  && Math.abs(error.getRotation().getRadians()) < Math.toRadians(3);
            })
        .finallyDo(() -> swerve.stop());
  }

  /** Builds a full drive-to-pose command (pathfind + PID settle) */
  public Command driveToPose(Pose2d targetPose) {
    Command pathfind =
        AutoBuilder.pathfindToPose(
            targetPose, getConstraints(), 0.0 // end velocity
            );

    return pathfind.andThen(fineAlign(targetPose));
  }
}
