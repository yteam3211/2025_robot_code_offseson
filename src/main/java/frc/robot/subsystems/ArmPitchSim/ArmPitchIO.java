package frc.robot.subsystems.ArmPitchSim;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import org.littletonrobotics.junction.AutoLog;

public interface ArmPitchIO {
  @AutoLog
  public static class ArmPitchInputs {
    /**
     * look https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/units/Units.html on
     * how to get evrey thing
     */
    public Angle pos = Degree.of(90);

    public AngularVelocity speed = DegreesPerSecond.of(0);
    public AngularAcceleration acce = DegreesPerSecondPerSecond.of(0);
  }
  /**
   * updateInputs default setting
   *
   * @param inputs the inputs to update and take data from
   * @implSpec it must be override to work
   * @apiNote check https://docs.advantagekit.org/getting-started/what-is-advantagekit/ for more
   *     info
   */
  public default void updateInputs(ArmPitchInputs inputs) {}
  /**
   * set simple speed (dutycycle must be from -1 to 1)
   *
   * @param speed dutycycle speed
   * @deprecated shold use the setPos() command use this only for test
   */
  @Deprecated
  public default Command setSpeedPos(double speed) {
    return Commands.runOnce(() -> {});
  }
  /**
   * set motor to pos
   *
   * @param pos the pos you want the arm to move to take a angle type pramte
   *     https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/units/Units.html
   */
  public default Command setPos(Angle pos) {
    return Commands.runOnce(() -> {});
  }
}
