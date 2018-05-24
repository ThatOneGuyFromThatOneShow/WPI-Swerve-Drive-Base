package com.company.subsystems;

import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class SwerveDriveBase extends RobotDriveBase {
  private final double SPEED_DEADZONE = 0.05;
  private final double TURN_DEADZONE = 0.05;

  SwerveModule frontLeft, frontRight, backLeft, backRight;

  public SwerveDriveBase (SwerveModule frontLeft, SwerveModule frontRight, SwerveModule backLeft, SwerveModule backRight) {
    this.frontLeft = frontLeft;
    this.frontRight = frontRight;
    this.backLeft = backLeft;
    this.frontRight = frontRight;
  }

  public void move(double x, double y, double turn) {
    double[] angleSpeed = calculateAngleSpeed(x, y);
    double angle = angleSpeed[0];
    double speed = angleSpeed[1];

    double speedFL, speedFR, speedBL, speedBR;
    double angleFL, angleFR, angleBL, angleBR;

    if (speed < SPEED_DEADZONE && turn > TURN_DEADZONE) {
      //turn in place

      speedFL = Math.abs(turn);
      speedFR = Math.abs(turn);
      speedBL = Math.abs(turn);
      speedBR = Math.abs(turn);

      double turnMod = turn > 0 ? 0 : 180;

      angleFL = 45 + turnMod;
      angleFR = 135 + turnMod;
      angleBL = 225 - turnMod;
      angleBR = 315 - turnMod;

      frontLeft.setSpeed(speedFL);
      frontLeft.goToAngle(angleFL);
      frontRight.setSpeed(speedFR);
      frontRight.goToAngle(angleFR);
      backLeft.setSpeed(speedBL);
      backLeft.goToAngle(angleBL);
      backRight.setSpeed(speedBR);
      backRight.goToAngle(angleBR);

    } else if (speed > SPEED_DEADZONE && turn > TURN_DEADZONE) {
      //turn in motion


    } else if (speed > SPEED_DEADZONE && turn < TURN_DEADZONE) {
      //move w/o turn

      frontLeft.setSpeed(speed);
      frontLeft.goToAngle(angle);
      frontRight.setSpeed(speed);
      frontRight.goToAngle(angle);
      backLeft.setSpeed(speed);
      backLeft.goToAngle(angle);
      backRight.setSpeed(speed);
      backRight.goToAngle(angle);

    } else {
      frontLeft.setSpeed(0);
      frontRight.setSpeed(0);
      backLeft.setSpeed(0);
      backRight.setSpeed(0);

    }


  }

  private double[] calculateAngleSpeed(double x, double y) {
    double angle, speed;

    if (x == 0 && y == 0) {
      speed = 0;
      angle = 0;
    } else if (x == 0 && y < 0) {
      speed = -y;
      angle = 180;
    } else if (x == 0 && y > 0) {
      speed = y;
      angle = 0;
    } else if (x < 0 && y == 0) {
      speed = x;
      angle = 270;
    } else if (x < 0 && y < 0) {
      speed = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
      angle = Math.atan(-y/-x);
    } else if (x < 0 && y > 0) {
      speed = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
      angle = Math.atan(y/-x);
    } else if (x > 0 && y == 0) {
      speed = x;
      angle = 90;
    } else if (x > 0 && y < 0) {
      speed = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
      angle = Math.atan(-y/x);
    } else {
      speed = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
      angle = Math.atan(y/x);
    }

    double[] out = {angle, speed};

    return out;
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
