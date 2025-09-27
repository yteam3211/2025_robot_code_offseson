package frc.robot.subsystems.elvetor;

import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class elvetorconstants {
    
    public static final int ELEVATOR_CLOSE_SWITCH_PORT = 2;
    public static int masterid = 40;
    public static int slaveid = 41;
    public static final double POSITION_CONVERSION_FACTOR = 32;
    public static final FeedbackSensorSourceValue SensorSource =
        FeedbackSensorSourceValue.RotorSensor;
    public static final NeutralModeValue NeutralMode = NeutralModeValue.Brake;

    public static final class MotorCurrentLimits {

      public static final int SUPPLY_CURRENT_LIMIT = 30;
      public static final boolean SUPPLY_CURRENT_LIMIT_ENABLE = true;
      public static final double SUPPLY_CURRENT_LOWER_LIMIT = 30;
    }

    public static final class MotionMagicConstants {

      public static final double MOTION_MAGIC_VELOCITY = 1.2;
      public static final double MOTION_MAGIC_ACCELERATION = 2;
      public static final double MOTION_MAGIC_JERK = 0;

      public static final double MOTOR_KS = 0.00; // TODO INITILIZE THESE VALUES
      public static final double MOTOR_KA = 0.0;
      public static final double MOTOR_KV = 4;
      public static final double MOTOR_KG = 0.524;
      public static final double MOTOR_KP = 0.01;
      public static final double MOTOR_KI = 0;
      public static final double MOTOR_KD = 0;
    }

}
