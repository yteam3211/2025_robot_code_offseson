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
import frc.robot.Buttons.SwerveButtons;
import frc.robot.Buttons.defaultbutton;
import frc.robot.commands.IntakeCommands;
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
  public final RobotSubsystems subsystems;
  // Controller
  private final Controller controller;
  private final IntakeCommands intakeCommands;
  // private final ArmCommands armCommands;

  // Dashboard inputs
  private final LoggedDashboardChooser<Command> autoChooser;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    subsystems = new RobotSubsystems();
    controller = new Controller();
    intakeCommands =
        new IntakeCommands(
            subsystems.IntakeGriper, subsystems.intakepitch, subsystems.intakeindexer);
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
  private void configureButtonBindings() {
    // controller.swervecontroller.circle().onTrue(intakeCommands.downTakeIndex());
    // controller.swervecontroller.cross().onTrue(intakeCommands.upTakeIndex());
    // controller.swervecontroller.square().onTrue(intakeCommands.scoreL1Command());
    controller.swervecontroller.circle().onTrue(subsystems.elevator.setHeightCommand(() -> 50));
    controller.swervecontroller.square().onTrue(subsystems.elevator.setHeightCommand(() -> 0));
    SwerveButtons.loadButtons(controller, subsystems);
    defaultbutton.loadButtons(controller, subsystems);
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
