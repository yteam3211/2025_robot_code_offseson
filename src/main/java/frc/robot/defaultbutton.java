package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.util.SetSubsystemTargetCommand;
import frc.robot.commands.DriveCommands;
import frc.robot.states.Elevatorstates;

public class defaultbutton {
  public static void loadButtons(RobotSubsystems robotSubsystems, Controller Controller) {
    elevator(robotSubsystems, Controller);
    swerve(robotSubsystems, Controller);
  }

  private static void swerve(RobotSubsystems robotSubsystems, Controller controller) {
    Runnable setswerve =
        () -> {
          DriveCommands.joystickDrive(
              robotSubsystems.drive,
              () -> -controller.swervecontroller.getLeftY(),
              () -> -controller.swervecontroller.getLeftX(),
              () -> -controller.swervecontroller.getRightX());
        };
    Command followswerveStateTargetCommand =
        new SetSubsystemTargetCommand(robotSubsystems.drive, setswerve);
    robotSubsystems.drive.setDefaultCommand(followswerveStateTargetCommand);
  }

  public static void elevator(RobotSubsystems robotSubsystems, Controller controller) {
    Runnable setElevatorTargetByState =
        () -> {
          if (!robotSubsystems.elevator.isFirstResetOccurred()) {
            robotSubsystems.elevator.stop();
          } else if (robotSubsystems.elevator.io.gethight()
              != robotSubsystems.elevator.inputs.state.getTarget()) {
            robotSubsystems.elevator.setLevel(robotSubsystems.elevator.inputs.state.getTarget());
          } else if (robotSubsystems.elevator.io.gethight()
              == robotSubsystems.elevator.inputs.state.getTarget()) {
            robotSubsystems.elevator.stop();
          }
        };
    Command followElevatorStateTargetCommand =
        new SetSubsystemTargetCommand(robotSubsystems.elevator, setElevatorTargetByState);
    robotSubsystems.elevator.setDefaultCommand(followElevatorStateTargetCommand);
    Runnable changestateele =
        () -> {
          if (robotSubsystems.elevator.inputs.getstate() == Elevatorstates.Close) {
            robotSubsystems.elevator.inputs.state = Elevatorstates.L4;
          } else if (robotSubsystems.elevator.inputs.getstate() == Elevatorstates.L4) {
            robotSubsystems.elevator.inputs.state = Elevatorstates.Close;
          }
        };

  }
}
