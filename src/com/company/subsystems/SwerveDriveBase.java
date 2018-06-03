package com.company.subsystems;

import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class SwerveDriveBase extends RobotDriveBase {
  private final double SPEED_DEADZONE = 0.05;
  private final double TURN_DEADZONE = 0.05;

  //In inches
  private double LENGTH = 24;
  private double WIDTH = 24;

  private SwerveModule frontLeft, frontRight, backLeft, backRight;

  private boolean fieldRelative = false;

  public SwerveDriveBase(SwerveModule frontLeft, SwerveModule frontRight, SwerveModule backLeft, SwerveModule backRight) {
    this.frontLeft = frontLeft;
    this.frontRight = frontRight;
    this.backLeft = backLeft;
    this.backRight = backRight;
  }

  public SwerveDriveBase(SwerveModule frontLeft, SwerveModule frontRight, SwerveModule backLeft,
      SwerveModule backRight, double length, double width) {

    this.frontLeft = frontLeft;
    this.frontRight = frontRight;
    this.backLeft = backLeft;
    this.backRight = backRight;

    setLength(length);
    setWidth(width);
  }

  public void setLength(double length) {
    LENGTH = length;
  }

  public void setWidth(double width) {
    WIDTH = width;
  }

  public void drive(double x, double y, double turn) {
    drive(x, y, turn, 0);
  }

  public void drive(double x, double y, double turn, double angle) {
    double r = Math.sqrt((LENGTH * LENGTH) + (WIDTH * WIDTH));
    y *= -1;
    
    // Rotated X input
    x = angle != 0 ? (x * Math.cos(Math.toRadians(angle))) - (y * Math.sin(Math.toRadians(angle))) : x;
		// Rotated Y input
		y = angle != 0 ? (x * Math.sin(angle)) + (y * Math.cos(angle)) : y;
    
    double a = x - turn * (LENGTH / r);
    double b = x + turn * (LENGTH / r);
    double c = y - turn * (WIDTH / r);
    double d = y + turn * (WIDTH / r);

    double backRightSpeed = Math.sqrt(Math.pow(a, 2) + Math.pow(d, 2));
    double backLeftSpeed = Math.sqrt(Math.pow(a, 2) + Math.pow(c, 2));
    double frontRightSpeed = Math.sqrt(Math.pow(b, 2) + Math.pow(d, 2));
    double frontLeftSpeed = Math.sqrt(Math.pow(b, 2) + Math.pow(c, 2));

    double backRightAngle = Math.atan2(a, d) / Math.PI * 180;
    double backLeftAngle = Math.atan2(a, c) / Math.PI * 180;
    double frontRightAngle = Math.atan2(b, d) / Math.PI * 180;
    double frontLeftAngle = Math.atan2(b, c) / Math.PI * 180;

    frontLeft.set(frontLeftSpeed, frontLeftAngle);
    frontRight.set(frontRightSpeed, frontRightAngle);
    backLeft.set(backLeftSpeed, backLeftAngle);
    backRight.set(backRightSpeed, backRightAngle);
  }

  @Override
  public void initSendable(SendableBuilder builder) {

  }

  @Override
  public void stopMotor() {

  }

  @Override
  public String getDescription() {
    return null;
  }
}
