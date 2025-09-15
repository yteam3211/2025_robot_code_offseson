package frc.robot.subsystems.elvetor;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants;
import frc.robot.states.Elevatorstates;

public class elevatorioreal implements elevatorio {
  public Elevatorstates state = Elevatorstates.Close;
  public Pose3d elevatorpPose = new Pose3d();
  private TalonFX motor = new TalonFX(Constants.elevatorcos.masterid);
  private TalonFX m_slave = new TalonFX(Constants.elevatorcos.slaveid);
  private DigitalInput m_closeSwitch;
  public ElevatorioinputsAutoLogged inputs = new ElevatorioinputsAutoLogged();
  public elevatorioreal() {
    updateInputs(inputs);
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
  @Override
  public void resetEncoder() {
    motor.setPosition(0);
  }
  public boolean isElevatorDown() {
    return !m_closeSwitch.get();
  }

  @Override
  public void setslave(int masterid, int slaveid) {

  }

  @Override
  public void setlevel(double pos) {
    return;
    //motor.setControl(motionMagicVoltage.withPosition(pos).withSlot(0));
  }
}
