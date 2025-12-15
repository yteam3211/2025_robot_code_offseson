// Copyright 2021-2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PS5Controller;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.lib.util.DriveToPointFactory;
import frc.robot.Buttons.defaultbutton;
import frc.robot.commands.ArmCommands;
import frc.robot.commands.IntakeCommands;
import frc.robot.commands.ScoreCommands;
import frc.robot.commands.swerve.DriveCommands2;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // subsystem container use this to get use the subsystems
  // subsystems
  public RobotSubsystems subsystems;
  // Controller
  protected Controller controller;

  protected IntakeCommands intakeCommands;
  protected ArmCommands armCommands;
  protected ScoreCommands scoreCommands;
  protected DriveToPointFactory driveToPointFactory;
  // Dashboard inputs
  private final LoggedDashboardChooser<Command> autoChooser;
  // private SwerveSubsystem swerveSubsystem = new SwerveSubsystem();
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    subsystems = new RobotSubsystems();
    controller = new Controller();

    intakeCommands =
        new IntakeCommands(
            subsystems.IntakeGriper, subsystems.IntakePitchSim, subsystems.intakeindexer);
    // driveToPointFactory = new DriveToPointFactory(subsystems.drive);
    armCommands =
        new ArmCommands(
            subsystems.ArmGriper,
            subsystems.armpitch,
            subsystems.elevator2,
            subsystems.IntakeGriper,
            subsystems.IntakePitchSim);
    scoreCommands =
        new ScoreCommands(
            armCommands, intakeCommands, driveToPointFactory, subsystems.ClimbSubsystem);
    // Set up auto routines
    autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link PS5Controller}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private boolean isdriver = true;

  // private ArmPitchSim armPitchSim = new ArmPitchSim(new ArmPitchIOSim());
  // private elevator2 elevator2 = new elevator2(new elevatorIOsim());
  // private elevator2 elevetor = new elevator2(new elevatorIOreal());
  // private SwerveSubsystem swerverSubsystem = new SwerveSubsystem();

  private void configureButtonBindings() {
    if (isdriver) {
      // driverButtom.loadButtons(controller, scoreCommands);
      // SubButton.loadButtons(controller, scoreCommands);
      // SwerveButtons.loadButtons(controller, subsystems);
      defaultbutton.loadButtons(controller, subsystems);
      // controller
      //     .swervecontroller
      //     .triangle()
      //     .whileTrue(
      //         DriveCommands.joystickDriveAtAngle(
      //             subsystems.drive, () -> 0, () -> 1, () -> Rotation2d.fromDegrees(0)));
    } else {
      // controller
      //     .swervecontroller
      //     .triangle()
      //
      // .onTrue(subsystems.IntakePitchSim.changestateCommand(IntakePitchstate.INTAKE_POSITION));
    }
    autoChooser.addOption(
        "Drive Wheel Radius Characterization",
        DriveCommands2.wheelRadiusCharacterization(subsystems.drive));
    autoChooser.addOption(
        "Drive Simple FF Characterization",
        DriveCommands2.feedforwardCharacterization(subsystems.drive));
    autoChooser.addOption(
        "Drive SysId (Quasistatic Forward)",
        subsystems.drive.sysIdQuasistatic(SysIdRoutine.Direction.kForward));
    autoChooser.addOption(
        "Drive SysId (Quasistatic Reverse)",
        subsystems.drive.sysIdQuasistatic(SysIdRoutine.Direction.kReverse));
    autoChooser.addOption(
        "Drive SysId (Dynamic Forward)",
        subsystems.drive.sysIdDynamic(SysIdRoutine.Direction.kForward));
    autoChooser.addOption(
        "Drive SysId (Dynamic Reverse)",
        subsystems.drive.sysIdDynamic(SysIdRoutine.Direction.kReverse));
  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.get();
  }
}
