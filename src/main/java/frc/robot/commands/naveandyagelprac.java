package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotSubsystems;
import frc.robot.states.Elevatorstates;
import frc.robot.states.armPitchState;
import frc.robot.subsystems.Aempitch.armPitch;
import frc.robot.subsystems.elvetor.elevator;

public class naveandyagelprac {
  elevator elevator;
  armPitch armPitch;

  public naveandyagelprac(RobotSubsystems subsystems) {
    armPitch = subsystems.armpitch;
    elevator = subsystems.elevator;
  }

  public Command elevetorUpArmDown() {
    return elevator
        .changeStateCommand(Elevatorstates.INTAKE_MODE_FIRST)
        .alongWith(armPitch.chengestateCommand(armPitchState.COLLECT));
  }
}
