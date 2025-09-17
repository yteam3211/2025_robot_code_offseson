package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.util.SetSubsystemTargetCommand;

public class defaultbutton {
  public static void loadButtons(RobotSubsystems robotSubsystems) {
    elevator(robotSubsystems);
  }

  public static void elevator(RobotSubsystems robotSubsystems) {
    Runnable setElevatorTargetByState =
        () -> {
          if (!robotSubsystems.elevator.isFirstResetOccurred()) {
            robotSubsystems.elevator.stop();
          } else if (robotSubsystems.elevator.io.gethight()
              < robotSubsystems.elevator.inputs.state.getTarget()) {
            robotSubsystems.elevator.setLevel(robotSubsystems.elevator.inputs.state.getTarget());
          }
        };
    Command followElevatorStateTargetCommand =
        new SetSubsystemTargetCommand(robotSubsystems.elevator, setElevatorTargetByState);
    robotSubsystems.elevator.setDefaultCommand(followElevatorStateTargetCommand);
  }
}
