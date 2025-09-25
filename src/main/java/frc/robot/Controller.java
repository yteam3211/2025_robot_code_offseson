package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;

public class Controller {
  public final CommandPS5Controller swervecontroller = new CommandPS5Controller(0);
  public final CommandPS5Controller subcontroller = new CommandPS5Controller(1);

  public Controller() {}
}
