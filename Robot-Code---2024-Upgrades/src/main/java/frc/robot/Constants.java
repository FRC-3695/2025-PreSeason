package frc.robot;

public final class Constants {
    public static class IDs {                    // View is taken from rear of robot for sides
        // Drive Train Motor Controllers
        public static final int drive_leftFront_motor           = 1;  // CAN ID
        public static final int drive_leftFront_powerBus        = 12;  // PowerHub Port
        public static final int drive_leftRear_motor            = 2;  // CAN ID
        public static final int drive_leftRear_powerBus         = 13;  // PowerHub Port
        public static final int drive_rightFront_motor          = 3;  // CAN ID
        public static final int drive_rightFront_powerBus       = 7;  // PowerHub Port
        public static final int drive_rightRear_motor           = 4;  // CAN ID
        public static final int drive_rightRear_powerBus        = 6;  // PowerHub Port
        // Lifter Motor Controllers
        public static final int lifter_left_motor               = 5;  // CAN ID
        public static final int lifter_left_powerBus            = 11;  // PowerHub Port
        public static final int lifter_right_motor              = 6;  // CAN ID
        public static final int lifter_right_powerBus           = 8;  // PowerHub Port
        public static final int lifter_left_sensor              = 0;  // Digital IO
        public static final int lifter_right_sensor             = 1;  // Digital IO
        // Manipulator Motor Controllers
        public static final int manipulator_feed_motor          = 7;  // CAN ID
        public static final int manipulator_feed_powerBus       = 14;  // PowerHub Port
        public static final int manipulator_posi_motor          = 8;  // CAN ID
        public static final int manipulator_posi_powerBus       = 15;  // PowerHub Port
        public static final int manupilator_sensor_retract      = 2;  // Digital IO
        public static final int manipulator_sensor_deploy       = 3;  // Digital IO
        public static final int manipulator_sensor_intake_L     = 4;  // Digital IO
        public static final int manipulator_sensor_intake_R     = 5;  // Digital IO
        // Shooter Motor Controllers
        public static final int shooter_left_motor              = 9;  // CAN ID
        public static final int shooter_left_powerBus           = 10;  // PowerHub Port
        public static final int shooter_right_motor             = 10; // CAN ID
        public static final int shooter_right_powerBus          = 9;  // PowerHub Port
        // Devices
        public static final int power_hub                       = 11; // CAN ID
        public static final int device_rio                      = 20; // PowerHub Port
        public static final int device_radio                    = 21; // PowerHub Port
    }
    public static class drive {
        public static final double  tuning_speedMax             = 5;
        public static final double  slew_drv                    = 1;    // Slew-Rate Limit for Acceletation
        public static final double  slew_turn                   = 0.5;    // Slew-Rate Limit for Turning
        public static       boolean rev_DT                      = false;  // To be used in future to offer drive reversing to driver
        public static final boolean rev_left                    = true;   // Reverses left drivetrain
        public static final boolean rev_right                   = false;  // Reverses right drivetrain
        public static final double  measure_wheel               = 6.00;   // Wheel Diameter
        public static final double  measure_gearBoxRatio        = 8.45;
    }
    public static class operator {
        public static final int controller_xBox_driver          = 0;            // DS USB ID
        public static final double tuning_driver_deadband       = 0.07;         // DeadBand for Driver Joysticks and Triggers
        public static final int controller_xBox_manip           = 1;            // DS USB ID
        public static final double tuning_manip_deadband        = 0.10;         // DeadBand for Manip Joysticks and Triggers
    }
    public static class shooter {
        public static final double slew_spinUp                  = 0.75;         // Forced Graceful SpinUp to prevent low current events on robot
        public static       double tuning_RPM                   = 4300;         // Motor RPM
        public static       double tuning_P                     = 0.75;         // Proportional Variable
        public static       double tuning_I                     = 0.000;        // Intergral Variable
        public static       double tuning_D                     = 0.03;         // Derivative Variable
        public static       double tuning_Iz                    = 0.000;        // Intergral Variable
        public static       double tuning_FF                    = 0.000;        // Feed Forward Variable
        public static final boolean rev_left                    = false;        // Reverse Left motor
        public static final boolean rev_right                   = true;         // Reverse Right motor
    }
    public static class intake {
        public static final double heave_speed                  = 0.40;         // Speed of intake run to self cycle a system recovery for a jammed disc
        public static final double puke_speed                   = 0.90;         // Speed of intake run to eject disc
        public static final double feed_speed                   = 0.50;         // Speed of intake run to injest disc
        public static final double feed_rotations               = 4.50;         // Count of rotations to injest disk
        public static final double rotation_deployment          = 0.000;        // Amount of rotation till intake is completly deployed
        public static final double rotation_calibration         = 0.00;         // Rotations to lift climber before retracting it to calibrate
        public static       double rotation_cal_speed           = 0.00;         // Speed at which calibration occurs
        public static       double posi_tuning_P                = 0.000;        // Proportional Variable
        public static       double posi_tuning_I                = 0.000;        // Intergral Variable
        public static       double posi_tuning_D                = 0.000;        // Derivative Variable
        public static       double posi_tuning_Iz               = 0.000;        // Intergral Variable
        public static       double posi_tuning_FF               = 0.000;        // Feed Forward Variable
        public static       double posi_tuning_speedMin         =-0.50;         // Max Retract Motor Speed
        public static       double posi_tuning_speedMax         = 0.80;         // Max Deploy Motor Speed
        public static       double feed_tuning_P                = 0.000;        // Proportional Variable
        public static       double feed_tuning_I                = 0.000;        // Intergral Variable
        public static       double feed_tuning_D                = 0.000;        // Derivative Variable
        public static       double feed_tuning_Iz               = 0.000;        // Intergral Variable
        public static       double feed_tuning_FF               = 0.000;        // Feed Forward Variable
        public static       double feed_tuning_speedMin         = 0.60;         // Max Injest Motor Speed
        public static       double feed_tuning_speedMax         = 0.80;         // Max Eject Motor Speed
        public static final double position_floor               = 0.000;        //
        public static final double position_feeder              = 0.000;        //
        public static final double position_shooting            = 0.000;        //
        public static final double rotation_retract_unCalSpd    = 0.00;         // Speed to run intake position motor when out of calibration to bring it back into calibration
        public static final double intake_tuning_clamp          = 2;            // Clamps reactivity of intake movement
    }
    public static class lifter {
        public static final boolean rev_left                    = false;
        public static final boolean rev_right                   = true;
        public static       double tuning_P                     = 0.280;        // Proportional Variable
        public static       double tuning_I                     = 0.000;        // Intergral Variable
        public static       double tuning_D                     = 0.034;        // Derivative Variable
        public static       double tuning_Iz                    = 0.000;        // Intergral Z Variable
        public static       double tuning_FF                    = 0.000;        // Feed Forward Variable
        public static       double tuning_speedMin              =-0.30;         // Max Inward Motor Speed
        public static       double tuning_speedMax              = 0.50;         // Max Outward Motor Speed
        public static final double rotation_calibration         = 5.00;         // Rotations to lift climber before retracting it to calibrate
        public static final double rotation_cal_speed           = 0.10;         // Speed at which calibration occurs
        public static final double rotation_cal_maxWatt         = 120;          // Max safe current during calibration
        public static final double rotation_climb               = 65.00;        // How many rotations required to fully deply lifter
        public static final double rotation_OV_speed            = 5.00;         // Speed to run lifter at on over ride

    }
    public static class vision {
        public static final double[][] targeting_april_alignment      = {  // Targeting Alignment Data for AprilTag's
        //       |ID#|   | X  |+/-| Y  |+/-| Z  |+/-| A  |+/-| R  |+/-| P  |+/-| Ya |+/-|
                /* 1 */  {  10,  1,  15,  3,  25,  5,  15, 25,  65, 75,  86, 12,  23, 86},  // Blue - Source    (Right)
                /* 2 */  {  10,  1,  15,  3,  25,  5,  15, 25,  65, 75,  86, 12,  23, 86},  // Blue - Source    (Left)
                /* 3 */  {  10,  1,  15,  3,  25,  5,  15, 25,  65, 75,  86, 12,  23, 86},  // Red -  Speaker   (Right)
                /* 4 */  {  10,  1,  15,  3,  25,  5,  15, 25,  65, 75,  86, 12,  23, 86},  // Red -  Speaker   (Center)
                /* 5 */  {  10,  1,  15,  3,  25,  5,  15, 25,  65, 75,  86, 12,  23, 86},  // Red -  Amplifier
                /* 6 */  {  10,  1,  15,  3,  25,  5,  15, 25,  65, 75,  86, 12,  23, 86},  // Blue - Amplifier
                /* 7 */  {  10,  1,  15,  3,  25,  5,  15, 25,  65, 75,  86, 12,  23, 86},  // Blue - Speaker   (Center)
                /* 8 */  {  10,  1,  15,  3,  25,  5,  15, 25,  65, 75,  86, 12,  23, 86},  // Blue - Speaker   (Left)
                /* 9 */  {  10,  1,  15,  3,  25,  5,  15, 25,  65, 75,  86, 12,  23, 86},  // Red -  Source    (Right)
                /* 10 */ {  10,  1,  15,  3,  25,  5,  15, 25,  65, 75,  86, 12,  23, 86},  // Red -  Source    (Left)
                /* 11 */ {  10,  1,  15,  3,  25,  5,  15, 25,  65, 75,  86, 12,  23, 86},  // Red -  Stage     (Left)
                /* 12 */ {  10,  1,  15,  3,  25,  5,  15, 25,  65, 75,  86, 12,  23, 86},  // Red -  Stage     (Right)
                /* 13 */ {  10,  1,  15,  3,  25,  5,  15, 25,  65, 75,  86, 12,  23, 86},  // Red -  Stage     (Center)
                /* 14 */ {  10,  1,  15,  3,  25,  5,  15, 25,  65, 75,  86, 12,  23, 86},  // Blue - Stage     (Center)
                /* 15 */ {  10,  1,  15,  3,  25,  5,  15, 25,  65, 75,  86, 12,  23, 86},  // Blue - Stage     (Left)
                /* 16 */ {  10,  1,  15,  3,  25,  5,  15, 25,  65, 75,  86, 12,  23, 86},  // Blue - Stage     (Right)
        };
        public static final String[] targeting_april_name           = {  // Name of Target Identified through AprilTag ID
        //   |ID#|   | Target Name
            /* 01 */ "B - Source (Right)",
            /* 02 */ "B - Source (Left)",
            /* 03 */ "R - Speaker (Right)",
            /* 04 */ "R - Speaker (Center)",
            /* 05 */ "R - Amplifier",
            /* 06 */ "B - Amplifier",
            /* 07 */ "B - Speaker (Center)",
            /* 08 */ "B - Speaker (Left)",
            /* 09 */ "R - Source (Right)",
            /* 10 */ "R - Source (Left)",
            /* 11 */ "R - Stage (Left)",
            /* 12 */ "R - Stage (Right)",
            /* 13 */ "R - Stage (Center)",
            /* 14 */ "B - Stage (Center)",
            /* 15 */ "B - Stage (Left)",
            /* 16 */ "B - Stage (Right)",
        };
        public static final String frontCamera                  = "limelight";       // Front of Robot Limelight3
        public static final String rearCamera                   = "";       // Front of Robot Limelight2
    }   
    public static class Universals {
        public static final double PI                           = 3.142;        // PI
    }
}