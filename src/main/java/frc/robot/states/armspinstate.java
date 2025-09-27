package frc.robot.states;

import frc.lib.util.ITarget;

public enum armspinstate implements ITarget {
  COLLECT(-90),
  Score(0.125),
  rest(0);

  private double m_roatoion;

  armspinstate(double roatoion) {
    m_roatoion = roatoion;
  }

  @Override
  public double getTarget() {
    return m_roatoion;
  }
}
