package frc.robot.subsystems.elvetor;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.states.Elevatorstates;
import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.Logger;

public interface elevatorio {
  @AutoLog
  public class Elevatorioinputs {
    public Elevatorstates state = Elevatorstates.L3;
    public Pose3d elevatorpPose = new Pose3d();

    public Elevatorioinputs() {
      Logger.recordOutput("Elevator/State", state);
      Logger.recordOutput("Elevator/EstimatedPos", getestametedpos());
    }

    public Pose3d getestametedpos() {
      return elevatorpPose;
    }

    public Elevatorstates getstate() {
      return state;
    }

    public Command gotonewxtstate(elevatorsubsystem subsystem) {
      return Commands.runOnce(
          () -> {
            if (state == Elevatorstates.Close) {
              state = Elevatorstates.L3;
            } else if(state == Elevatorstates.L3){
              state = Elevatorstates.Close;
            }
            Logger.recordOutput("Elevator/State", state);
          },
          subsystem);
    }

    public void set(double speed) {
      // io.set(speed);
      System.out.println("Elevator is not configured");
    }
  }

  public default void updatepose(Pose3d newpose) {}

  public default void updateInputs(ElevatorioinputsAutoLogged inputs) {}

  public default void set(double d) {}
  ;

  public default void resetEncoder() {}

  public default void setslave(int masterid, int slaveid) {}

  public default double gethight() {
    return 0;
  }

  public default boolean isElevatorDown() {
    return false;
  }

  public default void setlevel(double pos) {}
}
