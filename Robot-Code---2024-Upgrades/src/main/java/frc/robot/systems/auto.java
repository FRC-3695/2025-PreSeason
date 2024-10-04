package frc.robot.systems;
import frc.robot.Robot;                                 // Core Robot

import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;                             // Cross Robot Varriables Centralized
import frc.robot.utils;                                 // utils to simplify FRC Programming life

public class auto extends TimedRobot {


        public static Timer m_timer = new Timer();

        

     

    

    // function to set the distance I want the robot to move forward (in feet) aswell as set the target encoder so that the robot knows when to stop. Also to set speed.
   public static void autoDrive() {
    
    double targetDistance = 1.0;
    double targetEncoder = autoEncoders(targetDistance);
    moveForward(targetEncoder, 0.4);
    
   }

    // 18.85 is the circumference of the number set as the wheel diameter in constants. There's prabably a more dynamic way to do this, but I don't know how to code the math for it. For now this works.
   // Converts feet to encoder counts and returns it. 
   public static double autoEncoders(double distanceInFeet) {
    double circumferance = 18.85;
    double distancePerEncoder = circumferance / (Robot.drive_left_encoder.getCountsPerRevolution() * Constants.drive.measure_gearBoxRatio);
    return distanceInFeet / distancePerEncoder;
   }

   //moves the robot forward. Checks if the current encoder count is less than the target encoder count, then sets the speed to 0 when it reaches the target encoder count.
   public static void moveForward(double targetEncoder, double speed) {
         m_timer.reset();
                
    
       
        double leftReady = Robot.drive_left_encoder.getPosition();
        double rightReady = Robot.drive_right_encoder.getPosition();

        while ((Robot.drive_left_encoder.getPosition() - leftReady) < targetEncoder && (Robot.drive_right_encoder.getPosition() - rightReady) < targetEncoder) {
    // z rotation is set to 0 so it'll go in a straight line 
                Robot.drive_difDrive.arcadeDrive(speed,0);
        }
    // stops the robot.
        Robot.drive_difDrive.arcadeDrive(0,0);
        m_timer.start();

    
        Robot.drive_leftSlave.setIdleMode(IdleMode.kBrake);
        Robot.drive_rightSlave.setIdleMode(IdleMode.kBrake);

        

   }


   
public static void timerCheck() {


    if (m_timer.get() > 5.0) {
                            Robot.drive_leftSlave.setIdleMode(IdleMode.kCoast);
                            Robot.drive_rightSlave.setIdleMode(IdleMode.kCoast);
                            shooterAuto();
                        
                    }
    
}

public static void shooterAuto() {
    if (m_timer.get() > 7.0) {

        Robot.shooter_left_motor.set(0);
        Robot.shooter_right_motor.set(0);
                              
                                
     }

     Robot.shooter_left_motor.set(2);
     Robot.shooter_right_motor.set(2);

}
   



  

    

    
    private static void configTravel() {
        double circumferance = Constants.Universals.PI * Constants.drive.measure_wheel;
        double encoderStepDistant = circumferance / (Robot.drive_left_encoder.getCountsPerRevolution()*Constants.drive.measure_gearBoxRatio);
    }

    
}
