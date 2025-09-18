package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class Controller {
  public final CommandXboxController swervecontroller = new CommandXboxController(0);
  public final CommandXboxController subcontroller = new CommandXboxController(1);

  public Controller() {}
}
