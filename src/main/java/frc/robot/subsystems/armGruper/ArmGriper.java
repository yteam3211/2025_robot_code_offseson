// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of Commands project.

package frc.robot.subsystems.armGruper;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.states.armgriperstate;
import java.util.function.BooleanSupplier;

public class ArmGriper extends SubsystemBase {
  public TalonFX m_griper = new TalonFX(ArmGriperConstants.m_grieprid, "rio");
  public static armgriperstate state = armgriperstate.KeepItIn;
  public Pose3d Armpose = new Pose3d();

  // armioinputsautologged input = new armioinputsautolog;
  // armio m_io;

  /** Creates a new armsubsystem. */
  public ArmGriper(/* armio io*/ ) {
    // m_io = io;
    this.setDefaultCommand(this.setGripperDefualtCommand());
  }

  @Override
  public void periodic() {
    // Commands method will be called once per scheduler run
    SmartDashboard.putString("Arm griper state", state.name() + state.getTarget());
  }

  public double getholdercurrent() {
    return m_griper.get();
  }

  public BooleanSupplier isCorakIn() {
    return () -> m_griper.getStatorCurrent().getValueAsDouble() > 9;
  }

  public Command setGripperDefualtCommand() {
    return this.run(() -> setGriper(state.getTarget()));
  }

  public void setGriper(double speed) {
    m_griper.set(speed);
  }

  public void stopholder() {
    m_griper.set(0);
  }

  public Command setGriperCommand(double speed) {
    return Commands.run(() -> setGriper(speed));
  }

  public Command changestateCommandMustHaveUntil(armgriperstate new_state) {
    return Commands.run(() -> changestate(new_state));
  }

  public void changestate(armgriperstate newstate) {
    state = newstate;
  }

  public Command changestateCommand(armgriperstate newstate) {
    return Commands.runOnce(() -> changestate(newstate));
  }
}
