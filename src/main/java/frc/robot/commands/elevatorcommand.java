// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elvetor.elevatorsubsystem;
/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class elevatorcommand extends Command {
  private elevatorsubsystem m_elevator;
  /** Creates a new elevatorcommand. */
  public elevatorcommand(elevatorsubsystem elevator) {
    m_elevator = elevator;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(elevator);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_elevator.io.state >= m_elevator.io.getestametedpos()) {
      m_elevator.set(-(m_elevator.io.state.getTarget() / 10));
    } else {
      m_elevator.set(m_elevator.io.state.getTarget() / 10);
      ;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_elevator.io.state.getTarget() == m_elevator.io.getestametedpos();
  }
  ;
}
