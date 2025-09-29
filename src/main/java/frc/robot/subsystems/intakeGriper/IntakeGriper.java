// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intakeGriper;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.states.inakegriperstate;

public class IntakeGriper extends SubsystemBase {

  TalonFX m_griperintake = new TalonFX(IntakeGriperConstants.griperid, "canv");
  DigitalInput m_stop_DigitalInput = new DigitalInput(IntakeGriperConstants.INTAKE_IS_CORAL_IN);
  inakegriperstate gripertate = inakegriperstate.KeepItIn;

  /** Creates a new intakesubsystem. */
  public IntakeGriper() {
    this.setDefaultCommand(SetDefualCommandGriperIntake());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("IntakeSubsytem has coral", isCoralIn());
  }

  public Command SetDefualCommandGriperIntake() {
    return this.run(() -> setgriper(gripertate.getTarget()));
  }

  public void setgriper(double speed) {
    m_griperintake.set(speed);
  }

  public boolean isCoralIn() {
    return m_stop_DigitalInput.get();
  }

  public void changeState(inakegriperstate newstate) {
    this.gripertate = newstate;
  }

  public Command changeStateCommand(inakegriperstate newstate) {
    return this.runOnce(() -> changeState(newstate));
  }
}
