package frc.robot.states;

import frc.lib.util.ITarget;

public enum IntakeIndexerState implements ITarget {
  RUN(-0.2),
  STOP(0);

  double velocity;

  private IntakeIndexerState(double velocity) {
    this.velocity = velocity;
  }

  @Override
  public double getTarget() {
    return velocity;
  }
}
