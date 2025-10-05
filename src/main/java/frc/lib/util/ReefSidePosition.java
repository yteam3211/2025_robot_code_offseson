package frc.lib.util;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class ReefSidePosition {

  private Translation2d m_center;
  private Translation2d m_left;
  private Translation2d m_right;
  private Translation2d m_farPose;

  private Rotation2d m_angle;
  // private Rotation2d m_resetPoseAngle;
  private String m_name;

  public ReefSidePosition(
      Translation2d farPose,
      Translation2d center,
      Translation2d left,
      Translation2d right,
      Rotation2d angle,
      String name) {
    m_farPose = farPose;
    m_center = center;
    m_left = left;
    m_right = right;
    m_angle = angle;
    m_name = name;
  }

  public Translation2d getFarPose() {
    return m_farPose;
  }

  public Translation2d getCenter() {
    return m_center;
  }

  public Translation2d getLeft() {
    return m_left;
  }

  public Translation2d getRight() {
    return m_right;
  }

  public Rotation2d getAngle() {
    return m_angle;
  }

  // public Rotation2d getResetPoseAngle() {
  //   return m_resetPoseAngle;
  // }

  public String getName() {
    return m_name;
  }
}
