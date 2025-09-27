// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakeIndexer;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.states.IntakeIndexerState;

public class IntakeIndexer extends SubsystemBase {
  /** Creates a new IntakeIndexer. */
  TalonFX IntakeGriper = new TalonFX(IntakeIndexerConstants.indexerid);
  IntakeIndexerState state = IntakeIndexerState.STOP;
  public IntakeIndexer() {
    this.setDefaultCommand(setindexerdefualtCommand());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void setindexer(double speed){
    IntakeGriper.set(speed);
  }
  public Command setindexerCommand(double speed){
    return this.runOnce(()->setindexer(speed));
  }
  public Command setindexerdefualtCommand(){
    return this.setindexerCommand(state.getTarget());
  }
  public void changestate(IntakeIndexerState newstate){
    this.state=newstate;
  }
  public Command changestateCommand(IntakeIndexerState newstate){
    return this.runOnce(()->changestate(newstate));
  }
}
