package frc.robot.subsystems.elvetor;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import frc.robot.states.Elevatorstates;

import org.littletonrobotics.junction.Logger;

public class elevatoriosim implements elevatorio {

  private final DCMotor DRIVE_GEARBOX = DCMotor.getKrakenX60Foc(2);

  public Pose3d elevatorpPose = new Pose3d();
  public Elevatorstates state = Elevatorstates.Close;
  private ElevatorSim motor =
      new ElevatorSim(
          LinearSystemId.createElevatorSystem(DRIVE_GEARBOX, 9.85165, 0.071, 0.158227848),
          DRIVE_GEARBOX,
          0.17,
          50.0,
          true,
          0.17);

  @Override
  public void set(double speed) {
    motor.setInputVoltage(speed);
  }

  @Override
  public void updateInputs(ElevatorioinputsAutoLogged inputs) {
    this.elevatorpPose = inputs.elevatorpPose;
    this.state = Elevatorstates.Close;
    motor.update(0.02);
  }

  @Override
  public double gethight() {

    double posion = motor.getPositionMeters() - 0.17;
    return posion;
  }

  /*@Override
  public void resetEncoder() {
    motor.setState(0.17, 0);
    updatepose(new Pose3d(0, 0, 0.17, m_inputs.elevatorpPose.getRotation()));
  }*/

  @Override
  public boolean isElevatorDown() {
    return motor.getPositionMeters() == 0.17;
  }

  @Override
  public void setlevel(double pos) {
    motor.setState(pos, 0);
    updatepose(
        new Pose3d(
            this.elevatorpPose.getX(),
            this.elevatorpPose.getY(),
            motor.getPositionMeters(),
            this.elevatorpPose.getRotation()));
  }

  @Override
  public void updatepose(Pose3d newpose) {
    this.elevatorpPose = newpose;
  }
}
