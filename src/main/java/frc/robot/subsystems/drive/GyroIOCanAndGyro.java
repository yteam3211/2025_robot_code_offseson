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
            .setAccelerationFramePeriod(0.02)
            .setAngularPositionFramePeriod(0.02)
            .setAngularVelocityFramePeriod(0.02)
            .setStatusFramePeriod(0.02)
            .setYawFramePeriod(0.02);
    boolean settingsapplied = canandgyro.setSettings(settings);
    while (!settingsapplied) {
      settingsapplied = canandgyro.setSettings(settings);
    }
    yawTimestampQueue = PhoenixOdometryThread.getInstance().makeTimestampQueue();
    yawPositionQueue = PhoenixOdometryThread.getInstance().registerSignal(canandgyro::getYaw);
  }

  @Override
  public void updateInputs(GyroIOInputs inputs) {
    inputs.connected = canandgyro.isConnected();
    inputs.yawPosition = Rotation2d.fromRotations(canandgyro.getYaw());
    inputs.yawVelocityRadPerSec = Units.rotationsToRadians(canandgyro.getAngularVelocityYaw());
    inputs.odometryYawTimestamps =
        yawTimestampQueue.stream().mapToDouble((Double value) -> value).toArray();
    inputs.odometryYawPositions =
        yawPositionQueue.stream()
            .map((Double value) -> Rotation2d.fromDegrees(value))
            .toArray(Rotation2d[]::new);
  }
}
