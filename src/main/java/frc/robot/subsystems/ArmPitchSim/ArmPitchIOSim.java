package frc.robot.subsystems.ArmPitchSim;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.sim.TalonFXSimState;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ArmPitchIOSim implements ArmPitchIO {
  private TalonFX talon = new TalonFX(ArmPItchConstantsSim.m_PitchID, "rio");
  private TalonFXSimState simState = talon.getSimState();
  private SingleJointedArmSim armSim =
      new SingleJointedArmSim(
          LinearSystemId.createSingleJointedArmSystem(
              DCMotor.getKrakenX60(1),
              ArmPItchConstantsSim.jkgsquremeters,
              ArmPItchConstantsSim.gearRatio),
          DCMotor.getKrakenX60(1),
          ArmPItchConstantsSim.gearRatio,
          0.043,
          0,
          0,
          true,
          0);

  @Override
  public void updateInputs(ArmPitchInputs inputs) {}

  @Override
  public Command setSpeedPos(double speed) {
    return new InstantCommand();
  }

  @Override
  public Command setPos(Angle pos) {
    return new InstantCommand();
  }
}
