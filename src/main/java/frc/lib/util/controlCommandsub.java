package frc.lib.util;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.function.BooleanSupplier;

public class controlCommandsub {
  private Command command = new InstantCommand();

  public Command run(Runnable run, BooleanSupplier until, SubsystemBase... req) {
    Command tempCommand = Commands.run(run, req);
    return tempCommand.until(until);
  }

  public Command run(Runnable run, SubsystemBase... req) {
    return Commands.runOnce(run, req);
  }

  public Command cancel() {
    return Commands.runOnce(() -> command.cancel());
  }
}
