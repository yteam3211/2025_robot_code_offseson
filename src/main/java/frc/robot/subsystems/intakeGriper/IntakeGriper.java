// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intakeGriper;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.states.inakegriperstate;

public class IntakeGriper extends SubsystemBase {

  TalonFX m_griperintake = new TalonFX(IntakeGriperConstants.griperid, "canv");

  inakegriperstate gripertate = inakegriperstate.KeepItIn;

  /** Creates a new intakesubsystem. */
  public IntakeGriper() {

    this.setDefaultCommand(SetDefualCommandGriperIntake());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("IntakeSubsytem has coral", isCoralIn());
    SmartDashboard.putNumber(
        "sensor distance", m_griperintake.getSupplyCurrent().getValueAsDouble());
    SmartDashboard.putString("Intake griper state", gripertate.name() + gripertate.getTarget());
  }

  public Command SetDefualCommandGriperIntake() {
    return this.run(() -> setgriper(gripertate.getTarget()));
  }

  public Command changestateCommandMustHaveUntil(inakegriperstate new_state) {
    return Commands.run(() -> changeState(new_state));
  }

  public void setgriper(double speed) {
    m_griperintake.set(speed);
  }

  public boolean isCoralIn() {
    return m_griperintake.getStatorCurrent().getValueAsDouble() > 50;
  }

  public void changeState(inakegriperstate newstate) {
    gripertate = newstate;
  }

  public Command changeStateCommand(inakegriperstate newstate) {
    return Commands.runOnce(() -> changeState(newstate));
  }
}
