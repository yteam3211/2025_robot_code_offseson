package frc.robot.subsystems.ArmPitchSim;

import static edu.wpi.first.units.Units.Degree;
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
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import org.littletonrobotics.junction.Logger;

public class ArmPitchIOSim implements ArmPitchIO {
  private TalonFX talon = new TalonFX(ArmPItchConstantsSim.m_PitchID, "rio");
  private MotionMagicVoltage motionMagicVoltage = new MotionMagicVoltage(0);
  private SingleJointedArmSim armSim =
      new SingleJointedArmSim(
          DCMotor.getKrakenX60(1),
          ArmPItchConstantsSim.gearRatio,
          ArmPItchConstantsSim.jkgsquremeters,
          ArmPItchConstantsSim.ARM_LENGTH,
          Degree.of(-360).in(Radian),
          Degree.of(360).in(Radian),
          true,
          Degree.of(90).in(Radian));

  public ArmPitchIOSim() {

    TalonFXConfiguration talonFXConfigurationspin = new TalonFXConfiguration();
    FeedbackConfigs feedbackConfigsspin = talonFXConfigurationspin.Feedback;
    feedbackConfigsspin.SensorToMechanismRatio = ArmPItchConstantsSim.POSITION_CONVERSION_FACTOR;
    MotorOutputConfigs motorOutputConfigsspin = talonFXConfigurationspin.MotorOutput;
    motorOutputConfigsspin.NeutralMode = ArmPItchConstantsSim.NeutralMode;
    MotionMagicConfigs motionMagicConfigsspin = talonFXConfigurationspin.MotionMagic;
    motionMagicConfigsspin.MotionMagicCruiseVelocity =
        ArmPItchConstantsSim.MotionMagicConstantsspin.MOTION_MAGIC_VELOCITY;
    motionMagicConfigsspin.MotionMagicAcceleration =
        ArmPItchConstantsSim.MotionMagicConstantsspin.MOTION_MAGIC_ACCELERATION;
    motionMagicConfigsspin.MotionMagicJerk =
        ArmPItchConstantsSim.MotionMagicConstantsspin.MOTION_MAGIC_JERK;

    Slot0Configs slot0spin = talonFXConfigurationspin.Slot0;
    slot0spin.kS = ArmPItchConstantsSim.MotionMagicConstantsspin.MOTOR_KS;
    slot0spin.kG = ArmPItchConstantsSim.MotionMagicConstantsspin.MOTOR_KG;
    slot0spin.kV = ArmPItchConstantsSim.MotionMagicConstantsspin.MOTOR_KV;
    slot0spin.kA = ArmPItchConstantsSim.MotionMagicConstantsspin.MOTOR_KA;
    slot0spin.kP = ArmPItchConstantsSim.MotionMagicConstantsspin.MOTOR_KP;
    slot0spin.kI = ArmPItchConstantsSim.MotionMagicConstantsspin.MOTOR_KI;
    slot0spin.kD = ArmPItchConstantsSim.MotionMagicConstantsspin.MOTOR_KD;
    slot0spin.GravityType = ArmPItchConstantsSim.MotionMagicConstantsspin.GravityType;

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

  double postorot = ArmPItchConstantsSim.POSITION_CONVERSION_FACTOR;

  @Override
  public void updateArmPitch() {
    Logger.recordOutput("armPitch/Motorpos", talon.getPosition().getValueAsDouble());
    armSim.setInput(talon.getSimState().getMotorVoltage());
    armSim.update(0.02);

    talon.getSimState().setRawRotorPosition(armSim.getAngleRads() * postorot);
    talon.getSimState().setRotorVelocity(armSim.getVelocityRadPerSec() * postorot);
    RoboRioSim.setVInVoltage(
        BatterySim.calculateDefaultBatteryLoadedVoltage(armSim.getCurrentDrawAmps()));
  }

  @Override
  public void updateInputs(ArmPitchInputs inputs) {
    inputs.pos = Radian.of(armSim.getAngleRads());
    inputs.speed = RadiansPerSecond.of(armSim.getVelocityRadPerSec());
  }

  @Override
  public void setSpeedPos(double speed) {
    talon.set(speed);
  }

  @Override
  public void setPos(Double pos) {
    talon.setControl(motionMagicVoltage.withPosition(pos));
  }
}
