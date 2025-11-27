package frc.robot.subsystems.IntakePitchSim;

import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;

public class IntakePitchConstantsSim {

  public static final double POSITION_CONVERSION_FACTOR = 0.0959435626;
  public static final NeutralModeValue NeutralMode = NeutralModeValue.Brake;
  public static final InvertedValue MOTOR_INVERTED = InvertedValue.Clockwise_Positive;
  public static int PITCH_MOTOR_ID = 60;
  public static final int CLOSE_SWITCH_PORT = 9;
  public static double gearRatio = 34.54;
  public static double ARM_LENGTH = 0.043;
  public static double massInKg = 7.1;
  public static double jkgsquremeters = SingleJointedArmSim.estimateMOI(ARM_LENGTH, massInKg);

  public final class MotionMagicConstants {
    public static final double MOTION_MAGIC_VELOCITY = 250;
    public static final double MOTION_MAGIC_ACCELERATION = 170;
    public static final double MOTION_MAGIC_JERK = 0;

    public static final double MOTOR_KS = 0.0; // TODO INITILIZE THESE VALUES
    public static final double MOTOR_KA = 0;
    public static final double MOTOR_KV = 0.0145;
    public static final double MOTOR_KG = 0.6;
    public static final double MOTOR_KP = 0.02; // 4.5;
    public static final double MOTOR_KI = 0;
    public static final double MOTOR_KD = 0;
    public static final GravityTypeValue GravityType = GravityTypeValue.Arm_Cosine;
  }
}
