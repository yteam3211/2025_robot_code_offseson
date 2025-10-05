package frc.robot.states;

import frc.lib.util.ITarget;

public enum armgriperstate implements ITarget {
  KeepItIn(0),
  Collect(-0.4),
  Eject(0.1);

  private double m_velocity;

  armgriperstate(double velocity) {
    m_velocity = velocity;
  }

  @Override
  public double getTarget() {
    return m_velocity;
  }
}
