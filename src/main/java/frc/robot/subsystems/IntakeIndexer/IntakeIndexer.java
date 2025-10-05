// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakeIndexer;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.states.IntakeIndexerState;

public class IntakeIndexer extends SubsystemBase {
  TalonFX m_indexer = new TalonFX(IntakeIndexerConstants.indexerid, "canv");
  IntakeIndexerState indexerstate = IntakeIndexerState.STOP;
  /** Creates a new IntakeIndexer. */
  public IntakeIndexer() {
    this.setDefaultCommand(SetDefualCommandIntakeIndexer());
  }

  public Command changestateCommandMustHaveUntil(IntakeIndexerState new_state) {
    return Commands.run(() -> changestate(new_state));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setspeed(double speed) {
    m_indexer.set(speed);
  }

  public void changestate(IntakeIndexerState newstate) {
    indexerstate = newstate;
  }

  public Command changestateCommand(IntakeIndexerState newstate) {
    return Commands.runOnce(() -> changestate(newstate));
  }

  public Command SetDefualCommandIntakeIndexer() {
    return this.runOnce(() -> setspeed(indexerstate.getTarget()));
  }

  public DigitalInput m_isCoralIn = new DigitalInput(IntakeIndexerConstants.IsCoralInID);

  public boolean isCorakIn() {
    return m_isCoralIn.get();
  }
}
