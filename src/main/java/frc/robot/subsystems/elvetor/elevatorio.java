package frc.robot.subsystems.elvetor;

import edu.wpi.first.math.geometry.Pose3d;
import frc.robot.states.Elavatorstates;
import org.littletonrobotics.junction.AutoLog;

public interface elevatorio {
  @AutoLog
  public static class elevatorioinputs {
    public Elavatorstates state = Elavatorstates.Close;
    public Pose3d elevatorpPose = new Pose3d();

    public elevatorioinputs() {}

    public int getestametedpos() {
      return elevatorpPose.getZ() <= 0 ? 0 : (int) elevatorpPose.getZ();
    }
    public Elavatorstates getstate() {
      return state;
    }
  }
  public default void updateInputs(elevatorioinputs inputs) {}
}
