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
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.lib.util.DriveToPointFactory;
import frc.robot.commands.ArmCommands;
import frc.robot.commands.IntakeCommands;
import frc.robot.commands.ScoreCommands;
<<<<<<< HEAD
import frc.robot.states.ClimbPosition;
=======
import frc.robot.states.armPitchState;
>>>>>>> 8bf65b46bdd64a085fa92d7a81bc72cd893179c1

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // subsystem container use this to get use the subsystems
  // subsystems
  public final RobotSubsystems subsystems;
  // Controller
  private final Controller controller;

  private final IntakeCommands intakeCommands;
  private final ArmCommands armCommands;
  private final ScoreCommands scoreCommands;
  private final DriveToPointFactory driveToPointFactory;
  // Dashboard inputs
  private final SendableChooser<Command> autoChooser;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    subsystems = new RobotSubsystems();
    controller = new Controller();

    intakeCommands =
        new IntakeCommands(
            subsystems.IntakeGriper, subsystems.intakepitch, subsystems.intakeindexer);
    driveToPointFactory = new DriveToPointFactory(subsystems.swerve);
    armCommands =
        new ArmCommands(
            subsystems.ArmGriper,
            subsystems.armpitch,
            subsystems.elevator,
            subsystems.IntakeGriper,
            subsystems.intakepitch);
    scoreCommands =
        new ScoreCommands(
            armCommands,
            intakeCommands,
            driveToPointFactory,
            subsystems.swerve,
            subsystems.ClimbSubsystem);
    // Set up auto routines
    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("autoChooser", autoChooser);
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link PS5Controller}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // driverButtom.loadButtons(controller, scoreCommands);
    // SubButton.loadButtons(controller, scoreCommands);
    // SwerveButtons.loadButtons(controller, subsystems);
    // defaultbutton.loadButtons(controller, subsystems);
<<<<<<< HEAD
    // controller.swervecontroller
    //  subsystems.ClimbSubsystem
    controller
        .swervecontroller
        .cross()
        .onTrue(subsystems.ClimbSubsystem.chengestatecCommand(ClimbPosition.MoveFast));
    controller
        .swervecontroller
        .triangle()
        .onTrue(subsystems.ClimbSubsystem.chengestatecCommand(ClimbPosition.Move));
    controller
        .swervecontroller
        .square()
        .onTrue(subsystems.ClimbSubsystem.chengestatecCommand(ClimbPosition.Hold));
    controller
        .swervecontroller
        .circle()
        .onTrue(Commands.runOnce(() -> subsystems.ClimbSubsystem.setpos(0)));
=======
    controller
        .testController
        .a()
        .onTrue(subsystems.armpitch.chengestateCommand(armPitchState.COLLECT));
    controller
        .testController
        .b()
        .onTrue(subsystems.armpitch.chengestateCommand(armPitchState.L3first));
    controller
        .testController
        .x()
        .onTrue(subsystems.armpitch.chengestateCommand(armPitchState.rest));
>>>>>>> 8bf65b46bdd64a085fa92d7a81bc72cd893179c1
  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}
