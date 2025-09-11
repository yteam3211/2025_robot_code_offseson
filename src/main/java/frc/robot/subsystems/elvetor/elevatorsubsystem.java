// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.elvetor;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.states.Elavatorstates;

public class elevatorsubsystem extends SubsystemBase {
  public elevatorio io;
  public elevatorio.elevatorioinputs inputs = new elevatorio.elevatorioinputs();

  public elevatorsubsystem() {
    inputs.setslave();
  }
  ;
  /** Creates a new elevatorsubsystem. */
  public elevatorsubsystem(elevatorio io) {
    this.io = io;
    this.inputs.state = Elavatorstates.Close;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }
  public Command set(double d) {
    io.updatepose(
        new Pose3d(
            inputs.elevatorpPose.getX(),
            inputs.elevatorpPose.getY(),
            inputs.elevatorpPose.getZ() + 0.2,
            inputs.elevatorpPose.getRotation()));
    ;
    return runOnce(() -> io.set(d));
  }
}
