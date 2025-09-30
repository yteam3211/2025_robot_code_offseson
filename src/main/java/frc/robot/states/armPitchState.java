package frc.robot.states;

import frc.lib.util.ITarget;

public enum armPitchState implements ITarget {
  COLLECT(-180),
  L2(46),
  rest(0);

  private double m_degreeds;

  armPitchState(double degreeds) {
    m_degreeds = degreeds;
  }

  @Override
  public double getTarget() {
    return m_degreeds;
  }
}
