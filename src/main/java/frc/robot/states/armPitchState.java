package frc.robot.states;

import frc.lib.util.ITarget;

public enum armPitchState implements ITarget {
  COLLECT(-180),
  L2(-57),
  L3(-57),
  rest(2.5),
  firtstinit(0),
  L4(-57),
  alge(-90);

  private double m_degreeds;

  armPitchState(double degreeds) {
    m_degreeds = degreeds;
  }

  @Override
  public double getTarget() {
    return m_degreeds;
  }
}
