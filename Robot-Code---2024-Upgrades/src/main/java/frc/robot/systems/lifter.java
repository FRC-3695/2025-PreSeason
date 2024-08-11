package frc.robot.systems;

import frc.robot.Robot; // Core Robot
import frc.robot.Constants; // Cross Robot Varriables Centralized
// REVLib
import com.revrobotics.CANSparkMax; // SparkMAX CAN Control Map and Watchdog
import com.revrobotics.CANSparkBase.IdleMode; // Provides IdleModes on SparkMAX

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class lifter {
    private static boolean code_lifter_cal = false; // Has lifter been calibrated

    private lifter() {
    }

    public static final void setup() {
        motors_setup();
    }

    public static final void controlPeriodic() { // Periodic function for scheduler
        lowerControl();
    }

    public static final void pumpUpTheJam(int x) {

    }

    private static void reach() { // Extends Lifter out to latch onto chain
        Robot.lifter_left_PID.setReference(Constants.lifter.rotation_climb, CANSparkMax.ControlType.kPosition);
        Robot.lifter_right_PID.setReference(Constants.lifter.rotation_climb, CANSparkMax.ControlType.kPosition);
    }

    private static void climb() { // Retracts Lifter in body of robot
        Robot.lifter_left_PID.setReference(0, CANSparkMax.ControlType.kPosition);
        Robot.lifter_right_PID.setReference(0, CANSparkMax.ControlType.kPosition);
    }

    private static void motors_setup() { // Sets up motors with reversing and PID Data
        // Restore Factory Defaults
        Robot.lifter_left_motor.restoreFactoryDefaults();
        Robot.lifter_right_motor.restoreFactoryDefaults();
        // Set Inversion
        Robot.lifter_left_motor.setInverted(Constants.lifter.rev_left);
        Robot.lifter_right_motor.setInverted(Constants.lifter.rev_right);
        // Set Motor Braking
        Robot.lifter_left_motor.setIdleMode(IdleMode.kBrake);
        Robot.lifter_right_motor.setIdleMode(IdleMode.kBrake);
        // Set PIDs Encoders
        Robot.lifter_left_PID.setFeedbackDevice(Robot.lifter_left_encoder);
        Robot.lifter_right_PID.setFeedbackDevice(Robot.lifter_right_encoder);
        // Set PIDs for each PID
        Robot.lifter_left_PID.setP(Constants.lifter.tuning_P);
        Robot.lifter_right_PID.setP(Constants.lifter.tuning_P);
        Robot.lifter_left_PID.setI(Constants.lifter.tuning_I);
        Robot.lifter_right_PID.setI(Constants.lifter.tuning_I);
        Robot.lifter_left_PID.setD(Constants.lifter.tuning_D);
        Robot.lifter_right_PID.setD(Constants.lifter.tuning_D);
        Robot.lifter_left_PID.setIZone(Constants.lifter.tuning_Iz);
        Robot.lifter_right_PID.setIZone(Constants.lifter.tuning_Iz);
        Robot.lifter_left_PID.setFF(Constants.lifter.tuning_FF);
        Robot.lifter_right_PID.setFF(Constants.lifter.tuning_FF);
        Robot.lifter_left_PID.setOutputRange(Constants.lifter.tuning_speedMin, Constants.lifter.tuning_speedMax);
        Robot.lifter_right_PID.setOutputRange(Constants.lifter.tuning_speedMin, Constants.lifter.tuning_speedMax);
    }

    private static void zero() { // Re-Homes lifter and Zeros motor controller's encoder's
        SmartDashboard.putBoolean("Lifter Calibration Failed", false);
        if (Robot.lifter_left_DIO.get() && Robot.lifter_right_DIO.get()) { // If already home
            Robot.lifter_left_encoder.setPosition(0);
            Robot.lifter_right_encoder.setPosition(0);
            code_lifter_cal = true;
        } else {
            while (Robot.lifter_left_DIO.get() || Robot.lifter_right_DIO.get()) { // If already home
                Robot.lifter_left_motor.set(Constants.lifter.rotation_cal_speed);
                Robot.lifter_right_motor.set(Constants.lifter.rotation_cal_speed);
            }
            while (true) {
                if (Robot.lifter_left_DIO.get()) { // Switch is engaged
                    Robot.lifter_left_motor.set(0);
                } else if (Robot.lifter_left_motor.getOutputCurrent() >= Constants.lifter.rotation_cal_maxWatt) {
                    Robot.lifter_left_motor.set(0);
                    Robot.lifter_right_motor.set(0);
                    SmartDashboard.putBoolean("Lifter Calibration Failed", true);
                    break;
                } else { // Switch is not engaged
                    Robot.lifter_left_motor.set(-Constants.lifter.rotation_cal_speed);
                }
                if (Robot.lifter_right_DIO.get()) { // Switch is engaged
                    Robot.lifter_right_motor.set(0);
                } else if (Robot.lifter_right_motor.getOutputCurrent() >= Constants.lifter.rotation_cal_maxWatt) {
                    Robot.lifter_left_motor.set(0);
                    Robot.lifter_right_motor.set(0);
                    SmartDashboard.putBoolean("Lifter Calibration Failed", true);
                    break;
                } else { // Switch is not engaged
                    Robot.lifter_right_motor.set(-Constants.lifter.rotation_cal_speed);
                }
                if (Robot.lifter_left_DIO.get() && Robot.lifter_right_DIO.get()) {
                    Robot.lifter_left_motor.set(0);
                    Robot.lifter_right_motor.set(0);
                    Robot.lifter_left_encoder.setPosition(0);
                    Robot.lifter_right_encoder.setPosition(0);
                    code_lifter_cal = true;
                    break;
                } // Both Switches become Engaged
            }
        }
    }

    private static void basicControl() {
        if (code_lifter_cal) {
            if (Robot.drivestation_operator.getLeftBumper()) { // Lifter Deployment Triggered
                reach();
            } else { // Retract or Keep Lifter retracted
                climb();
            }
        } else {
            System.out.println("Pending Lifter Calibration");
        }
    }

    private static void lowerControl() {
        if (Robot.lifter_left_DIO.get()) {
            Robot.lifter_left_motor.set(0);
        } else if (Robot.drivestation_operator.getLeftBumper()) { // Lifter Deployment Triggered
            Robot.lifter_left_PID.setReference(Constants.lifter.rotation_climb, CANSparkMax.ControlType.kPosition);
        } else { // Retract or Keep Lifter retracted
            Robot.lifter_left_motor.set(0);
        }
        if (Robot.lifter_right_DIO.get()) {
            Robot.lifter_right_motor.set(0);
        } else if (Robot.drivestation_operator.getLeftBumper()) { // Lifter Deployment Triggered
            Robot.lifter_right_PID.setReference(Constants.lifter.rotation_climb, CANSparkMax.ControlType.kPosition);
        } else { // Retract or Keep Lifter retracted
            Robot.lifter_right_motor.set(0);
        }
    }
}
