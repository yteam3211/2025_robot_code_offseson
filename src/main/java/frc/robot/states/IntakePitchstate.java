package frc.robot.states;

import frc.lib.util.ITarget;

public enum IntakePitchstate implements ITarget {
  ZERO_POSITION(0),
  INTAKE_POSITION(120),
  L1(40);

  private final double angleDegrees;

  private IntakePitchstate(double angleDegrees) {
    this.angleDegrees = angleDegrees;
  }

  @Override
  public double getTarget() {
    return angleDegrees;
  }
}
