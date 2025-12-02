package frc.robot.subsystems.drive;

import com.reduxrobotics.sensors.canandgyro.Canandgyro;
import com.reduxrobotics.sensors.canandgyro.CanandgyroSettings;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import java.util.Queue;

public class GyroIOCanAndGyro implements GyroIO {
  private final Canandgyro canandgyro = new Canandgyro(47);
  private final Queue<Double> yawPositionQueue;
  private final Queue<Double> yawTimestampQueue;

  public GyroIOCanAndGyro() {
    CanandgyroSettings settings =
        new CanandgyroSettings()
            .setAngularPositionFramePeriod(0.02)
            .setYawFramePeriod(0.02)
            .setAccelerationFramePeriod(0);
    boolean settingsapplied = canandgyro.setSettings(settings);
    canandgyro.setYaw(Units.degreesToRotations(0));
    // while (!settingsapplied) {
    //   settingsapplied = canandgyro.setSettings(settings);
    // }
    yawTimestampQueue = PhoenixOdometryThread.getInstance().makeTimestampQueue();
    yawPositionQueue =
        PhoenixOdometryThread.getInstance()
            .registerSignal(() -> Units.rotationsToDegrees(canandgyro.getYaw()));
  }

  @Override
  public void updateInputs(GyroIOInputs inputs) {
    inputs.connected = canandgyro.isConnected();
    inputs.yawPosition = canandgyro.getRotation2d();
    inputs.yawVelocityRadPerSec = Units.rotationsToRadians(canandgyro.getAngularVelocityYaw());
    inputs.odometryYawTimestamps =
        yawTimestampQueue.stream().mapToDouble((Double value) -> value).toArray();
    inputs.odometryYawPositions =
        yawPositionQueue.stream()
            .map((Double value) -> Rotation2d.fromDegrees(value))
            .toArray(Rotation2d[]::new);
    yawTimestampQueue.clear();
    yawPositionQueue.clear();
  }
}
