package frc.robot.states;

import frc.lib.util.ITarget;

public enum armPitchState implements ITarget {
  COLLECT(-90),
  Score(0.125),
  rest(0);

  private double m_roatoion;

  armPitchState(double roatoion) {
    m_roatoion = roatoion;
  }

  @Override
  public double getTarget() {
    return m_roatoion;
  }
}
