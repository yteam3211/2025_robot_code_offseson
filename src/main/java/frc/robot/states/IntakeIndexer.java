package frc.robot.states;

import frc.lib.util.ITarget;

public enum IntakeIndexer implements ITarget {
  RUN(-0.25),
  STOP(0);

  double velocity;

  private IntakeIndexer(double velocity) {
    this.velocity = velocity;
  }

  @Override
  public double getTarget() {
    return velocity;
  }
}
