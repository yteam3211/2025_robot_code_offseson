package frc.robot.states;

import frc.lib.util.ITarget;

public enum Elevatorstates implements ITarget {
  Close(0.17),
  Human(0.03),
  L1(0.17),
  L2(0.43),
  L3(0.8),
  L4(1.5),
  SafeZone(0),
  Threshold(0.075),
  Net(2);

  private double m_hight;

  Elevatorstates(double hight) {}

  @Override
  public double getTarget() {
    return m_hight * 2000;
  }
}
