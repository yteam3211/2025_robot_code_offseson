package frc.robot.subsystems.elvetor;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import frc.robot.states.Elavatorstates;

public class elevatoriosim implements elevatorio {
  public Elavatorstates state = Elavatorstates.Close;
  public Pose3d elevatorpPose = new Pose3d();
  public elevatoriosim() {
    
  }
  @Override
  public void updateInputs(elevatorioinputs inputs) {
    state = Elavatorstates.Close;
    elevatorpPose = new Pose3d(elevatorpPose.getX(),elevatorpPose.getY(),inputs.elevatorpPose.getZ(),new Rotation3d());
  }
  }
