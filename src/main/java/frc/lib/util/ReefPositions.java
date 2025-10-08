package frc.lib.util;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class ReefPositions {
  public static final double offset = 17.64;

  private static final ReefSidePosition[] m_targets =
      new ReefSidePosition[] {
        // // Blue Alliance South
        // new ReefSidePosition(
        // new Translation2d(6.14, 4),
        // new Translation2d(5.76, 3.99), // AprilTag Center
        // new Translation2d(3.4499, 4.57), // Left
        // new Translation2d(3.8899000000000004, 4.57), // Right
        // Rotation2d.fromDegrees(-180), // Facing Angle
        // "Blue North"),

        // new ReefSidePosition(
        // new Translation2d(5.28, 5.47),
        // new Translation2d(5.07, 5.2), // AprilTag Center
        // new Translation2d(5.36, 5.18), // Left
        // new Translation2d(5.1, 5.37), // Right
        // Rotation2d.fromDegrees(-120), // Facing Angle
        // "Blue North-West"),

        // new ReefSidePosition(
        // new Translation2d(3.67, 5.47),
        // new Translation2d(3.9, 5.16), // AprilTag Center
        // new Translation2d(3.99, 5.27), // Right
        // new Translation2d(3.64, 5.17), // Left
        // Rotation2d.fromDegrees(-60), // Facing Angle
        // "Blue South-West"),

        // new ReefSidePosition(
        // new Translation2d(2.84, 3.98),
        // new Translation2d(3.2, 4), // AprilTag Center
        // new Translation2d(3.1, 4.23), // Left
        // new Translation2d(3.1, 3.82), // Right
        // Rotation2d.fromDegrees(0), // Facing Angle
        // "Blue South"),

        // new ReefSidePosition(
        // new Translation2d(3.65, 2.6),
        // new Translation2d(3.84, 2.89), // AprilTag Center
        // new Translation2d(5.1198999999999995, 4.75), // Left
        // new Translation2d(4.6799, 4.75), // Right
        // Rotation2d.fromDegrees(60), // Facing Angle
        // "Blue South-East"),

        // new ReefSidePosition(
        // new Translation2d(5.35, 2.59),
        // new Translation2d(5.15, 2.91), // AprilTag Center
        // new Translation2d(4.0699, 4.53), // Left
        // new Translation2d(4.0699, 4.97), // Right
        // Rotation2d.fromDegrees(120), // Facing Angle
        // "Blue North-East"),

        // Blue Alliance South
        new ReefSidePosition(
            new Translation2d(3.715, 2.722),
            new Translation2d(offset - 13.77, 2.91), // AprilTag Center
            new Translation2d(3.673, 2.978), // Left
            new Translation2d(3.957, 2.814), // Right
            Rotation2d.fromDegrees(60), // Mirrored Facing Angle
            "Blue North-East"),
        new ReefSidePosition(
            // new Translation2d(offset - 14.76, 4),
            // new Translation2d(offset - 14.76, 4),
            // new Translation2d(offset - 14.76, 4),
            // new Translation2d(offset - 14.76, 4),

            // is first one
            new Translation2d(2.96, 4.03),
            new Translation2d(offset - 14.439, 3.99), // AprilTag Center
            new Translation2d(3.16, 4.194), // Left
            new Translation2d(3.16, 3.866 + 0.1), // Right
            Rotation2d.fromDegrees(0), // Mirrored Facing Angle
            "Blue North"),
        new ReefSidePosition(
            new Translation2d(3.715, 5.338),
            new Translation2d(offset - 13.69, 5.2), // AprilTag Center
            new Translation2d(3.957, 5.246), // Left
            new Translation2d(3.673, 5.082), // Right
            Rotation2d.fromDegrees(-60), // Mirrored Facing Angle
            "Blue North-West"),
        new ReefSidePosition(
            new Translation2d(5.225, 5.338),
            new Translation2d(offset - 12.52, 5.16), // AprilTag Center
            new Translation2d(5.267, 5.082), // Left
            new Translation2d(4.983, 5.246), // Right
            Rotation2d.fromDegrees(-120), // Mirrored Facing Angle
            "Blue South-West"),
        new ReefSidePosition(
            new Translation2d(5.98, 4.03),
            new Translation2d(offset - 11.82, 4), // AprilTag Center
            new Translation2d(5.78, 3.866), // Left
            new Translation2d(5.78, 4.194), // Right
            Rotation2d.fromDegrees(-180), // Mirrored Facing Angle
            "Blue South"),
        new ReefSidePosition(
            new Translation2d(5.225, 2.722),
            new Translation2d(offset - 12.46, 2.89), // AprilTag Center
            new Translation2d(4.983, 2.814), // Left
            new Translation2d(5.267, 2.978), // Right
            Rotation2d.fromDegrees(120), // Mirrored Facing Angle
            "Blue South-East"),

        // Red Alliance South
        new ReefSidePosition(
            new Translation2d(14.57, 4.03),
            new Translation2d(14.439, 3.99), // AprilTag Center
            new Translation2d(14.37, 3.866), // Left
            new Translation2d(14.37, 4.194), // Right
            Rotation2d.fromDegrees(-180), // Facing Angle // TODO: meassure real angle
            "Red North"),
        new ReefSidePosition(
            new Translation2d(13.815, 5.338),
            new Translation2d(13.69, 5.2), // AprilTag Center
            new Translation2d(13.857, 5.082), // Left
            new Translation2d(13.573, 5.246), // Right
            Rotation2d.fromDegrees(-120), // Facing Angle // TODO: meassure real angle
            "Red North-West"),
        new ReefSidePosition(
            new Translation2d(12.305, 5.338),
            new Translation2d(12.52, 5.16), // AprilTag Center
            new Translation2d(12.547, 5.246), // Left
            new Translation2d(12.263, 5.26), // Right
            Rotation2d.fromDegrees(-60), // Facing Angle // TODO: meassure real angle
            "Red South-West"),
        new ReefSidePosition(
            new Translation2d(11.55, 4.03),
            new Translation2d(11.82, 4), // AprilTag Center
            new Translation2d(11.75, 4.194), // Left
            new Translation2d(11.75, 3.866), // Right
            Rotation2d.fromDegrees(0), // Facing Angle // TODO: meassure real angle
            "Red South"),
        new ReefSidePosition(
            new Translation2d(12.305, 2.722),
            new Translation2d(12.46, 2.89), // AprilTag Center
            new Translation2d(12.263, 2.978), // Left
            new Translation2d(12.547, 2.814), // Right
            Rotation2d.fromDegrees(60), // Facing Angle // TODO: meassure real angle
            "Red South-East"),
        new ReefSidePosition(
            new Translation2d(13.815, 2.722),
            new Translation2d(13.77, 2.91), // AprilTag Center
            new Translation2d(13.573, 2.814), // Left
            new Translation2d(13.857, 2.978), // Right
            Rotation2d.fromDegrees(120), // Facing Angle // TODO: meassure real angle
            "Red North-East")
      };

  public static ReefSidePosition[] getReefPositions() {
    return m_targets;
  }
}
