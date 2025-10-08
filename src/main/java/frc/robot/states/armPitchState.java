package frc.robot.states;

import frc.lib.util.ITarget;

public enum armPitchState implements ITarget {
  COLLECT(-180),
  L2(-57),
  L2first(-30),
  L3(-57),
  L3first(-30),
  rest(2.5),
  firtstinit(0),
  L4(-57),
  L4first(-30),
  alge(-90),
  net(45);

  private double m_degreeds;

  armPitchState(double degreeds) {
    m_degreeds = degreeds;
  }

  @Override
  public double getTarget() {
    return m_degreeds;
  }
}
