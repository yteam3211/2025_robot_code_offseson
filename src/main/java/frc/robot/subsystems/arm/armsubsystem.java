// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.states.armpointerstate;
import frc.robot.states.armspinstate;

public class armsubsystem extends SubsystemBase {

  TalonFX m_spin = new TalonFX(Constants.Armconstants.m_spinid);
  TalonFX m_holder = new TalonFX(Constants.Armconstants.m_holderid);
  armspinstate spinstate = armspinstate.stop;
  armpointerstate pointerstate = armpointerstate.KeepItIn;
  /** Creates a new armsubsystem. */
  public armsubsystem() {
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }
}
