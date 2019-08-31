/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.ArcadeDrive;

/**
 * An example subsystem. You can replace me with your own Subsystem.
 */
public class Drive extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private Spark left1 = new Spark(0);
  private Spark left2 = new Spark(1);
  private Spark right1 = new Spark(2);
  private Spark right2 = new Spark(3);

  private AHRS gyro;
  private Encoder encoder;

  public Drive() {
    initGyro();
    initEncoder();
  }

  private void initEncoder() {
    encoder = new Encoder(0, 1, true, EncodingType.k4X);
    encoder.setSamplesToAverage(5); // used to reduce noise in period
    encoder.reset();
  }

  private void initGyro() {
    try {
      // gyro = new AHRS(SPI.Port.kMXP);
      gyro = new AHRS(SerialPort.Port.kUSB);

    } catch (RuntimeException ex) {
      System.out.println("Error instantiating navX-MXP:  " + ex.getMessage());
    }
  }

  public void tankDrive(double left, double right) {
    left1.set(left);
    left2.set(left);
    right1.set(-right);
    right2.set(-right);
  }

  public void stop() {
    tankDrive(0, 0);
  }

  public double getGyroAngleDeg() {
    return gyro.getAngle();
  }

  @Override
  public void periodic() {// debugging code: display value on smartDashboard
    SmartDashboard.putNumber("gyro value", getGyroAngleDeg());
    SmartDashboard.putNumber("encoder value", getEncoder());
  }

  public double getEncoder() {
    return encoder.get() * 5.8 * Math.PI / 256.0;

  }

  public void resetEncoder() {
    encoder.reset();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new ChezyDrive());
  }
}
