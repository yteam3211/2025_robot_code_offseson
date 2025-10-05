package frc.robot.states;

import edu.wpi.first.math.util.Units;
import frc.lib.util.ITarget;

public enum Elevatorstates implements ITarget {
  INTAKE_MODE_FIRST(120.0),
  INTAKE_MODE_SECOND(105.0),
  SAFE_ZONE(70.0),
  REST_MODE(0.0),
  Threshold(0.1),
  L2(0.0),
  L1(70.0),
  L3(47.0),
  L4(Units.inchesToMeters(72) * 100);

  private double m_hight;

  Elevatorstates(double hight) {
    m_hight = hight;
  }

  @Override
  public double getTarget() {
    return m_hight;
  }
}
