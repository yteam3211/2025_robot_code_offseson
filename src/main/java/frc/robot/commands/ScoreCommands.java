package frc.robot.commands;

import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.lib.util.DriveToPointFactory;
import frc.lib.util.ITarget;
import frc.lib.util.LimelightHelpers;
import frc.lib.util.ReefPositions;
import frc.lib.util.ReefSidePosition;
import frc.robot.states.ClimbPosition;
import frc.robot.subsystems.climb.ClimbSubsystem;
import frc.robot.subsystems.swerve.SwerveSubsystem;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class ScoreCommands {
  public static final Command ScoreL4 = null;
  private ArmCommands armCommands;
  private IntakeCommands intakeCommands;
  private DriveToPointFactory driveToPointFactory;
  private SwerveSubsystem swerveSubsystem;
  private ClimbSubsystem ClimbSubsystem;

  public ScoreCommands(
      ArmCommands armCommands,
      IntakeCommands intakeCommands,
      DriveToPointFactory driveToPointFactory,
      SwerveSubsystem swerveSubsystem,
      ClimbSubsystem ClimbSubsystem) {
    this.armCommands = armCommands;
    this.intakeCommands = intakeCommands;
    this.driveToPointFactory = driveToPointFactory;
    this.swerveSubsystem = swerveSubsystem;
    this.ClimbSubsystem = ClimbSubsystem;
    this.targetPose2d = swerveSubsystem.getPose();

    NamedCommands.registerCommand("score_L4", scoreL4auto());
    NamedCommands.registerCommand("score_intke", ScoreL1());
  }

  public Command intakeStraitToArm() {
    return armCommands
        .elevatorUphDwonArmTopos()
        .andThen(
            intakeCommands
                .intakeCommand()
                .andThen(armCommands.passToArmFromintake().andThen(resetCommand())));
  }

  public Command intakeStayOnIntake() {
    return intakeCommands.intakeCommand().andThen(resetCommand());
  }

  // public Command intkeToArmUpSide() {
  // return armCommands.elevatorUpDwon().andThen(intakeCommands.downTakeIndexunil());
  // }
  public Command ScoreL1() {
    return intakeCommands.scoreL1Command().andThen(resetCommand());
  }

  public Command ScoreL2(BooleanSupplier isatpose) {
    return armCommands.scoreL2(isatpose).andThen(resetCommand());
  }

  public enum sideScore implements ITarget {
    left(1),
    right(-1);
    int side;

    private sideScore(int side) {
      this.side = side;
    }

    @Override
    public double getTarget() {
      return side;
    }
  }
  private Pose2d targetPose2d;
  public Command getClosestLeftRightPose(sideScore side) {
    Runnable m_callback =
        new Runnable() {
          public void run() {
            int checkarr;
            if (DriverStation.getAlliance().get() == Alliance.Blue) {
              checkarr = 17;
              
            } else {
              checkarr = 0;
            }
            targetPose2d = Pose2d.kZero;
            ReefSidePosition reefSidePosition[] = ReefPositions.getReefPositions();
            double rightTag = LimelightHelpers.getFiducialID("limelight-right");
            double leftTag = LimelightHelpers.getFiducialID("limelight-left");
            if (side == sideScore.left) {
              if (rightTag != -1 && rightTag != 0) {
                targetPose2d =
                    new Pose2d(
                        reefSidePosition[(int) rightTag - checkarr].getLeft(),
                        swerveSubsystem.getPose().getRotation());
              }
              if (leftTag != -1 && leftTag != 0) {
                targetPose2d =
                    new Pose2d(
                        reefSidePosition[(int) leftTag - checkarr].getLeft(),
                        swerveSubsystem.getPose().getRotation());
              }
            }
            if (side.getTarget() == sideScore.right.getTarget()) {
              if (rightTag != -1 && rightTag != 0) {
                targetPose2d =
                    new Pose2d(
                        reefSidePosition[(int) rightTag - checkarr].getRight(),
                        swerveSubsystem.getPose().getRotation());
              }
              if (leftTag != -1 && leftTag != 0) {
                targetPose2d =
                    new Pose2d(
                        reefSidePosition[(int) leftTag - checkarr].getRight(),
                        swerveSubsystem.getPose().getRotation());
              }
            }
          }
        };
    return Commands.runOnce(m_callback).andThen(driveToPointFactory.fineAlign(()->targetPose2d));
  }

  public Command testPidAuto(Supplier<Pose2d> newpose) {
    return driveToPointFactory.fineAlign(newpose);
  }

  public Command alegelowCommand() {
    return armCommands
        .alegeCommanLow()
        .andThen(Commands.waitSeconds(1))
        .andThen(resetCommandalge());
  }

  public Command alegehighCommand() {
    return armCommands
        .alegeCommanhgih()
        .andThen(Commands.waitSeconds(1))
        .andThen(resetCommandalge());
  }

  public Command netScore(BooleanSupplier plot) {
    return Commands.waitSeconds(0.3).andThen(armCommands.netScore(plot));
  }

  public Command resetCommandalge() {
    return armCommands.resetcommandalge().alongWith(intakeCommands.resetCommand());
  }

  public Command resetCommand() {
    return armCommands.resetcommand().alongWith(intakeCommands.resetCommand());
  }

  public Command resetCommandsuper() {
    return armCommands.resetCommandsuper().alongWith(intakeCommands.resetCommand());
  }

  public Command ScoreL3(BooleanSupplier isatpose) {
    return armCommands.scoreL3(isatpose).andThen(resetCommand());
  }

  public Command climbslow() {
    return Commands.run(() -> ClimbSubsystem.setOutput(ClimbPosition.Move.getTarget()));
  }

  public Command climbfast() {
    return Commands.run(() -> ClimbSubsystem.setOutput(ClimbPosition.MoveFast.getTarget()));
  }

  public Command climbstop() {
    return Commands.run(() -> ClimbSubsystem.setOutput(ClimbPosition.Hold.getTarget()));
  }

  private boolean isCommandWOrking = false;

  public boolean isCommandWOrking() {
    if (isCommandWOrking) {
      isCommandWOrking = false;
      return true;
    }
    return false;
  }

  public Command ScoreL4(BooleanSupplier isatpose) {
    return armCommands.scoreL4(isatpose).andThen(resetCommand());
  }

  public Command scoreL4auto() {
    return armCommands.scoreL4auto().andThen(resetCommand());
  }
}
