package frc.robot.states;

import frc.lib.util.ITarget;

public enum inakespinstate implements ITarget {
  ground(0),
  up(180);

  private double m_degrees;

  inakespinstate(double degrees) {
    m_degrees = degrees;
  }

  @Override
  public double getTarget() {
    return m_degrees;
  }
}
