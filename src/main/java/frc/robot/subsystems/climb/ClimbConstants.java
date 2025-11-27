package frc.robot.subsystems.climb;

import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class ClimbConstants {

  public static int CLIMB_MOTOR_ID = 55;
  public static final double POSITION_CONVERSION_FACTOR = 1;
  public static final double GEAR_RATIO = 0;
  public static final FeedbackSensorSourceValue SensorSource =
      FeedbackSensorSourceValue.RotorSensor;
  public static final NeutralModeValue NeutralMode = NeutralModeValue.Brake;

  public static final class MotorCurrentLimits {

    public static final int SUPPLY_CURRENT_LIMIT = 30;
    public static final boolean SUPPLY_CURRENT_LIMIT_ENABLE = true;
    public static final double SUPPLY_CURRENT_LOWER_LIMIT = 30;
  }

  public static final class MotionMagicConstants {

    public static final double MOTION_MAGIC_VELOCITY = 84;
    public static final double MOTION_MAGIC_ACCELERATION = 816;
    public static final double MOTION_MAGIC_JERK = 0;

    public static final double MOTOR_KS = 0; // TODO INITILIZE THESE VALUES
    public static final double MOTOR_KA = 0.01;
    public static final double MOTOR_KV = 0.12;
    public static final double MOTOR_KG = 0;
    public static final double MOTOR_KP = 1;
    public static final double MOTOR_KI = 0;
    public static final double MOTOR_KD = 0;
    public static final GravityTypeValue GravityType = GravityTypeValue.Elevator_Static;
  }
}
