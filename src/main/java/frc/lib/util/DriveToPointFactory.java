package frc.lib.util;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.swerve.DriveCommands;
import frc.robot.subsystems.drive.Drive;
import java.util.function.Supplier;

public class DriveToPointFactory {
  private final Drive swerve;

  public DriveToPointFactory(Drive swerve) {
    this.swerve = swerve;
  }

  /** PathPlanner constraints for auto-driving */
  private PathConstraints getConstraints() {
    return new PathConstraints(
        2, // 3.0
        3, // 4.0 // max vel, accel (m/s, m/s^2)
        Units.Degrees.of(540).in(Units.Radians), // max ang vel rad/s
        Units.Degrees.of(720).in(Units.Radians) // max ang accel rad/s^2
        );
  }

  public Command fineAlign(Supplier<Pose2d> Target) {
    // if (DriverStation.getAlliance().get() == Alliance.Red) {
    //   Target = Target.div(-1);
    // }
    PIDController xPID = new PIDController(5, 0, 0);
    PIDController yPID = new PIDController(5, 0, 0);
    PIDController rotPID = new PIDController(5, 0, 0);
    rotPID.enableContinuousInput(-Math.PI, Math.PI);

    return swerve
        .run(
            () -> {
              Pose2d current = swerve.getPose();
              double xOut = xPID.calculate(current.getX(), Target.get().getY());
              double yOut = yPID.calculate(current.getY(), Target.get().getY());
              double rotOut =
                  rotPID.calculate(
                      current.getRotation().getRadians(), Target.get().getRotation().getRadians());

              DriveCommands.joystickDrive(swerve, () -> xOut, () -> yOut, () -> rotOut);
            })
        .until(
            () -> {
              Pose2d error = Target.get().relativeTo(swerve.getPose());
              return Math.abs(error.getX()) < 0.2
                  && Math.abs(error.getY()) < 0.2
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

    return pathfind.andThen(fineAlign(() -> targetPose));
  }

  public Command driveToPosesimple(Pose2d targetPose) {
    Command pathfind = AutoBuilder.pathfindToPose(targetPose, getConstraints(), 0.0);
    return pathfind;
  }
}
