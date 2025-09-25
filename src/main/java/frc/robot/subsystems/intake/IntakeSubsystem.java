// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.states.inakegriperstate;
import frc.robot.states.inakespinstate;
import org.littletonrobotics.junction.Logger;

public class IntakeSubsystem extends SubsystemBase {

  TalonFX m_middleintake = new TalonFX(Constants.intakecos.griperid);
  TalonFX m_middle = new TalonFX(Constants.intakecos.middleid);


  inakegriperstate gripertate = inakegriperstate.KeepItIn;
  inakespinstate spinstate = inakespinstate.up;

  Pose3d intakepose = new Pose3d();



  /** Creates a new intakesubsystem. */
  public IntakeSubsystem() {

  }

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
  }

  public double getholdercurrent() {
    return m_middleintake.get();
  }

  public void setholfrter(double speed) {
    m_middleintake.set(speed);
  }

  public void stopholder() {
    m_middleintake.set(0);
  }
}
