package frc.robot.systems;
import frc.robot.Robot;                                 // Core Robot
import frc.robot.utils;
import frc.robot.Constants;                             // Cross Robot Varriables Centralized

import edu.wpi.first.math.MathUtil;                     // Mathematics tools
import edu.wpi.first.util.sendable.SendableRegistry;

public final class drive {
    private drive () {}
    public static void controlPeriodic() {              // Periodic to update drive system states
        Robot.drive_difDrive.feed();
        double robot_drive_x = MathUtil.applyDeadband(
            Robot.drivestation_driver.getRightTriggerAxis()
            -
            Robot.drivestation_driver.getLeftTriggerAxis(),
            Constants.operator.tuning_driver_deadband);
        double robot_drive_y = MathUtil.applyDeadband(
            Robot.drivestation_driver.getLeftX(),
        Constants.operator.tuning_driver_deadband);
        Robot.drive_difDrive.arcadeDrive(utils.map(robot_drive_x, -1, 1, -Constants.drive.tuning_speedMax, Constants.drive.tuning_speedMax), utils.map(robot_drive_y, -1, 1, -Constants.drive.tuning_speedMax, Constants.drive.tuning_speedMax));
    }
    public static void startup() {                     // Sets up drive motors
        // Reset Motors to factory base for core function settings
        Robot.drive_leftMaster.restoreFactoryDefaults();
        Robot.drive_leftSlave.restoreFactoryDefaults();
        Robot.drive_rightMaster.restoreFactoryDefaults();
        Robot.drive_rightSlave.restoreFactoryDefaults();
        initFollowers();
        SendableRegistry.addChild(Robot.drive_difDrive, Robot.drive_leftMaster);
        SendableRegistry.addChild(Robot.drive_difDrive, Robot.drive_rightMaster);
        Robot.drive_leftMaster.setInverted(Constants.drive.rev_left);
        Robot.drive_rightMaster.setInverted(Constants.drive.rev_right);
        zeroEncoders();
        // Setting PWM safety for Drivetrain and Motor safety time out
        Robot.drive_difDrive.setSafetyEnabled(false);
        Robot.drive_difDrive.setExpiration(1);
        // Set acceleration
        Robot.drive_leftMaster.setClosedLoopRampRate(Constants.drive.slew_drv);
        Robot.drive_leftSlave.setClosedLoopRampRate(Constants.drive.slew_drv);
        Robot.drive_rightMaster.setClosedLoopRampRate(Constants.drive.slew_drv);
        Robot.drive_rightSlave.setClosedLoopRampRate(Constants.drive.slew_drv);
    }
    private static void initFollowers() {               // Within this function followers will be defined where a master and slave controller relationship will be defined
        // Setting Followers
        Robot.drive_leftSlave.follow(Robot.drive_leftMaster);
        Robot.drive_rightSlave.follow(Robot.drive_rightMaster);
    }
    private static void zeroEncoders() {                // Sets encoders to zero on init
        Robot.drive_left_encoder.setPosition(0);
        Robot.drive_right_encoder.setPosition(0);
    }
}
