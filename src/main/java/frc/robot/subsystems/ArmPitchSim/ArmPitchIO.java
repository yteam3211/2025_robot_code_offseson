package frc.robot.subsystems.ArmPitchSim;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.DegreesPerSecond;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
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
  }
  /**
   * updateInputs default setting
   *
   * @param inputs the inputs to update and take data from
   * @implSpec it must be override to work
   * @apiNote check https://docs.advantagekit.org/getting-started/what-is-advantagekit/ for more
   *     info
   */
  void updateInputs(ArmPitchInputs inputs);
  /**
   * set simple speed (dutycycle must be from -1 to 1)
   *
   * @param speed dutycycle speed
   * @deprecated shold use the setPos() command use this only for test
   */
  @Deprecated
  void setSpeedPos(double speed);
  /**
   * set motor to pos
   *
   * @param pos the pos you want the arm to move to take a angle type paramte
   *     https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/units/Units.html
   */
  void setPos(Double pos);

  void updateArmPitch();
}
