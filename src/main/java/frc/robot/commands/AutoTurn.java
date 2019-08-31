
package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Drive;

/**
 * An example command. You can replace me with your own command.
 */
public class AutoTurn extends Command {

  private Drive drive = Robot.drive;

  // --------------\import files/---------/local variables\---------------

  private PIDController pidController;

  public AutoTurn(double angle) {
    // super(config.kP, config.kI, config.kD);
    requires(drive);
    pidController = new PIDController(0.9, 0.02, 0.07, new PIDSource() {

      @Override
      public void setPIDSourceType(PIDSourceType pidSource) {
      }

      @Override
      public double pidGet() {
        return drive.getGyroAngleDeg();
      }

      @Override
      public PIDSourceType getPIDSourceType() {
        return null;
      }
    }, new PIDOutput() {

      @Override
      public void pidWrite(double output) {
        drive.tankDrive(output, -output);
      }
    });
    pidController.disable();
    pidController.setSetpoint(angle);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    pidController.enable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (pidController.getError() < 5);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    pidController.disable();
    drive.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
