package frc.robot.subsystems.IntakePitchSim;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.Radian;
import static edu.wpi.first.units.Units.RadiansPerSecond;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import org.littletonrobotics.junction.Logger;

public class IntakePitchIOSim implements IntakePitchIO {
  private TalonFX talon = new TalonFX(IntakePitchConstantsSim.PITCH_MOTOR_ID, "rio");
  private MotionMagicVoltage motionMagicVoltage = new MotionMagicVoltage(0);
  private SingleJointedArmSim armSim =
      new SingleJointedArmSim(
          DCMotor.getKrakenX60(1),
          IntakePitchConstantsSim.gearRatio,
          IntakePitchConstantsSim.jkgsquremeters,
          IntakePitchConstantsSim.ARM_LENGTH,
          Degree.of(-5).in(Radian),
          Degree.of(138).in(Radian),
          true,
          Degree.of(138).in(Radian));

  public IntakePitchIOSim() {
    TalonFXConfiguration talonFXConfigurationspin = new TalonFXConfiguration();
    FeedbackConfigs feedbackConfigsspin = talonFXConfigurationspin.Feedback;
    feedbackConfigsspin.SensorToMechanismRatio = IntakePitchConstantsSim.POSITION_CONVERSION_FACTOR;
    MotorOutputConfigs motorOutputConfigsspin = talonFXConfigurationspin.MotorOutput;
    motorOutputConfigsspin.NeutralMode = IntakePitchConstantsSim.NeutralMode;
    MotionMagicConfigs motionMagicConfigsspin = talonFXConfigurationspin.MotionMagic;
    motionMagicConfigsspin.MotionMagicCruiseVelocity =
        IntakePitchConstantsSim.MotionMagicConstants.MOTION_MAGIC_VELOCITY;
    motionMagicConfigsspin.MotionMagicAcceleration =
        IntakePitchConstantsSim.MotionMagicConstants.MOTION_MAGIC_ACCELERATION;
    motionMagicConfigsspin.MotionMagicJerk =
        IntakePitchConstantsSim.MotionMagicConstants.MOTION_MAGIC_JERK;

    Slot0Configs slot0spin = talonFXConfigurationspin.Slot0;
    slot0spin.kS = IntakePitchConstantsSim.MotionMagicConstants.MOTOR_KS;
    slot0spin.kG = IntakePitchConstantsSim.MotionMagicConstants.MOTOR_KG;
    slot0spin.kV = IntakePitchConstantsSim.MotionMagicConstants.MOTOR_KV;
    slot0spin.kA = IntakePitchConstantsSim.MotionMagicConstants.MOTOR_KA;
    slot0spin.kP = IntakePitchConstantsSim.MotionMagicConstants.MOTOR_KP;
    slot0spin.kI = IntakePitchConstantsSim.MotionMagicConstants.MOTOR_KI;
    slot0spin.kD = IntakePitchConstantsSim.MotionMagicConstants.MOTOR_KD;
    slot0spin.GravityType = IntakePitchConstantsSim.MotionMagicConstants.GravityType;

    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 5; ++i) {
      status = talon.getConfigurator().apply(talonFXConfigurationspin);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not configure device. Error: " + status.toString());
    }
    talon.setPosition(0);
  }

  double postorot = IntakePitchConstantsSim.POSITION_CONVERSION_FACTOR;

  @Override
  public void updateArmPitch() {
    Logger.recordOutput("IntakePitch/motor", talon.getVelocity().getValueAsDouble());
    armSim.setInput(talon.getSimState().getMotorVoltage());
    armSim.update(0.02);

    talon
        .getSimState()
        .setRawRotorPosition(
            (Units.radiansToDegrees(armSim.getAngleRads()) - Units.degreesToRadians(-5))
                * postorot);
    talon
        .getSimState()
        .setRotorVelocity(Units.radiansToDegrees(armSim.getVelocityRadPerSec()) * postorot);
    RoboRioSim.setVInVoltage(
        BatterySim.calculateDefaultBatteryLoadedVoltage(armSim.getCurrentDrawAmps()));
  }

  @Override
  public void updateInputs(IntakePitchInputs inputs) {
    inputs.pos = Radian.of(armSim.getAngleRads()).in(Degree);
    inputs.speed = RadiansPerSecond.of(armSim.getVelocityRadPerSec()).in(DegreesPerSecond);
    inputs.isClosed = Radian.of(armSim.getAngleRads()).in(Degree) < 50;
  }

  @Override
  public void setSpeedPos(double speed) {
    talon.set(speed);
  }

  @Override
  public void setPos(Double pos) {
    talon.setControl(motionMagicVoltage.withPosition(pos + 138));
  }
}
