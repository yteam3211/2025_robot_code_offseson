// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.states.armgriperstatestate;
import frc.robot.states.armspinstate;
import org.littletonrobotics.junction.Logger;

public class armsubsystem extends SubsystemBase {

  public TalonFX m_spin = new TalonFX(Constants.Armconstants.m_spinid, "rio");
  public TalonFX m_griper = new TalonFX(Constants.Armconstants.m_grieprid, "rio");
  public armspinstate spinstate = armspinstate.stop;
  public armgriperstatestate gripertate = armgriperstatestate.KeepItIn;
  private final MotionMagicVoltage motionMagicVoltage = new MotionMagicVoltage(0);
  public Pose3d Armpose = new Pose3d();

  // armioinputsautologged input = new armioinputsautolog;
  // armio m_io;

  /** Creates a new armsubsystem. */
  public armsubsystem(/* armio io*/ ) {
    // m_io = io;
    Logger.recordOutput("arm/pose", Armpose);
    Logger.recordOutput("arm/spinstate", spinstate);
    Logger.recordOutput("arm/griperstate", gripertate);

    TalonFXConfiguration talonFXConfigurationspin = new TalonFXConfiguration();
    FeedbackConfigs feedbackConfigsspin = talonFXConfigurationspin.Feedback;
    feedbackConfigsspin.SensorToMechanismRatio = Constants.Armconstants.POSITION_CONVERSION_FACTOR;

    MotionMagicConfigs motionMagicConfigsspin = talonFXConfigurationspin.MotionMagic;
    motionMagicConfigsspin.MotionMagicCruiseVelocity =
        Constants.Armconstants.MotionMagicConstantsspin.MOTION_MAGIC_VELOCITY;
    motionMagicConfigsspin.MotionMagicAcceleration =
        Constants.Armconstants.MotionMagicConstantsspin.MOTION_MAGIC_ACCELERATION;
    motionMagicConfigsspin.MotionMagicJerk =
        Constants.Armconstants.MotionMagicConstantsspin.MOTION_MAGIC_JERK;

    Slot0Configs slot0spin = talonFXConfigurationspin.Slot0;
    slot0spin.kS = Constants.Armconstants.MotionMagicConstantsspin.MOTOR_KS;
    slot0spin.kG = Constants.Armconstants.MotionMagicConstantsspin.MOTOR_KG;
    slot0spin.kV = Constants.Armconstants.MotionMagicConstantsspin.MOTOR_KV;
    slot0spin.kA = Constants.Armconstants.MotionMagicConstantsspin.MOTOR_KA;
    slot0spin.kP = Constants.Armconstants.MotionMagicConstantsspin.MOTOR_KP;
    slot0spin.kI = Constants.Armconstants.MotionMagicConstantsspin.MOTOR_KI;
    slot0spin.kD = Constants.Armconstants.MotionMagicConstantsspin.MOTOR_KD;

    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 5; ++i) {
      status = m_spin.getConfigurator().apply(talonFXConfigurationspin);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not configure device. Error: " + status.toString());
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // io.updateinputs(inputs);
  }

  public void setspin(double pos) {
    m_spin.setControl(motionMagicVoltage.withPosition(pos).withSlot(0));
  }

  public double getarmspin() {
    return m_spin.getPosition().getValueAsDouble();
  }

  public void stopspin() {
    m_spin.set(0);
  }

  public double getholdercurrent() {
    return m_griper.get();
  }

  public void setholfrter(double speed) {
    m_griper.set(speed);
  }

  public void stopholder() {
    m_griper.set(0);
  }
}
