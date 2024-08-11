package frc.robot.systems;
import frc.robot.Robot;                                 // Core Robot
import frc.robot.Constants;                             // Cross Robot Varriables Centralized
import frc.robot.utils;                                 // utils to simplify FRC Programming life

public class power {
    static Boolean[] powerHub_activePorts;
    public static void setup() {
        Robot.power_hub.clearStickyFaults();
        Robot.power_hub.resetTotalEnergy();
        hubInitChannels();
    }
    public static void controlPeriodic(){
        driveCheck();
        lifterCheck();
        manipulatorCheck();
        shooterCheck();
        surgeCurrentCheck();
    }
    static boolean driveCheck_fail = false;
    static double tolerance_drive_power = 0.095;
    static double tolerance_drive_current = 0.20;
    private static void driveCheck(){
        // Checks for Voltage Drop to Drive Motors & Difference between the two
        if (Robot.power_hub.getCurrent(Constants.IDs.drive_leftRear_powerBus)/Robot.power_hub.getCurrent(Constants.IDs.drive_leftFront_powerBus)+tolerance_drive_current < 1) {
            utils.Logging(5, "Left Drive Master Loss");
            driveCheck_fail = true;
        } else if (Robot.drive_leftMaster.getBusVoltage()/Robot.power_hub.getVoltage()+tolerance_drive_power < 1) {
            utils.Logging(4, "Left Drive Master - \"Bad Power Connection\"");
            driveCheck_fail = true;
        }
        if (Robot.power_hub.getCurrent(Constants.IDs.drive_leftFront_powerBus)/Robot.power_hub.getCurrent(Constants.IDs.drive_leftRear_powerBus)+tolerance_drive_power < 1) {
            utils.Logging(5, "Left Drive Follower Loss");
            driveCheck_fail = true;
        } else if (Robot.drive_leftSlave.getBusVoltage()/Robot.power_hub.getVoltage()+tolerance_drive_power < 1) {
            utils.Logging(4, "Left Drive Follower - \"Bad Power Connection\"");
            driveCheck_fail = true;
        }
        if (Robot.power_hub.getCurrent(Constants.IDs.drive_rightRear_powerBus)/Robot.power_hub.getCurrent(Constants.IDs.drive_rightFront_powerBus)+tolerance_drive_current < 1) {
            utils.Logging(5, "Right Drive Master Loss");
            driveCheck_fail = true;
        } else if (Robot.drive_rightMaster.getBusVoltage()/Robot.power_hub.getVoltage()+tolerance_drive_power < 1) {
            utils.Logging(4, "Right Drive Master - \"Bad Power Connection\"");
            driveCheck_fail = true;
        }
        if (Robot.power_hub.getCurrent(Constants.IDs.drive_rightFront_powerBus)/Robot.power_hub.getCurrent(Constants.IDs.drive_rightRear_powerBus)+tolerance_drive_current < 1) {
            utils.Logging(5, "Right Drive Follower Loss");
            driveCheck_fail = true;
        } else if (Robot.drive_rightSlave.getBusVoltage()/Robot.power_hub.getVoltage()+tolerance_drive_power < 1) {
            utils.Logging(4, "Right Drive Follower - \"Bad Power Connection\"");
            driveCheck_fail = true;
        }
    }
    static int lifterCheck_status = 0;  // 0 - Run | 1 - Alert | 2 - Stopped
    static long lifterCheck_count = 0;
    static double tolerance_lifter_power = 0.095;
    private static void lifterCheck(){
        if (Robot.lifter_left_motor.getBusVoltage()/Robot.power_hub.getVoltage()+tolerance_lifter_power < 1 && lifterCheck_status == 0) {
            utils.Logging(4, "Left Lifter - \"Bad Power Connection\"");
            lifterCheck_status = 1;
        }
        if (Robot.lifter_right_motor.getBusVoltage()/Robot.power_hub.getVoltage()+tolerance_lifter_power < 1 && lifterCheck_status == 0) {
            utils.Logging(4, "Right Lifter - \"Bad Power Connection\"");
            lifterCheck_status = 1;
        }
        if (lifterCheck_status == 1) {
            lifterCheck_count ++;
            if (lifterCheck_count >= 20 && lifterCheck_count <21) {
                utils.Logging(5, "Lifter - \"Critical Power Connection Failure\" Lifter Check Stopping");
                lifterCheck_status = 2;
            } else if (lifterCheck_count == 10) {
                utils.Logging(5, "Lifter - \"Critical Power Connection Failure\"");
                lifterCheck_status = 0;
            } else {
                lifterCheck_status = 0;
            }
        }
    }
    static int manipulatorCheck_status = 0;  // 0 - Run | 1 - Alert | 2 - Stopped
    static long manipulatorCheck_count = 0;
    static double tolerance_manipulator_power = 0.15;
    private static void manipulatorCheck(){
        if (Robot.intake_feeder_motor.getBusVoltage()/Robot.power_hub.getVoltage()+tolerance_manipulator_power < 1 && manipulatorCheck_status == 0) {
            utils.Logging(4, "Manipulator Intake - \"Bad Power Connection\"");
            manipulatorCheck_status = 1;
        }
        if (Robot.intake_position_motor.getBusVoltage()/Robot.power_hub.getVoltage()+tolerance_manipulator_power < 1 && manipulatorCheck_status == 0) {
            utils.Logging(4, "Manipulator Position - \"Bad Power Connection\"");
            manipulatorCheck_status = 1;
        }
        if (manipulatorCheck_status == 1) {
            manipulatorCheck_count ++;
            if (manipulatorCheck_count >= 20 && manipulatorCheck_count <21) {
                utils.Logging(5, "Manipulator - \"Critical Power Connection Failure\" Manipulator Check Stopping");
                manipulatorCheck_status = 2;
            } else if (manipulatorCheck_count == 10) {
                utils.Logging(5, "Manipulator - \"Critical Power Connection Failure\"");
                manipulatorCheck_status = 0;
            } else {
                manipulatorCheck_status = 0;
            }
        }
    }
    static int shooterCheck_status = 0;  // 0 - Run | 1 - Alert | 2 - Stopped
    static long shooterCheck_count = 0;
    static double tolerance_shooterCheck_power = 0.15;
    private static void shooterCheck(){
        if (Robot.shooter_left_motor.getBusVoltage()/Robot.power_hub.getVoltage()+tolerance_shooterCheck_power < 1 && shooterCheck_status == 0) {
            utils.Logging(4, "Left Fly-Wheel - \"Bad Power Connection\"");
            shooterCheck_status = 1;
        }
        if (Robot.shooter_right_motor.getBusVoltage()/Robot.power_hub.getVoltage()+tolerance_shooterCheck_power < 1 && shooterCheck_status == 0) {
            utils.Logging(4, "Right Fly-Wheel - \"Bad Power Connection\"");
            shooterCheck_status = 1;
        }
        if (shooterCheck_status == 1) {
            shooterCheck_count ++;
            if (shooterCheck_count >= 20 && shooterCheck_count <21) {
                utils.Logging(5, "Fly-Wheels - \"Critical Power Connection Failure\" Fly-Wheel Check Stopping");
                shooterCheck_status = 2;
            } else if (shooterCheck_count == 10) {
                utils.Logging(5, "Fly-Wheels - \"Critical Power Connection Failure\"");
                shooterCheck_status = 0;
            } else {
                shooterCheck_status = 0;
            }
        }
    }
    static int surgeCurrentLevel_High           = 40;
    static int surgeCurrentLevel_Breaker        = 50;
    static int surgeCurrentLevel_Critical       = 70;
    private static void surgeCurrentCheck(){
        for(int channel = 0; channel < powerHub_activePorts.length; channel++) {
            double port_current = Robot.power_hub.getCurrent(channel);
            if (port_current > surgeCurrentLevel_High) {
                utils.Logging(4, "Surge Current on PowerHub port \"Exceeds Breaker\" : "+channel+" | Current - "+port_current);
            } else if (port_current > surgeCurrentLevel_Breaker) {
                utils.Logging(5, "Surge Current on PowerHub port \"Exceeding Hub Limit\" : "+channel+" | Current - "+port_current);
            } else if (port_current > surgeCurrentLevel_Critical) {
                utils.Logging(5, "Surge Current on PowerHub port \"Exceeding Hub Limit\" : "+channel+" | Current - "+port_current);
            }
        }
    }
    private static void hubInitChannels(){
        powerHub_activePorts = new Boolean[Robot.power_hub.getNumChannels()];
        for(int channel = 0; channel < powerHub_activePorts.length; channel++) {
            if (Robot.power_hub.getCurrent(channel) > 0) {
                powerHub_activePorts[channel] = true;
                utils.Logging(1, "Device at PowerHub port : "+channel);
            } else {
                powerHub_activePorts[channel] = false;
            }
        }
    }
}
