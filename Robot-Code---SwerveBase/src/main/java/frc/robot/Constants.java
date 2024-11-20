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
        public static final double          LOOP_TIME               = 0.13;                                                                 //s, 20ms + 110ms sprk max velocity lag
        public static final double          MAX_SPEED               = Units.feetToMeters(14.5);                                        //ft,  Maximum speed of the robot in feet per second conv. to meters per second, used to limit acceleration.
        public static final PIDConstants    AUTO_Translation_PID    = new PIDConstants(0.7, 0, 0);                                 //
        public static final PIDConstants    AUTO_Angle_PID          = new PIDConstants(0.4, 0, 0.01);                              //
        public static final double          WHEEL_LOCK_TIME         = 10;                                                                   //s, Time till wheels return to breaking mode and stop PID's after x seconds
        public static final double          wheelDiameter           = 4;                                                                    //in, Diameter of wheels in the swerve modules
        public static final double          gearRatio_Stearing      = 12.8;                                                                 // Ratio's upper number for swerve modules steering
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
    public static final class vision {

    }
    public static final class power {
        public static final int    port_core_RIO        = 20;                                                                               // Port on the Power Hub for the RIO Controller
        public static final int   fused_core_RIO        = 5;                                                                                //amps, Fused for
        public static final int    port_core_Radio      = 21;                                                                               // Port on the Power Hub for the Radio Module (Vivid Hosting or OpenMesh)
        public static final int   fused_core_Radio      = 15;                                                                               //amps, Fused for
        public static final int    port_core_LL_VRM     = 22;                                                                               // Port on the Power Hub for the Voltage Regulation Module dedicated for LimeLight Cameras
        public static final int   fused_core_LL_VRM     = 10;                                                                               //amps, Fused for
        public static final int    port_sw_FL_Stearing  = 0;                                                                                // Port on the Power Hub for the Front Left Swerve Stearing Motor
        public static final int   fused_sw_FL_Stearing  = 30;                                                                               //amps, Fused for
        public static final int    port_sw_FL_Drive     = 1;                                                                                // Port on the Power Hub for the Front Left Swerve Drive Motor
        public static final int   fused_sw_FL_Drive     = 40;                                                                               //amps, Fused for
        public static final int    port_sw_FR_Stearing  = 19;                                                                               // Port on the Power Hub for the Front Right Swerve Stearing Motor
        public static final int   fused_sw_FR_Stearing  = 30;                                                                               //amps, Fused for
        public static final int    port_sw_FR_Drive     = 18;                                                                               // Port on the Power Hub for the Front Right Swerve Drive Motor
        public static final int   fused_sw_FR_Drive     = 40;                                                                               //amps, Fused for
        public static final int    port_sw_F_Encoders   = 4;                                                                                // Port on the Power Hub for the Front two Absolute Encoders on the Swerve Modules
        public static final int   fused_sw_F_Encoders   = 10;                                                                               //amps, Fused for
        public static final int    port_sw_BL_Stearing  = 2;                                                                                // Port on the Power Hub for the Back Left Swerve Stearing Motor
        public static final int   fused_sw_BL_Stearing  = 30;                                                                               //amps, Fused for
        public static final int    port_sw_BL_Drive     = 3;                                                                                // Port on the Power Hub for the Back Left Swerve Drive Motor
        public static final int   fused_sw_BL_Drive     = 40;                                                                               //amps, Fused for
        public static final int    port_sw_BR_Stearing  = 17;                                                                               // Port on the Power Hub for the Back Right Swerve Stearing Motor
        public static final int   fused_sw_BR_Stearing  = 30;                                                                               //amps, Fused for
        public static final int    port_sw_BR_Drive     = 16;                                                                               // Port on the Power Hub for the Back Right Swerve Drive Motor
        public static final int   fused_sw_BR_Drive     = 40;                                                                               //amps, Fused for
        public static final int    port_sw_B_Encoders   = 15;                                                                               // Port on the Power Hub for the Back two Absolute Encoders on the Swerve Modules
        public static final int   fused_sw_B_Encoders   = 10;                                                                               //amps, Fused for
        public static final int    port_sw_IMU          = 23;                                                                               // Port on the Power Hub for the CTRE Pigeon2 IMU
        public static final int   fused_sw_IMU          = 5;                                                                                //amps, Fused for
    }
    public static final class CANnet {
        public static final int         core_RIO        = 0;
        public static final int         core_Radio      = 1;
        public static final int         core_PowerHub   = 2;
    }
}
