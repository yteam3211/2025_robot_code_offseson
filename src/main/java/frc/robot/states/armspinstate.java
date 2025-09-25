package frc.robot.states;

import frc.lib.util.ITarget;

public enum armspinstate implements ITarget {
  collect(0.5),
  eject(0.125),
  stop(0);

  private double m_roatoion;

  armspinstate(double roatoion) {
    m_roatoion = roatoion;
  }

  @Override
  public double getTarget() {
    return m_roatoion;
  }
}
