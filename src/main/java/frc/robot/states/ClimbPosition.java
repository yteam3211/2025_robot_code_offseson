package frc.robot.states;

import frc.lib.util.ITarget;

public enum ClimbPosition implements ITarget {
  Move(85),
  MoveFast(10),
  Hold(0);

  private double m_angle;

  private ClimbPosition(double angle) {
    m_angle = angle;
  }

  @Override
  public double getTarget() {
    return m_angle;
  }
}
