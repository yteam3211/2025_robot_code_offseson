// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.elevatorsim;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.SwerveConstants;
import frc.robot.states.Elevatorstates;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

public class elevator2 extends SubsystemBase {
  @AutoLogOutput public static Elevatorstates state = Elevatorstates.REST_MODE;
  private elevatorIO io;
  private elevatorInputsAutoLogged inputs = new elevatorInputsAutoLogged();

  /** Creates a new elevatorsubsystem. */
  public elevator2(elevatorIO io) {
    this.io = io;
    this.setDefaultCommand(this.setDefualElevatorCommand());
  }

  @Override
  public void periodic() {
    io.updateinputs(inputs);
    Logger.processInputs("elevator", inputs);
    Logger.recordOutput("elevator/state", state);

    resetHeight();
    limitSwerveDriveSpeed();
  }

  @Override
  public void simulationPeriodic() {
    io.updateElevator();
  }

  public void setspeed(DoubleSupplier speed) {
    io.setSpeed(speed);
  }

  public void limitSwerveDriveSpeed() {
    if (getHeight() > Elevatorstates.SAFE_ZONE.getTarget()) {
      SwerveConstants.SLOW_DRIVE = 2;
    } else {
      SwerveConstants.SLOW_DRIVE = 1;
    }
  }

  public Command setSpeedCommand(DoubleSupplier speed) {
    return Commands.runOnce(() -> setspeed(speed));
  }

  public Command setToZeroPosionCommand() {
    return changestateCommandMustHaveUntil(Elevatorstates.REST_MODE)
        .until(() -> getHeight() < 5)
        .andThen(setSpeedCommand(() -> -0.1).until(() -> isElevatorDown()));
  }

  public Command setToZeroPosionCommandsuper() {
    return changeStateCommand(Elevatorstates.REST_MODE)
        .andThen(this.run(() -> io.setSpeed(() -> -0.2)).until(() -> this.isElevatorDown()));
  }

  public void setDefaultElevator() {
    setToPos(() -> state.getTarget());
  }

  public Command setDefualElevatorCommand() {
    return this.runOnce(() -> setDefaultElevator());
  }

  public void changeState(Elevatorstates newstate) {
    state = newstate;
  }

  public Command changestateCommandMustHaveUntil(Elevatorstates new_state) {
    return Commands.run(() -> changeState(new_state));
  }

  public void stopElevatorcommand() {
    this.getCurrentCommand().cancel();
  }

  public Command changeStateCommand(Elevatorstates newstate) {
    return Commands.runOnce(() -> changeState(newstate));
  }

  public void setToPos(DoubleSupplier height) {
    io.setheight(height);
  }

  public Command setToPosCommand(DoubleSupplier height) {
    return Commands.runOnce(() -> setToPos(height));
  }

  public double getHeight() {
    return inputs.height;
  }

  public BooleanSupplier isAtLestHight(double height) {
    return () -> getHeight() > height;
  }

  public double getVelocity() {
    return inputs.speed;
  }

  public void resetHeight() {
    if (isElevatorDown() && state.getTarget() == 0) {
      io.setMotorHeight(0);
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
    return inputs.is_close;
  }
}
