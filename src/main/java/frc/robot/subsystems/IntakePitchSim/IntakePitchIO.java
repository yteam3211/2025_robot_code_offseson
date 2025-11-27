package frc.robot.subsystems.IntakePitchSim;

import org.littletonrobotics.junction.AutoLog;

public interface IntakePitchIO {
  @AutoLog
  public static class IntakePitchInputs {
    /**
     * look https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/units/Units.html on
     * how to get evrey thing
     */
    public double pos = 42;

    public double speed = 0;
    public boolean isClosed = false;
  }
  /**
   * updateInputs default setting
   *
   * @param inputs the inputs to update and take data from
   * @implSpec it must be override to work
   * @apiNote check https://docs.advantagekit.org/getting-started/what-is-advantagekit/ for more
   *     info
   */
  void updateInputs(IntakePitchInputs inputs);
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
