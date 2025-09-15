package frc.robot.subsystems.elvetor;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import frc.robot.states.Elevatorstates;
import org.littletonrobotics.junction.Logger;

public class elevatoriosim implements elevatorio {

  private final DCMotor DRIVE_GEARBOX = DCMotor.getKrakenX60Foc(2);

  public ElevatorioinputsAutoLogged inputs = new ElevatorioinputsAutoLogged();
  private ElevatorSim motor =
      new ElevatorSim(
          LinearSystemId.createElevatorSystem(DRIVE_GEARBOX, 9.85165, 0.071, 0.158227848),
          DRIVE_GEARBOX,
          0.17,
          2.45,
          true,
          0.17);

  public elevatoriosim() {
    updateInputs(inputs);
    motor.setInputVoltage(0);
  }

  @Override
  public void set(double speed) {
    motor.setInputVoltage(speed);
  }

  public void updateInputs(ElevatorioinputsAutoLogged inputs) {
    this.inputs.state = Elevatorstates.Close;
    this.inputs.elevatorpPose = inputs.elevatorpPose;
    Logger.recordOutput("Elevator/State", this.inputs.state);
    Logger.recordOutput("Elevator/EstimatedPos", this.inputs.getestametedpos());
  }

  @Override
  public double gethight() {
    Logger.recordOutput("Elevator/gethight", motor.getPositionMeters());
    return motor.getPositionMeters();
  }

  @Override
  public void resetEncoder() {
    motor.setState(0.17, 0);
  }

  @Override
  public boolean isElevatorDown() {
    return motor.getPositionMeters() <= 0.17;
  }
  @Override
  public void setlevel(double pos) {
    motor.setInputVoltage(0);
  }
}
