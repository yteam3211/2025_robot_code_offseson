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

  public static boolean disableHAL = false;

  public static void disableHAL() {
    disableHAL = true;
  }
}
