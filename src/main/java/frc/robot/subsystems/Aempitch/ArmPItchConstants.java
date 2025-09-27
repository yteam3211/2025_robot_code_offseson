package frc.robot.subsystems.Aempitch;

import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class ArmPItchConstants {
    
    public static final double POSITION_CONVERSION_FACTOR = 1; // 98.5714285711;
    public static final NeutralModeValue NeutralMode = NeutralModeValue.Brake;
    public static int m_PitchID = 16;

    public final class MotionMagicConstantsspin {
      public static final double MOTION_MAGIC_VELOCITY = 283.73;
      public static final double MOTION_MAGIC_ACCELERATION = 160.5;
      public static final double MOTION_MAGIC_JERK = 0;

      public static final double MOTOR_KS = 0; // TODO INITILIZE THESE VALUES
      public static final double MOTOR_KA = 0;
      public static final double MOTOR_KV = 0;
      public static final double MOTOR_KG = 0;
      public static final double MOTOR_KP = 0;
      public static final double MOTOR_KI = 0;
      public static final double MOTOR_KD = 0;
      public static final GravityTypeValue GravityType = GravityTypeValue.Arm_Cosine;
    }
  }

