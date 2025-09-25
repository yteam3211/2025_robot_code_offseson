// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.states.IntakeIndexer;
import frc.robot.states.inakegriperstate;

public class IntakeSubsystem extends SubsystemBase {

  TalonFX m_griperintake = new TalonFX(Constants.intakecos.griperid, "canv");
  TalonFX m_indexer = new TalonFX(Constants.intakecos.indexerid, "canv");

  inakegriperstate gripertate = inakegriperstate.KeepItIn;
  IntakeIndexer indexer = IntakeIndexer.close;

  /** Creates a new intakesubsystem. */
  public IntakeSubsystem() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("IntakeSubsytem has coral", isCoralIn());
  }

  public void setgriper(double speed) {
    m_griperintake.setControl(new DutyCycleOut(speed));
  }

  public Command setgriperCommand(double speed) {
    return this.runOnce(() -> setgriper(speed));
  }

  public Command stopgriperCommand() {
    return this.runOnce(() -> setgriper(0)); // stop
  }

  public void setIndexerSpeed(double speed) {
    m_indexer.set(speed);
    System.out.println("indexer speed: " + speed);
  }

  public Command setindexerCommand(double speed) {
    return this.runOnce(() -> setIndexerSpeed(speed));
  }

  public Command stopindexerCommand() {
    return this.runOnce(() -> setIndexerSpeed(0));
  }

  public boolean isCoralIn() {
    return m_griperintake.getSupplyCurrent().getValueAsDouble()
        > Constants.intakecos.INTAKE_CURRENT_THRESHOLD;
  }
}
