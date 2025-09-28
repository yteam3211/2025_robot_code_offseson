package frc.robot.states;

import frc.lib.util.ITarget;

public enum Elevatorstates implements ITarget {
  INTAKE_MODE(1.2),
  REST_MODE(0);

  private double m_hight;

  Elevatorstates(double hight) {}

  @Override
  public double getTarget() {
    return m_hight;
  }
}
