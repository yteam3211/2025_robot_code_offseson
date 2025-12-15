package frc.robot;

import edu.wpi.first.wpilibj.PS5Controller;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class Controller {
  public final CommandPS5Controller swervecontroller = new CommandPS5Controller(0);
  public final CommandPS5Controller subcontroller = new CommandPS5Controller(1);
  public final PS5Controller driverPrimitive = new PS5Controller(0);
  public final CommandXboxController test = new CommandXboxController(0);

  public Controller() {}
}
