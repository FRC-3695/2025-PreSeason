package frc.robot.systems;
import frc.robot.Robot;                                         // Core Robot
import frc.robot.Constants;                                     // Cross Robot Varriables Centralized
import frc.robot.testingConstants;                              // Cross Robot Testing Varriables Centralized
import frc.robot.utils;
import frc.robot.systems.power;

import com.revrobotics.CANSparkBase.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class testingAnHealth {  
    static String selection_current;
    static String selection_old;
    private static SendableChooser<String> test_options = new SendableChooser<>();
    private testingAnHealth() {}
    public static void setup() {
        test_options.setDefaultOption(testingConstants.testing.runnables[0], testingConstants.testing.runnables[0]);
        for (int i = 1; i < testingConstants.testing.runnables.length; i++) {
            test_options.addOption(testingConstants.testing.runnables[i], testingConstants.testing.runnables[i]);
        }
        SmartDashboard.putData("Testing Modes", test_options);
    }
    public static void periodic() {
        selection_current = test_options.getSelected();
        if (selection_current != selection_old) {
            selection_old = selection_current;
            switch (selection_current) {                                                    // Starts
                case "Motor Drive Manual":
                    break;
                case "Shooter - Static Run":
                case "Shooter - Calibration":
                    manipulator.motorSetup();
                    shooter_dashboard_start();
                    break;
                case "View - Sensors":
                    break;
                case "Power - Check":
                    power.setup();
                    break;
                default:  // No Selection Made for Mode provided
                    utils.Logging(0, "No Logging System Selected");
                    break;
            }
        }
        switch (selection_current) {                                                        // Periodic
            case "Motor Drive Manual":
                Robot.lifter_left_motor.set(-Robot.drivestation_operator.getLeftY()/3);
                Robot.lifter_right_motor.set(-Robot.drivestation_operator.getLeftY()/3);
                break;
            case "Shooter - Calibration":
                shooter_dashboard_renew();
                break;
            case "View - Sensors":
                dashboard_DIO();
                break;
            case "Shooter - Static Run":
                shooter_static_run();
                break;
            case "Power - Check":
                power.controlPeriodic();
                break;
            default:  // No Selection Made for Mode provided
                break;
        }
    }
    // -----------------------  Test periodics and code for ** Shooter **  -----------------------
    private static final void shooter_dashboard_start() {
        SmartDashboard.putNumber("Shooter P", Constants.shooter.tuning_P);
        SmartDashboard.putNumber("Shooter I", Constants.shooter.tuning_I);
        SmartDashboard.putNumber("Shooter D", Constants.shooter.tuning_D);
        SmartDashboard.putNumber("Shooter I Zone", Constants.shooter.tuning_Iz);
        SmartDashboard.putNumber("Shooter FF", Constants.shooter.tuning_FF);
        SmartDashboard.putNumber("Shooter RPM", Constants.shooter.tuning_RPM);
        SmartDashboard.putNumber("Shooter Running RPM Left", Robot.shooter_left_encoder.getVelocity());
        SmartDashboard.putNumber("Shooter Running RPM Right", Robot.shooter_right_encoder.getVelocity());
    }
    private static final void shooter_dashboard_renew() {
        if (Constants.shooter.tuning_P != SmartDashboard.getNumber("Shooter P", Constants.shooter.tuning_P)) {
            Constants.shooter.tuning_P = SmartDashboard.getNumber("Shooter P", 0);
            Robot.shooter_left_PID.setP(Constants.shooter.tuning_P);            
            Robot.shooter_right_PID.setP(Constants.shooter.tuning_P);
        }
        if (Constants.shooter.tuning_I != SmartDashboard.getNumber("Shooter I", Constants.shooter.tuning_I)) {
            Constants.shooter.tuning_I = SmartDashboard.getNumber("Shooter I", 0);
            Robot.shooter_left_PID.setI(Constants.shooter.tuning_I);            
            Robot.shooter_right_PID.setI(Constants.shooter.tuning_I);
        }
        if (Constants.shooter.tuning_D != SmartDashboard.getNumber("Shooter D", Constants.shooter.tuning_D)) {
            Constants.shooter.tuning_D = SmartDashboard.getNumber("Shooter D", 0);
            Robot.shooter_left_PID.setD(Constants.shooter.tuning_D);            
            Robot.shooter_right_PID.setD(Constants.shooter.tuning_D);
        }
        if (Constants.shooter.tuning_Iz != SmartDashboard.getNumber("Shooter I Zone", Constants.shooter.tuning_Iz)) {
            Constants.shooter.tuning_Iz = SmartDashboard.getNumber("Shooter I Zone", 0);
            Robot.shooter_left_PID.setIZone(Constants.shooter.tuning_Iz);       
            Robot.shooter_right_PID.setIZone(Constants.shooter.tuning_Iz);
        }
        if (Constants.shooter.tuning_FF != SmartDashboard.getNumber("Shooter FF", Constants.shooter.tuning_FF)) {
            Constants.shooter.tuning_FF = SmartDashboard.getNumber("Shooter FF", 0);
            Robot.shooter_left_PID.setFF(Constants.shooter.tuning_FF);          
            Robot.shooter_right_PID.setFF(Constants.shooter.tuning_FF);
        }
        if (Constants.shooter.tuning_RPM != SmartDashboard.getNumber("Shooter RPM", Constants.shooter.tuning_RPM)) {
            Constants.shooter.tuning_RPM = SmartDashboard.getNumber("Shooter RPM", 0);
            if(Robot.shooter_left_motor.get() != 0 && Robot.shooter_right_motor.get() != 0) {
                Constants.shooter.tuning_RPM = SmartDashboard.getNumber("Shooter RPM", Constants.shooter.tuning_RPM);
                // Set RPM
                Robot.shooter_left_PID.setReference(Constants.shooter.tuning_RPM, ControlType.kVelocity);
                Robot.shooter_right_PID.setReference(Constants.shooter.tuning_RPM, ControlType.kVelocity);
            }
        } else {
            SmartDashboard.putNumber("Shooter Running RPM Left", Constants.shooter.tuning_RPM);
            SmartDashboard.putNumber("Shooter Running RPM Right", Constants.shooter.tuning_RPM);
        }
    }
    private static final void shooter_static_run() {
        Robot.shooter_left_motor.set(1);
        Robot.shooter_right_motor.set(1);
    }
    private static final void dashboard_DIO() {
        SmartDashboard.putNumber("Shooter Running RPM Left", Robot.shooter_left_encoder.getVelocity());
        SmartDashboard.putNumber("Shooter Running RPM Right", Robot.shooter_right_encoder.getVelocity());
        SmartDashboard.putBoolean("Sensor Lifter - Left", Robot.lifter_left_DIO.get());
        SmartDashboard.putBoolean("Sensor Lifter - Right", Robot.lifter_right_DIO.get());
        SmartDashboard.putBoolean("Sensor Intake - Home", Robot.intake_retract_DIO.get());
        SmartDashboard.putBoolean("Sensor Intake - Deploy", Robot.intake_deploy_DIO.get());
    }
}
