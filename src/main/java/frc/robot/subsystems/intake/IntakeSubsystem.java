// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake;


import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.states.IntakeIndexer;
import frc.robot.states.inakegriperstate;

public class IntakeSubsystem extends SubsystemBase {

  TalonFX m_griperintake = new TalonFX(Constants.intakecos.griperid);
  TalonFX m_indexer = new TalonFX(Constants.intakecos.indexerid);


  inakegriperstate gripertate = inakegriperstate.KeepItIn;
  IntakeIndexer indexer = IntakeIndexer.close;



  /** Creates a new intakesubsystem. */
  public IntakeSubsystem() {

  }

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
  }

  public void setgriper(double speed) {
    m_griperintake.set(speed);
  }


  public Command setgriperCommand(double speed){
    return this.runOnce(()-> setgriper(speed));
  }
  public Command stopgriperCommand(){
    return this.runOnce(()-> setgriper(0));//stop
  }
  public void setindexer(double speed){
    m_indexer.set(speed);
  }
  public Command setindexerCommand(double speed){
    return this.runOnce(()-> setindexer(speed));
  }
  public Command stopindexerCommand(){
    return this.runOnce(()-> setindexer(0));
  }
}
