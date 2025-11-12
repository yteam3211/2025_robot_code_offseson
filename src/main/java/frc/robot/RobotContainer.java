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
import frc.lib.util.DriveToPointFactory;
import frc.robot.Buttons.SubButton;
import frc.robot.Buttons.SwerveButtons;
import frc.robot.Buttons.defaultbutton;
import frc.robot.Buttons.driverButtom;
import frc.robot.commands.ArmCommands;
import frc.robot.commands.IntakeCommands;
import frc.robot.commands.ScoreCommands;

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
  protected SendableChooser<Command> autoChooser;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer(boolean drivelern) {
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
  private boolean isderiver = false;
  protected void configureButtonBindings() {
    if(isderiver){
      driverButtom.loadButtons(controller, scoreCommands);
      SubButton.loadButtons(controller, scoreCommands);
      SwerveButtons.loadButtons(controller, subsystems);
      defaultbutton.loadButtons(controller, subsystems);  
    }
    else{
      controller.swervecontroller.triangle().onTrue(subsystems.elevator.defer(null));
    }
    
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
