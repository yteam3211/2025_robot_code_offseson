// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.states.armgriperstate;


public class ArmGriper extends SubsystemBase {
  public TalonFX m_griper = new TalonFX(ArmGriperConstants.m_grieprid, "rio");
  public armgriperstate state = armgriperstate.KeepItIn;
  public Pose3d Armpose = new Pose3d();

  // armioinputsautologged input = new armioinputsautolog;
  // armio m_io;

  /** Creates a new armsubsystem. */
  public ArmGriper(/* armio io*/ ) {
    // m_io = io;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  SmartDashboard.putNumber("Arm griper state", getholdercurrent());
  }
  public double getholdercurrent() {
    return m_griper.get();
  }

  public void setGriper(double speed) {
    m_griper.set(speed);
  }

  public void stopholder() {
    m_griper.set(0);
  }

  public Command setGriperCommand(double speed) {
    return this.run(() -> setGriper(speed));
  }
  public Command setGripperDefualtCommand(){
    return this.setGriperCommand(state.getTarget());
  }
  public void changestate(armgriperstate newstate){
    this.state=newstate;
  }
  public Command changestateCommand(armgriperstate newstate){
    return this.runOnce(()->changestate(newstate));
  }
}
