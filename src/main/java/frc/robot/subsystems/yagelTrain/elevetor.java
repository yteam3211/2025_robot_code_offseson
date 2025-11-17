// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.yagelTrain;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.states.Elevatorstates;
import frc.robot.subsystems.yagelTrain.elevetorcons.MotorCurrentLimits;

public class elevetor extends SubsystemBase {
  private TalonFX m_master = new TalonFX(elevetorcons.masterid, "canv");
  private TalonFX m_slave = new TalonFX(elevetorcons.slaveid, "canv");
  private DigitalInput m_closeSwitch = new DigitalInput(elevetorcons.ELEVATOR_CLOSE_SWITCH_PORT);
  public Elevatorstates state = Elevatorstates.REST_MODE;
  private MotionMagicVoltage motionMagicVoltage = new MotionMagicVoltage(0);
  /** Creates a new elevetor. */
  public elevetor() {

    TalonFXConfiguration talonFXConfiguration = new TalonFXConfiguration();

    FeedbackConfigs feedbackConfigs = talonFXConfiguration.Feedback;
    feedbackConfigs.FeedbackSensorSource = elevetorcons.SensorSource;
    feedbackConfigs.SensorToMechanismRatio = elevetorcons.POSITION_CONVERSION_FACTOR;

    MotorOutputConfigs motorOutputConfigs = talonFXConfiguration.MotorOutput;
    motorOutputConfigs.NeutralMode = elevetorcons.NeutralMode;

    CurrentLimitsConfigs limitConfigs = talonFXConfiguration.CurrentLimits;
    limitConfigs.SupplyCurrentLimit = MotorCurrentLimits.SUPPLY_CURRENT_LIMIT;
    limitConfigs.SupplyCurrentLowerLimit = MotorCurrentLimits.SUPPLY_CURRENT_LOWER_LIMIT;
    limitConfigs.SupplyCurrentLimitEnable = MotorCurrentLimits.SUPPLY_CURRENT_LIMIT_ENABLE;

    MotionMagicConfigs motionMagicConfigs = talonFXConfiguration.MotionMagic;
    motionMagicConfigs.MotionMagicCruiseVelocity =
        elevetorcons.MotionMagicConstants.MOTION_MAGIC_VELOCITY;
    motionMagicConfigs.MotionMagicAcceleration =
        elevetorcons.MotionMagicConstants.MOTION_MAGIC_ACCELERATION;
    motionMagicConfigs.MotionMagicJerk = elevetorcons.MotionMagicConstants.MOTION_MAGIC_JERK;

    Slot0Configs slot0 = talonFXConfiguration.Slot0;
    slot0.kS = elevetorcons.MotionMagicConstants.MOTOR_KS;
    slot0.kG = elevetorcons.MotionMagicConstants.MOTOR_KG;
    slot0.kV = elevetorcons.MotionMagicConstants.MOTOR_KV;
    slot0.kA = elevetorcons.MotionMagicConstants.MOTOR_KA;
    slot0.kP = elevetorcons.MotionMagicConstants.MOTOR_KP;
    slot0.kI = elevetorcons.MotionMagicConstants.MOTOR_KI;
    slot0.kD = elevetorcons.MotionMagicConstants.MOTOR_KD;
    slot0.GravityType = elevetorcons.MotionMagicConstants.GravityType;

    m_slave.setControl(new Follower(m_master.getDeviceID(), false));

    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 5; ++i) {
      status = m_master.getConfigurator().apply(talonFXConfiguration);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not configure device. Error: " + status.toString());
    }
    m_master.setPosition(0);
    this.setDefaultCommand(defualtCommand());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    resetHeight();
  }

  public void resetHeight() {
    if (m_closeSwitch.get()) {
      m_master.setPosition(0);
    }
  }

  public void changeState(Elevatorstates new_state) {
    state = new_state;
  }

  public Command changeStateCommand(Elevatorstates new_state) {
    return Commands.runOnce(() -> changeState(new_state));
  }

  public Command defualtCommand() {
    return Commands.runOnce(
        () -> m_master.setControl(motionMagicVoltage.withPosition(state.getTarget())), this);
  }
}
