package frc.robot.subsystems.IntakePitch;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class IntakePitchConstants {
  public static final double POSITION_CONVERSION_FACTOR = 0.0959435626;
  public static int PITCH_MOTOR_ID = 60;
  public static final int CLOSE_SWITCH_PORT = 9;
  public static final InvertedValue MOTOR_INVERTED = InvertedValue.Clockwise_Positive;
  public static final NeutralModeValue MOTOR_NEUTRAL_MODE = NeutralModeValue.Coast;

  public final class MotionMagicConstants {
    public static final double MOTION_MAGIC_VELOCITY = 200;
    public static final double MOTION_MAGIC_ACCELERATION = 100;
    public static final double MOTION_MAGIC_JERK = 0;

    public static final double MOTOR_KS = 0.0; // TODO INITILIZE THESE VALUES
    public static final double MOTOR_KA = 0.005;
    public static final double MOTOR_KV = 0.013;
    public static final double MOTOR_KG = -0.0525;
    public static final double MOTOR_KP = -0.001; // -0.025
    public static final double MOTOR_KI = 0;
    public static final double MOTOR_KD = 0.00;
  }
}
