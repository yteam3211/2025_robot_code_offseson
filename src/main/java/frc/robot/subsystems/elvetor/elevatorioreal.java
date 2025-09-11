package frc.robot.subsystems.elvetor;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.geometry.Pose3d;
import frc.robot.Constants;
import frc.robot.states.Elavatorstates;

public class elevatorioreal implements elevatorio {
  public Elavatorstates state = Elavatorstates.Close;
  public Pose3d elevatorpPose = new Pose3d();
  public TalonFX m_master = new TalonFX(Constants.elevatorcos.masterid);
  public TalonFX m_slave = new TalonFX(Constants.elevatorcos.slaveid);
  public elevatorioinputsAutoLogged inputs = new elevatorioinputsAutoLogged();

  public elevatorioreal() {
    updateInputs(inputs);
  }

  @Override
  public void updateInputs(elevatorioinputsAutoLogged inputs) {
    state = Elavatorstates.Close;
    elevatorpPose = inputs.elevatorpPose;
  }

  @Override
  public void set(double speed) {
    m_master.set(speed);
  }
}
