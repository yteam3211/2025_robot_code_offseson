// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.elvetor;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.elevatorcos.MotorCurrentLimits;
import frc.robot.states.Elevatorstates;

import java.lang.Thread.State;

import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

public class elevatorsubsystem extends SubsystemBase {
  public Elevatorstates state = Elevatorstates.Close;
    private final MotionMagicVoltage motionMagicVoltage = new MotionMagicVoltage(0);
  private TalonFX motor = new TalonFX(Constants.elevatorcos.masterid);
  private TalonFX m_slave = new TalonFX(Constants.elevatorcos.slaveid);
  private DigitalInput m_closeSwitch =
      new DigitalInput(Constants.elevatorcos.ELEVATOR_CLOSE_SWITCH_PORT);
  @AutoLogOutput(key = "Elevator/Pose")
  public Pose3d elevatorpose = new Pose3d(0, 0, 0.17, new Rotation3d());

  ;
  /** Creates a new elevatorsubsystem. */
  public elevatorsubsystem() {
        TalonFXConfiguration talonFXConfiguration = new TalonFXConfiguration();
    CurrentLimitsConfigs limitConfigs = talonFXConfiguration.CurrentLimits;
    FeedbackConfigs feedbackConfigs = talonFXConfiguration.Feedback;
    feedbackConfigs.SensorToMechanismRatio = Constants.elevatorcos.POSITION_CONVERSION_FACTOR;
    // feedbackConfigs.SensorToMechanismRatio = 1;

    limitConfigs.SupplyCurrentLimit = MotorCurrentLimits.SUPPLY_CURRENT_LIMIT;
    limitConfigs.SupplyCurrentLowerLimit = MotorCurrentLimits.SUPPLY_CURRENT_LOWER_LIMIT;
    limitConfigs.SupplyCurrentLimitEnable = MotorCurrentLimits.SUPPLY_CURRENT_LIMIT_ENABLE;

    MotionMagicConfigs motionMagicConfigs = talonFXConfiguration.MotionMagic;
    motionMagicConfigs.MotionMagicCruiseVelocity =
        Constants.elevatorcos.MotionMagicConstants.MOTION_MAGIC_VELOCITY;
    motionMagicConfigs.MotionMagicAcceleration =
        Constants.elevatorcos.MotionMagicConstants.MOTION_MAGIC_ACCELERATION;
    motionMagicConfigs.MotionMagicJerk =
        Constants.elevatorcos.MotionMagicConstants.MOTION_MAGIC_JERK;

    Slot0Configs slot0 = talonFXConfiguration.Slot0;
    slot0.kS = Constants.elevatorcos.MotionMagicConstants.MOTOR_KS;
    slot0.kG = Constants.elevatorcos.MotionMagicConstants.MOTOR_KG;
    slot0.kV = Constants.elevatorcos.MotionMagicConstants.MOTOR_KV;
    slot0.kA = Constants.elevatorcos.MotionMagicConstants.MOTOR_KA;
    slot0.kP = Constants.elevatorcos.MotionMagicConstants.MOTOR_KP;
    slot0.kI = Constants.elevatorcos.MotionMagicConstants.MOTOR_KI;
    slot0.kD = Constants.elevatorcos.MotionMagicConstants.MOTOR_KD;

    m_slave.setControl(new Follower(motor.getDeviceID(), false));

    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 5; ++i) {
      status = motor.getConfigurator().apply(talonFXConfiguration);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not configure device. Error: " + status.toString());
    }
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("ELevatordown", m_closeSwitch.get());
  }

  boolean m_occurred = true;

  public boolean isFirstResetOccurred() {
    if (isElevatorDown()) {
          m_occurred = true;
        }
        return m_occurred;
      }
    
  public boolean isElevatorDown() {
    return !m_closeSwitch.get();}
  public Command set(double pos) {
    return runOnce(() -> setLevel(pos));}
    
      public Command stop() {
    return set(state.getTarget());
  }

  public void setLevel(double pos) {
    motor.setControl(motionMagicVoltage.withPosition(pos).withSlot(0));
  }
}
