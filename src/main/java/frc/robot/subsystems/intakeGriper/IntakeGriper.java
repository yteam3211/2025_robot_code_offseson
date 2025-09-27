// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intakeGriper;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.states.inakegriperstate;

public class IntakeGriper extends SubsystemBase {

  TalonFX m_griperintake = new TalonFX(IntakeGriperConstants.griperid, "canv");
  DutyCycle m_stopbutton =
      new DutyCycle(new DigitalInput(IntakeGriperConstants.INTAKE_CLOSE_SWITCH_PORT));
  inakegriperstate gripertate = inakegriperstate.KeepItIn;

  /** Creates a new intakesubsystem. */
  public IntakeGriper() {
    this.setDefaultCommand(SetDefualCommandGriperIntake());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("IntakeSubsytem has coral", isCoralIn());
    SmartDashboard.putNumber("IntakeSubsystem Coral Distance", getCoralDistance());
  }
  public Command SetDefualCommandGriperIntake(){
    return this.runOnce(()->setgriper(gripertate.getTarget()));
  }

  public void setgriper(double speed) {
    m_griperintake.set(speed);
  }

  public Command setgriperCommand(double speed) {
    return this.runOnce(() -> setgriper(speed)).until(() -> isCoralIn());
  }


  public boolean isCoralIn() {
    return m_griperintake.getSupplyCurrent().getValueAsDouble() > 20;
  }

  public double getCoralDistance() {
    return m_stopbutton.getOutput();
  }
  public void changeState(inakegriperstate newstate){
    this.gripertate=newstate;
  }
  public Command changeStateCommand(inakegriperstate newstate){
    return this.runOnce(()->changeState(newstate));
  }
}
