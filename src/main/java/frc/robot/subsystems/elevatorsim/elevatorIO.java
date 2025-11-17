package frc.robot.subsystems.elevatorsim;

import static edu.wpi.first.units.Units.Meter;
import static edu.wpi.first.units.Units.MetersPerSecond;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearVelocity;
import java.util.function.DoubleSupplier;
import org.littletonrobotics.junction.AutoLog;

public interface elevatorIO {

  @AutoLog
  public static class elevatorInputs {
    public Distance height = Meter.of(0);
    public LinearVelocity speed = MetersPerSecond.of(0);
    public boolean is_close = false;

    public elevatorInputs() {}
  }
  /** Updates the set of loggable inputs. */
  void updateinputs(elevatorInputs inputs);
  /** simple set speed */
  void setSpeed(DoubleSupplier speed);
  /** set elevator to height */
  void setheight(DoubleSupplier height);
  /** set motor coder value to what given */
  void setMotorHeight(double height);
  /** update what happen with the elevator */
  void updateElevator();
}
