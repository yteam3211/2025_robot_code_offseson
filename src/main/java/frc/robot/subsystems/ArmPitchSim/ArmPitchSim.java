// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.ArmPitchSim;

import java.io.IOError;

import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.states.armPitchState;
import frc.robot.subsystems.ArmPitchSim.ArmPitchIO.ArmPitchInputs;

public class ArmPitchSim extends SubsystemBase {
  @AutoLogOutput
  armPitchState state = armPitchState.firtstinit;
  ArmPitchInputs inputs = new ArmPitchInputs();
  ArmPitchIO io;
  /** Creates a new ArmPitchSim. */
  public ArmPitchSim(ArmPitchIO io) {
    this.io = io;
    
  }

  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    io.updateInputs(inputs);
    // Logger.processInputs("Arm Pitch", inputs);
  }
  
}
