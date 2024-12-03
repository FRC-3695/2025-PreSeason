package frc.robot.systems;

import frc.robot.Robot;
import com.revrobotics.CANSparkBase.IdleMode;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants; 

public class auto extends TimedRobot {

    public static int numCheck = 0;

    public static Timer m_timer = new Timer();
    public static Timer m_timer2 = new Timer();
    
    public static void autoDrive() {
        Robot.drive_left_encoder.setPosition(0);
        Robot.drive_right_encoder.setPosition(0);
        
        double targetDistance = 1.0;
        double targetEncoder = autoEncoders(targetDistance);
        moveForward(targetEncoder, 0.4);
    }

    public static double autoEncoders(double distanceInFeet) {
        double circumference = 18.85;
        double distancePerEncoder = circumference / (Robot.drive_left_encoder.getCountsPerRevolution() * Constants.drive.measure_gearBoxRatio);
        return distanceInFeet / distancePerEncoder;
    }

    public static void moveForward(double targetEncoder, double speed) {
        m_timer.reset();
        m_timer.start();

        double leftReady = Robot.drive_left_encoder.getPosition();
        double rightReady = Robot.drive_right_encoder.getPosition();

        while ((Robot.drive_right_encoder.getPosition() - rightReady) < targetEncoder && 
               (Robot.drive_left_encoder.getPosition() - leftReady) < targetEncoder) {
            Robot.drive_difDrive.arcadeDrive(speed, 0);
        }
        Robot.drive_difDrive.arcadeDrive(0, 0);
        Robot.drive_leftSlave.setIdleMode(IdleMode.kBrake);
        Robot.drive_rightSlave.setIdleMode(IdleMode.kBrake);
    }

    public static Timer deployTimer = new Timer();
    public static boolean hasDeployed = false;







    public static void intakeAuto() {  
    // deploy = true but it = retract
    // retract = true but it = deploy
    boolean intakeActive = false;  
    
    if (Robot.intake_deploy_DIO.get()) {
    
        Robot.intake_position_motor.set(0.1);  
    } else if (Robot.intake_retract_DIO.get()) {
        hasDeployed = true;
        if (hasDeployed == true) {
        Robot.intake_position_motor.set(0.7);  
         
            
        
        deployTimer.reset();
        deployTimer.start();
        } else if (deployTimer.get() >= 1.0 && !intakeActive) {
    
        Robot.intake_feeder_motor.set(1.0); 
        intakeActive = true;  
        
        Timer intakeTimer = new Timer();
        intakeTimer.reset();
        intakeTimer.start();
        

        if (intakeTimer.get() >= 3.0) {
            Robot.intake_feeder_motor.set(0.0);  
            intakeActive = false; 
            
            Robot.intake_position_motor.set(-0.1);
        }
        }
    } else {
        Robot.intake_position_motor.set(0.1);  
    }


    if (Robot.intake_retract_DIO.get()) {
     Robot.intake_position_motor.set(0.0); 
     hasDeployed = true;
    }
}


    public static void intakeAutoBrake(){
    if ((Robot.intake_deploy_DIO.get() || Robot.intake_retract_DIO.get()) && numCheck == 1) {
                Robot.intake_position_motor.set(0);

        }

    }
    public static void timerCheck() {
        if (m_timer.get() > 5.0) {
            Robot.drive_leftSlave.setIdleMode(IdleMode.kCoast);
            Robot.drive_rightSlave.setIdleMode(IdleMode.kCoast);
            shooterAuto();
            m_timer2.start();   
        }   
    }

    public static void shooterAuto() {
        if (m_timer2.get() < 3.0) {
            Robot.shooter_left_motor.set(5);
            Robot.shooter_right_motor.set(5);
        } else {
            Robot.shooter_left_motor.set(0);
            Robot.shooter_right_motor.set(0);
        }
    }
}
