package frc.robot.states;

import frc.lib.util.ITarget;

public enum Elavatorstates implements ITarget {
  Close(0),
  Human(0.03),
  L1(0),
  L2(0.43),
  L3(0.8),
  L4(1.5),
  SafeZone(0),
  Threshold(0.075),
  Net(2);

  private double m_hight;

  Elavatorstates(double hight) {}

  @Override
  public double getTarget() {
    return m_hight;
  }
}
