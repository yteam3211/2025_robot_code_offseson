package frc.robot.subsystems.elvetor;

import edu.wpi.first.math.geometry.Pose3d;
import frc.robot.states.Elevatorstates;
import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.Logger;

public interface elevatorio {
  @AutoLog
  public static class Elevatorioinputs {
    public Elevatorstates state = Elevatorstates.Close;
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

    public void set(double speed) {
      // io.set(speed);
      System.out.println("Elevator is not configured");
    }
  }

  public default void updatepose(Pose3d newpose) {
    ElevatorioinputsAutoLogged inputs = new ElevatorioinputsAutoLogged();
    inputs.elevatorpPose = newpose;
    updateInputs(inputs);
  }

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

public default void setlevel(double pos){}
}
