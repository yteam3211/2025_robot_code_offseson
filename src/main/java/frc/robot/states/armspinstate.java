package frc.robot.states;

import frc.lib.util.ITarget;

public enum armspinstate implements ITarget {
  collect(180),
  eject(45),
  stop(0);


  private double m_degrees ;

  armspinstate(double degrees ) {
    m_degrees  = degrees ;
  }

  @Override
  public double getTarget() {
    return m_degrees ;
  }
}
