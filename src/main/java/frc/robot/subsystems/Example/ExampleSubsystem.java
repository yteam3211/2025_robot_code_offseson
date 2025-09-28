// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Example;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.states.Examplestaet;

public class ExampleSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  public ExampleSubsystem() {}

  private Examplestaet examplestaet = Examplestaet.KeepItIn;
  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  // how to build subsystem on a state robot
  public void setMotortospeed(double speed) {
    // can be with motion magic or not
    // set motor to speed
  }

  public Command setMotortospeedCommand(double speed) {
    // can be with motion magic or not
    return this.runOnce(
        () -> {
          // set motor to speed
          setMotortospeed(speed);
        });
  }

  public void changeState(Examplestaet newstate) {
    this.examplestaet = newstate;
  }

  public Command changeStateCommand(Examplestaet newstate) {
    return this.runOnce(() -> changeState(newstate));
  }
}
