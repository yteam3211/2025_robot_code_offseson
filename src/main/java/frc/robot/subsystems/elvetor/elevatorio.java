package frc.robot.subsystems.elvetor;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.Alert;
import frc.robot.states.Elavatorstates;
import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.Logger;

public interface elevatorio {
  @AutoLog
  public static class elevatorioinputs {
    public Elavatorstates state = Elavatorstates.Close;
    public Pose3d elevatorpPose = new Pose3d();

    public void setslave() {
      //
    }

    public elevatorioinputs() {
      Logger.recordOutput("Elavator/State", state);
      Logger.recordOutput("Elavator/EstimatedPos", getestametedpos());
    }

    public Pose3d getestametedpos() {
      return elevatorpPose;
    }

    public Elavatorstates getstate() {
      return state;
    }

    public void set(double speed) {
      // io.set(speed);
      System.out.println("Elevator is not configured");
    }
  }

  public default void updatepose(Pose3d newpose) {
    elevatorioinputsAutoLogged inputs = new elevatorioinputsAutoLogged();
    inputs.elevatorpPose = newpose;
    updateInputs(inputs);
  }

  public default void updateInputs(elevatorioinputsAutoLogged inputs) {
    new Alert("no updateInputs in elevator", Alert.AlertType.kError)
        .set(true); // Alert that the method is not overridden
  }

  public default void set(double d) {
    System.out.println("Elevator set is not configured");
  }
  ;
}
