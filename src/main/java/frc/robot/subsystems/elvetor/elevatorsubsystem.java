// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.elvetor;

import com.ctre.phoenix6.hardware.TalonFX;

import com.ctre.phoenix6.controls.Follower;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class elevatorsubsystem extends SubsystemBase {
  public elevatorio io;
  private TalonFX m_master = new TalonFX(Constants.elevatorcos.masterid);
  private TalonFX m_slave = new TalonFX(Constants.elevatorcos.slaveid);
  public elevatorsubsystem(){
    m_slave.setControl(new Follower(m_master.getDeviceID(), false));
  };
  /** Creates a new elevatorsubsystem. */
  public elevatorsubsystem(elevatorio io) {
    this.io = io;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }

  public void set(double speed) {
    m_master.set(speed);
  }
}
