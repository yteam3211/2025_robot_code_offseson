// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.elvetor;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class elevatorsubsystem extends SubsystemBase {
  public elevatorio io;
  public ElevatorioinputsAutoLogged inputs = new ElevatorioinputsAutoLogged();
  ;
  /** Creates a new elevatorsubsystem. */
  public elevatorsubsystem(elevatorio io) {
    this.io = io;
    io.updateInputs(inputs);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    /*if (io.isElevatorDown()) {
      io.resetEncoder();
    }*/
    io.updateInputs(inputs);
    io.    updatepose(
        inputs.elevatorpPose);
    Logger.processInputs("Elevator/inputs", inputs);
  }

  public Command set(double d) {
    return runOnce(() -> io.setlevel(d));
  }

  boolean m_occurred = true;

  public boolean isFirstResetOccurred() {
    if (io.isElevatorDown()) {
      m_occurred = true;
    }
    return m_occurred;
  }

  public Command stop() {
    return set(inputs.state.getTarget());
  }

  public void setLevel(double pos) {
    io.setlevel(pos);
  }
}
