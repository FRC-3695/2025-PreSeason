package frc.robot.systems;
import frc.robot.Robot;                                         // Core Robot
import frc.robot.Constants;                                     // Cross Robot Varriables Centralized
import frc.robot.utils;
//- import frc.robot.Constants.intake;
// REVLib Libraries
//- import com.revrobotics.CANSparkMax;                     // SparkMAX CAN Control Map and Watchdog
//- import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;           // Provides IdleModes on SparkMAX
// WPILib  Libraries
import edu.wpi.first.math.MathUtil;


public class manipulator {
    static double intake_feeder_speed;
    static double intake_position_speed;
    private manipulator() {}
    public static final void startup() {
        motorSetup();
    }
    public static final void controlPeriodic() {
        /*if (Robot.drivestation_operator.getLeftTriggerAxis() > Constants.operator.tuning_manip_deadband) {
            Robot.intake_feeder_motor.set(-utils.map(Robot.drivestation_operator.getLeftTriggerAxis(), 0, 1, .75, 1));
        } else {
            Robot.intake_feeder_motor.set(0);
        }*/
        if (Robot.intake_retract_DIO.get() && Robot.drivestation_operator.getLeftY()<= 0) {
            Robot.intake_position_motor.set(MathUtil.applyDeadband(Robot.drivestation_operator.getLeftY(), Constants.operator.tuning_manip_deadband));
        } else if (Robot.intake_deploy_DIO.get() && Robot.drivestation_operator.getLeftY()> 0) {
            Robot.intake_position_motor.set(MathUtil.applyDeadband(Robot.drivestation_operator.getLeftY(), Constants.operator.tuning_manip_deadband));
        } else {
            Robot.intake_position_motor.set(0);
        }
        if (Robot.drivestation_operator.getBButton()) {
            intake_feeder_speed = Constants.intake.feed_tuning_speedMin;
        } else if (Robot.drivestation_operator.getAButton()) {
            intake_feeder_speed = -Constants.intake.feed_tuning_speedMax;
        } else {
            intake_feeder_speed = 0;
        }
        Robot.intake_feeder_motor.set(intake_feeder_speed/Constants.intake.intake_tuning_clamp);
        if (Robot.drivestation_operator.getRightTriggerAxis() > Constants.operator.tuning_manip_deadband) {
            Robot.shooter_left_motor.set(utils.map(Robot.drivestation_operator.getRightTriggerAxis(), Constants.operator.tuning_manip_deadband, 1, .5, 1));
            Robot.shooter_right_motor.set(utils.map(Robot.drivestation_operator.getRightTriggerAxis(), Constants.operator.tuning_manip_deadband, 1, .5, 1));
        } else {
            Robot.shooter_left_motor.set(0);
            Robot.shooter_right_motor.set(0);
        }
    }
    /*private static void intake_deploy() {                       // Deploys intake 

    }
    private static void intake_retract() {                      // Retracts intake
        Robot.intake_position_PID.setReference(0, ControlType.kPosition);
        if (Robot.intake_retract_DIO.get()) {
            while (Robot.intake_retract_DIO.get()) {
                Robot.intake_position_motor.set(Constants.intake.rotation_retract_unCalSpd);
            }
            Robot.intake_position_motor.set(0);
        }
    }
    private static void intake_feed_floor() {                   // Runs feeder on intake until ring is pulled in from floor

    }
    private static void intake_feed_sky() {                     // Runs feeder on intake until ring is pulled in from above

    }
    private static void intake_clear() {                        // Reverses feeder on intake and completes a couple clearing cycles

    }
    private static void shooter_spinUp() {                      // Spins up motor to set RPM with PID
        SlewRateLimiter slewLim_SpinUp=
            new SlewRateLimiter(Constants.shooter.slew_spinUp);
        Robot.shooter_left_PID.setReference(slewLim_SpinUp.calculate(Constants.shooter.tuning_RPM), CANSparkMax.ControlType.kVelocity);
        Robot.shooter_right_PID.setReference(slewLim_SpinUp.calculate(Constants.shooter.tuning_RPM), CANSparkMax.ControlType.kVelocity);
    }
    private static void shooter_spinDown() {                    // Spins down via free wheeling
        Robot.shooter_left_PID.setReference(0, CANSparkMax.ControlType.kVelocity);
        Robot.shooter_right_PID.setReference(0, CANSparkMax.ControlType.kVelocity);
    }*/
    public static final void motorSetup() {                     // Sets up parameters for intake & shooter motor controllers
        //  *************************   Intake Setup  *************************
        // Zeroing motor settings
        Robot.intake_position_motor.restoreFactoryDefaults();
        Robot.intake_feeder_motor.restoreFactoryDefaults();
        // Set Motor IdleMode
        Robot.intake_position_motor.setIdleMode(IdleMode.kBrake);
        // Setting PID Loop Feed Back Devices
        Robot.intake_position_PID.setFeedbackDevice(Robot.intake_position_encoder);
        // Setting Encoders polling time
        Robot.intake_position_encoder.setMeasurementPeriod(8);
        // Loading PIDs into Motor
        Robot.intake_position_PID.setP(Constants.intake.posi_tuning_P);
        Robot.intake_position_PID.setI(Constants.intake.posi_tuning_I);
        Robot.intake_position_PID.setD(Constants.intake.posi_tuning_D);
        Robot.intake_position_PID.setIZone(Constants.intake.posi_tuning_Iz);
        Robot.intake_position_PID.setFF(Constants.intake.posi_tuning_FF);
        Robot.intake_feeder_PID.setP(Constants.intake.feed_tuning_P);
        Robot.intake_feeder_PID.setI(Constants.intake.feed_tuning_I);
        Robot.intake_feeder_PID.setD(Constants.intake.feed_tuning_D);
        Robot.intake_feeder_PID.setIZone(Constants.intake.feed_tuning_Iz);
        Robot.intake_feeder_PID.setFF(Constants.intake.feed_tuning_FF);
        // Setting Speed Limits for Motion
        Robot.intake_position_PID.setOutputRange(Constants.intake.posi_tuning_speedMin, Constants.intake.posi_tuning_speedMax);
        Robot.intake_feeder_PID.setOutputRange(Constants.intake.feed_tuning_speedMin, Constants.intake.feed_tuning_speedMax);
        //  *************************  Shooter Setup  *************************
        // Zeroing motor settings
        Robot.shooter_left_motor.restoreFactoryDefaults();
        Robot.shooter_right_motor.restoreFactoryDefaults();
        // Setting IdleMode
        Robot.shooter_left_motor.setIdleMode(IdleMode.kCoast);
        Robot.shooter_right_motor.setIdleMode(IdleMode.kCoast);
        // Reversing correct motor for forward operation
        Robot.shooter_left_motor.setInverted(Constants.shooter.rev_left);
        Robot.shooter_right_motor.setInverted(Constants.shooter.rev_right);
        // Setting PID Loop Feed Back Devices
        Robot.shooter_left_PID.setFeedbackDevice(Robot.shooter_left_encoder);
        Robot.shooter_right_PID.setFeedbackDevice(Robot.shooter_right_encoder);
        // Setting Encoders polling time
        Robot.shooter_left_encoder.setMeasurementPeriod(64);
        Robot.shooter_right_encoder.setMeasurementPeriod(64);
        // Loading Base PIDs into Motors
        Robot.shooter_left_PID.setP(Constants.shooter.tuning_P);            
        Robot.shooter_right_PID.setP(Constants.shooter.tuning_P);
        Robot.shooter_left_PID.setI(Constants.shooter.tuning_I);            
        Robot.shooter_right_PID.setI(Constants.shooter.tuning_I);
        Robot.shooter_left_PID.setD(Constants.shooter.tuning_D);            
        Robot.shooter_right_PID.setD(Constants.shooter.tuning_D);
        Robot.shooter_left_PID.setIZone(Constants.shooter.tuning_Iz);       
        Robot.shooter_right_PID.setIZone(Constants.shooter.tuning_Iz);
        Robot.shooter_left_PID.setFF(Constants.shooter.tuning_FF);          
        Robot.shooter_right_PID.setFF(Constants.shooter.tuning_FF);
    }
    public static final void zeroIntake() {                     // Zeros home for intake
        while (!Robot.intake_retract_DIO.get()) {
            Robot.intake_position_motor.set(Constants.intake.rotation_cal_speed);
        }
        Robot.intake_position_motor.set(0);
        while (Robot.intake_retract_DIO.get()) {
            Robot.intake_position_motor.set(-Constants.intake.rotation_cal_speed);
        }
        Robot.intake_position_motor.set(0);
        Robot.intake_position_encoder.setPosition(0);
    }
}
