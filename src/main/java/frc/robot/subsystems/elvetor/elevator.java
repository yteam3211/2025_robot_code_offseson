// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.elvetor;

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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.states.Elevatorstates;
import frc.robot.subsystems.elvetor.elvetorconstants.MotorCurrentLimits;
import java.util.function.DoubleSupplier;

public class elevator extends SubsystemBase {
  public Elevatorstates state = Elevatorstates.REST_MODE;

  private TalonFX motor = new TalonFX(elvetorconstants.masterid, "canv");
  private TalonFX m_slave = new TalonFX(elvetorconstants.slaveid, "canv");
  private DigitalInput m_closeSwitch =
      new DigitalInput(elvetorconstants.ELEVATOR_CLOSE_SWITCH_PORT);

  private final MotionMagicVoltage motionMagicVoltage = new MotionMagicVoltage(0);
  ;
  /** Creates a new elevatorsubsystem. */
  public elevator() {
    TalonFXConfiguration talonFXConfiguration = new TalonFXConfiguration();

    FeedbackConfigs feedbackConfigs = talonFXConfiguration.Feedback;
    feedbackConfigs.FeedbackSensorSource = elvetorconstants.SensorSource;
    feedbackConfigs.SensorToMechanismRatio = elvetorconstants.POSITION_CONVERSION_FACTOR;

    MotorOutputConfigs motorOutputConfigs = talonFXConfiguration.MotorOutput;
    motorOutputConfigs.NeutralMode = elvetorconstants.NeutralMode;

    CurrentLimitsConfigs limitConfigs = talonFXConfiguration.CurrentLimits;
    limitConfigs.SupplyCurrentLimit = MotorCurrentLimits.SUPPLY_CURRENT_LIMIT;
    limitConfigs.SupplyCurrentLowerLimit = MotorCurrentLimits.SUPPLY_CURRENT_LOWER_LIMIT;
    limitConfigs.SupplyCurrentLimitEnable = MotorCurrentLimits.SUPPLY_CURRENT_LIMIT_ENABLE;

    MotionMagicConfigs motionMagicConfigs = talonFXConfiguration.MotionMagic;
    motionMagicConfigs.MotionMagicCruiseVelocity =
        elvetorconstants.MotionMagicConstants.MOTION_MAGIC_VELOCITY;
    motionMagicConfigs.MotionMagicAcceleration =
        elvetorconstants.MotionMagicConstants.MOTION_MAGIC_ACCELERATION;
    motionMagicConfigs.MotionMagicJerk = elvetorconstants.MotionMagicConstants.MOTION_MAGIC_JERK;

    Slot0Configs slot0 = talonFXConfiguration.Slot0;
    slot0.kS = elvetorconstants.MotionMagicConstants.MOTOR_KS;
    slot0.kG = elvetorconstants.MotionMagicConstants.MOTOR_KG;
    slot0.kV = elvetorconstants.MotionMagicConstants.MOTOR_KV;
    slot0.kA = elvetorconstants.MotionMagicConstants.MOTOR_KA;
    slot0.kP = elvetorconstants.MotionMagicConstants.MOTOR_KP;
    slot0.kI = elvetorconstants.MotionMagicConstants.MOTOR_KI;
    slot0.kD = elvetorconstants.MotionMagicConstants.MOTOR_KD;
    slot0.GravityType = elvetorconstants.MotionMagicConstants.GravityType;

    m_slave.setControl(new Follower(motor.getDeviceID(), false));

    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 5; ++i) {
      status = motor.getConfigurator().apply(talonFXConfiguration);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not configure device. Error: " + status.toString());
    }
    this.setDefaultCommand(this.setDefualElevatorCommand());
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("ELevatordown", isElevatorDown());
    SmartDashboard.putNumber("ELEVATOR: distance", getHeight());
    SmartDashboard.putNumber("ELEVATOR velcoity", getVelocity());
    SmartDashboard.putString("ELEVATOR state", state.name() + state.getTarget());

    resetHeight();
  }

  public void setDefaultElevator() {
    setToPos(() -> state.getTarget());
  }

  public Command setDefualElevatorCommand() {
    return this.run(() -> setDefaultElevator());
  }

  public void changeState(Elevatorstates newstate) {
    this.state = newstate;
  }

  public Command changeStateCommand(Elevatorstates newstate) {
    return this.runOnce(() -> changeState(newstate));
  }

  public void setToPos(DoubleSupplier Pos) {
    motor.setControl(motionMagicVoltage.withPosition(Pos.getAsDouble()).withSlot(0));
  }

  public Command setToPosCommand(DoubleSupplier Pos) {
    return this.runOnce(() -> setToPos(Pos));
  }

  public double getHeight() {
    return motor.getPosition().getValueAsDouble();
  }

  public double getVelocity() {
    return motor.getVelocity().getValueAsDouble();
  }

  public void resetHeight() {
    if (isElevatorDown()) {
      motor.setPosition(0);
    }
  }

  boolean m_occurred = false;

  public boolean isFirstResetOccurred() {
    if (isElevatorDown()) {
      m_occurred = true;
    }
    return m_occurred;
  }

  public boolean isElevatorDown() {
    return m_closeSwitch.get();
  }
}
