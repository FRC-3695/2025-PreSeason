package frc.robot.systems;
import frc.robot.Robot;                                 // Core Robot
import frc.robot.Constants;                             // Cross Robot Varriables Centralized
import frc.robot.utils;                                 // utils to simplify FRC Programming life

public class auto {
    private auto() {
    }
    public static void driveLine() {

    }
    private static void configTravel() {
        double circumferance = Constants.Universals.PI * Constants.drive.measure_wheel;
        double encoderStepDistant = circumferance / (Robot.drive_left_encoder.getCountsPerRevolution()*Constants.drive.measure_gearBoxRatio);
    }
}
