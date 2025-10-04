package frc.robot.states;

import frc.lib.util.ITarget;

public enum Elevatorstates implements ITarget {
  INTAKE_MODE_FIRST(115),
  INTAKE_MODE_SECOND(102),
  SAFE_ZONE(70),
  REST_MODE(0),
  Threshold(0.1),
  L2(0);

  private double m_hight;

  Elevatorstates(double hight) {
    m_hight = hight;
  }

  @Override
  public double getTarget() {
    return m_hight;
  }
}
