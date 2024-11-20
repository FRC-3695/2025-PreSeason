package frc.robot;

import com.pathplanner.lib.util.PIDConstants;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import swervelib.math.Matter;

public class Constants {
    public static final class robot {
        public static final double ROBOT_MASS = (148 - 20.3) * 0.453592;                                                                    // 32lbs * kg per pound
        public static final Matter CHASSIS    = new Matter(new Translation3d(0, 0, Units.inchesToMeters(8)), ROBOT_MASS);        // Frame Size
    }
    public static final class swerve {
        public static final double LOOP_TIME  = 0.13;                                                                                       //s, 20ms + 110ms sprk max velocity lag
        public static final double MAX_SPEED  = Units.feetToMeters(14.5);                                                              //ft,  Maximum speed of the robot in meters per second, used to limit acceleration.
        public static final PIDConstants AUTO_Translation_PID = new PIDConstants(0.7, 0, 0);                                       //
        public static final PIDConstants AUTO_Angle_PID       = new PIDConstants(0.4, 0, 0.01);                                    //
        public static final double WHEEL_LOCK_TIME = 10;                                                                                    //s, Time till wheels return to breaking mode and stop PID's after x seconds
    }
    public static final class operatorDriver {
        public static final int    port             = 0;                                                                                    // USB position assignment for DriverStation
        public static final double LEFT_X_DEADBAND  = 0.1;                                                                                  //%, Left Stick Horizontal Deadband Percentage of Travel from center
        public static final double LEFT_Y_DEADBAND  = 0.1;                                                                                  //%, Left Stick Vertical Deadband Percentage of Travel from center
        public static final double RIGHT_X_DEADBAND = 0.1;                                                                                  //%, Right Stick Horizontal Deadband Percentage of Travel from center
        public static final double RIGHT_Y_DEADBAND = 0.1;                                                                                  //%, Right Stick Vertical Deadband Percentage of Travel from center
    }
    public static final class operatorManip {
        public static final int    port             = 1;                                                                                    // USB position assignment for DriverStation
        public static final double LEFT_X_DEADBAND  = 0.1;                                                                                  //%, Left Stick Horizontal Deadband Percentage of Travel from center
        public static final double LEFT_Y_DEADBAND  = 0.1;                                                                                  //%, Left Stick Vertical Deadband Percentage of Travel from center
        public static final double RIGHT_X_DEADBAND = 0.1;                                                                                  //%, Right Stick Horizontal Deadband Percentage of Travel from center
        public static final double RIGHT_Y_DEADBAND = 0.1;                                                                                  //%, Right Stick Vertical Deadband Percentage of Travel from center
    }
}
