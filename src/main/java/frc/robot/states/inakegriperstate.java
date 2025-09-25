package frc.robot.states;

import frc.lib.util.ITarget;

public enum inakegriperstate implements ITarget {
  KeepItIn(0),
  Collect(-1),
  Eject(1);

  private double m_velocity;

  inakegriperstate(double velocity) {
    m_velocity = velocity;
  }

  @Override
  public double getTarget() {
    return m_velocity;
  }
}
