package frc.robot.subsystems.elvetor;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import frc.robot.states.Elavatorstates;
import org.littletonrobotics.junction.Logger;

public class elevatoriosim implements elevatorio {

  private static final DCMotor DRIVE_GEARBOX = DCMotor.getKrakenX60Foc(2);

  public elevatorioinputsAutoLogged inputs = new elevatorioinputsAutoLogged();
  public DCMotorSim m_master =
      new DCMotorSim(
          LinearSystemId.createElevatorSystem(DRIVE_GEARBOX, 9.85165, 0.071, 5), DRIVE_GEARBOX);

  public elevatoriosim() {
    updateInputs(inputs);
    m_master.setInputVoltage(0);
  }

  @Override
  public void set(double speed) {
    m_master.setInputVoltage(speed);
  }

  public void updateInputs(elevatorioinputsAutoLogged inputs) {
    this.inputs.state = Elavatorstates.Close;
    this.inputs.elevatorpPose = (inputs.elevatorpPose);
    Logger.recordOutput("Elavator/State", this.inputs.state);
    Logger.recordOutput("Elavator/EstimatedPos", this.inputs.getestametedpos());
  }
}
