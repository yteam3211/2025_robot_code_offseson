package frc.robot.states;

import frc.lib.util.ITarget;

public enum armgriperstatestate implements ITarget {
  KeepItIn(0),
  Collect(-1),
  Eject(1);

  private double m_velocity;

  armgriperstatestate(double velocity) {
    m_velocity = velocity;
  }

  @Override
  public double getTarget() {
    return m_velocity;
  }
}
