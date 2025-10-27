package frc.robot.subsystems.elevatorsim;

import java.util.function.DoubleSupplier;
import org.littletonrobotics.junction.AutoLog;

public interface elevatorIO {

  @AutoLog
  public static class elevatorInputs {
    public double height = 0.0;
    public double speed = 0.0;
    public boolean is_close = false;

    public elevatorInputs() {}
  }
  /** Updates the set of loggable inputs. */
  public default void updateinputs(elevatorInputs inputs) {}
  /** simple set speed */
  public default void setSpeed(DoubleSupplier speed) {}
  /** set elevator to height */
  public default void setheight(DoubleSupplier height) {}
  /** set motor coder value to what given */
  public default void setMotorHeight(double height) {}
  /** update what happen with the elevator */
  public default void updateElevator() {}
}
