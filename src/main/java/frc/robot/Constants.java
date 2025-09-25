// Copyright 2021-2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * This class defines the runtime mode used by AdvantageKit. The mode is always "real" when running
 * on a roboRIO. Change the value of "simMode" to switch between "sim" (physics sim) and "replay"
 * (log replay from a file).
 */
public final class Constants {
  public static final Mode simMode = Mode.SIM;
  public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;

  public static enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,

    /** Replaying from a log file. */
    REPLAY
  }

  public static class elevatorcos {

    public static final int ELEVATOR_CLOSE_SWITCH_PORT = 2;
    public static int masterid = 40;
    public static int slaveid = 41;
    public static final double POSITION_CONVERSION_FACTOR = 48.7;

    public static final class MotorCurrentLimits {

      public static final int SUPPLY_CURRENT_LIMIT = 30;
      public static final boolean SUPPLY_CURRENT_LIMIT_ENABLE = true;
      public static final double SUPPLY_CURRENT_LOWER_LIMIT = 30;
    }

    public static final class MotionMagicConstants {

      public static final double MOTION_MAGIC_VELOCITY = 2;
      public static final double MOTION_MAGIC_ACCELERATION = 2.5;
      public static final double MOTION_MAGIC_JERK = 25;

      public static final double MOTOR_KS = 0.06; // TODO INITILIZE THESE VALUES
      public static final double MOTOR_KA = 0.5;
      public static final double MOTOR_KV = 0.12;
      public static final double MOTOR_KG = 0.03;
      public static final double MOTOR_KP = 60;
      public static final double MOTOR_KI = 0;
      public static final double MOTOR_KD = 0;
    }
  }

  public static final class Armconstants {

    public static final double POSITION_CONVERSION_FACTOR = 98.5714285711;
    public static int m_spinid = 16;
    public static int m_grieprid = 15;

    public final class MotionMagicConstantsspin {
      public static final double MOTION_MAGIC_VELOCITY = 2;
      public static final double MOTION_MAGIC_ACCELERATION = 2.5;
      public static final double MOTION_MAGIC_JERK = 25;

      public static final double MOTOR_KS = 0.045; // TODO INITILIZE THESE VALUES
      public static final double MOTOR_KA = 0.5;
      public static final double MOTOR_KV = 6.5;
      public static final double MOTOR_KG = 0.015;
      public static final double MOTOR_KP = 60;
      public static final double MOTOR_KI = 0;
      public static final double MOTOR_KD = 0;
    }
  }

  public static class intakecos {
    public static final double POSITION_CONVERSION_FACTOR = 1;
    public static int griperid = 20;
    public static int indexerid = 21;
    public static final int INTAKE_CLOSE_SWITCH_PORT = 9;

    public final class MotionMagicConstants {
      public static final double MOTION_MAGIC_VELOCITY = 2;
      public static final double MOTION_MAGIC_ACCELERATION = 2.5;
      public static final double MOTION_MAGIC_JERK = 25;

      public static final double MOTOR_KS = 0.045; // TODO INITILIZE THESE VALUES
      public static final double MOTOR_KA = 0.5;
      public static final double MOTOR_KV = 6.5;
      public static final double MOTOR_KG = 0;
      public static final double MOTOR_KP = 60;
      public static final double MOTOR_KI = 0;
      public static final double MOTOR_KD = 0;
    }
  }

  public class ClimbSubsystemConstants {

    public static final class MotorCurrentLimits {

      public static final int SUPPLY_CURRENT_LIMIT = 40;
      public static final boolean SUPPLY_CURRENT_LIMIT_ENABLE = true;
      public static final double SUPPLY_CURRENT_LOWER_LIMIT = 0;
    }

    public static final int CLIMB_MOTOR_ID = 60;
    public static final int POSITION_CONVERSION_FACTOR = 1;
    public static final int VELOCITY_CONVERSION_FACTOR = 1000;

    public static final int WHEEL_KP = -0;
    public static final int WHEEL_KI = -0;
    public static final int WHEEL_KD = -0;
    public static final int WHEEL_KFF = -0;

    public static final class MotionMagicConstants {

      public static final double MOTION_MAGIC_VELOCITY = -0;
      public static final double MOTION_MAGIC_ACCELERATION = -0;
      public static final double MOTION_MAGIC_JERK = -0;

      public static final int MOTOR_KS = -0; // TODO INITILIZE THESE VALUES
      public static final int MOTOR_KA = -0;
      public static final int MOTOR_KV = -0;
      public static final int MOTOR_KP = -0;
      public static final int MOTOR_KI = -0;
      public static final int MOTOR_KD = -0;
    }
  }
}
