package frc.robot.subsystems.elvetor;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.geometry.Pose3d;
import frc.robot.Constants;
import frc.robot.states.Elevatorstates;

public class elevatorioreal implements elevatorio {
  public Elevatorstates state = Elevatorstates.Close;
  public Pose3d elevatorpPose = new Pose3d();
  public TalonFX motor = new TalonFX(Constants.elevatorcos.masterid);
  public TalonFX m_slave = new TalonFX(Constants.elevatorcos.slaveid);
  public ElevatorioinputsAutoLogged inputs = new ElevatorioinputsAutoLogged();

  public elevatorioreal() {
    updateInputs(inputs);
  }

  public void setslave() {
    m_slave.setControl(new Follower(motor.getDeviceID(), false));
  }

  @Override
  public void updateInputs(ElevatorioinputsAutoLogged inputs) {
    state = Elevatorstates.Close;
    elevatorpPose = inputs.elevatorpPose;
  }

  @Override
  public double gethight() {
    return motor.getPosition().getValueAsDouble();
  }

  @Override
  public void set(double speed) {
    motor.set(speed);
  }
}
