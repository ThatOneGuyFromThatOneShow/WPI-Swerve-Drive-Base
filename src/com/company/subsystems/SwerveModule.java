package com.company.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;

public class SwerveModule {
  private WPI_TalonSRX angleController;
  private SpeedController speedController;
  private double pulsesPerDegree;

  public SwerveModule(WPI_TalonSRX angleController, SpeedController speedController) {
    this.angleController = angleController;
    this.speedController = speedController;
  }

  public void goToAngle(double setpoint) { //0 - 359
    boolean turnRight = !(setpoint - getCurrentAngle360() < 0);
    double targetAngle = getCurrentAngle() + (turnRight ? setpoint : -setpoint);

    angleController.set(ControlMode.Position, targetAngle);
  }

  public void PIDF(double p, double i, double d, double f) {
    angleController.config_kP(0, p, 0);
    angleController.config_kI(0, i, 0);
    angleController.config_kD(0, d, 0);
    angleController.config_kF(0, f, 0);
  }

  public void setPPR(double PPR) {
    pulsesPerDegree = PPR / 360;
  }

  /**
   *
   * @return the angle from 0 to 359
   */
  public double getCurrentAngle360() {
    double angle = getCurrentAngle();

    if (angle < 0) {
      angle = 360 + (angle % 360);
    } else {
      angle %= 360;
    }

    return angle;
  }

  public double getCurrentAngle() {
    return angleController.getSelectedSensorPosition(0) / pulsesPerDegree;
  }

  public void setSpeed(double speed) {
    speedController.set(speed);
  }

  public void setCurrentAngle(double angle) {
    angleController.setSelectedSensorPosition((int) (angle * pulsesPerDegree), 0, 0);
  }

  public void setInverted(boolean inverted) {
    speedController.setInverted(inverted);
  }

  public boolean getInverted() {
    return speedController.getInverted();
  }

  public void reverse() {
    setInverted(!getInverted());
  }

  public void set(double speed, double angle) {
    setSpeed(speed);
    goToAngle(angle);
  }

}
