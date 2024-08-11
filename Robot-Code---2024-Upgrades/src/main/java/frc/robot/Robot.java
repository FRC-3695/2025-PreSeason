package frc.robot;
// Constants libraries
//- import frc.robot.Constants;                             // Cross Robot Varriables Centralized
//- import frc.robot.BuildConstants;                        // Generated on Each Build for Tracking Purposes
// Operations for robot
import frc.robot.systems.drive;
import frc.robot.systems.lifter;
import frc.robot.systems.manipulator;
import frc.robot.systems.testingAnHealth;
import frc.robot.systems.power;
import frc.robot.systems.auto;
//import frc.robot.utils;
// Lib for SparkMax Motor Controllers
import com.revrobotics.CANSparkMax;                         // SparkMAX CAN Control Map and Watchdog
import com.revrobotics.CANSparkLowLevel.MotorType;          // MotorType Definitions
import com.revrobotics.RelativeEncoder;                     // REVLib Relative Encoder
import com.revrobotics.SparkPIDController;                  // REVLib SparkPID Control
import com.revrobotics.SparkRelativeEncoder;                // REVLib RelativeEncoder Reporting from device attached to SparkDevice
// Adds Smart Dashboard Capabilities & Autonomous chooser
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// WPILib  Libraries
import edu.wpi.first.wpilibj.TimedRobot;                    // Support for Timed code layout
import edu.wpi.first.wpilibj.drive.DifferentialDrive;       // Introducing Prebuilt drivecontroller
import edu.wpi.first.wpilibj.XboxController;                // Support for Xbox Controller
import edu.wpi.first.wpilibj.DigitalInput;                  // RoboRio Digital Input Control
import edu.wpi.first.wpilibj.PowerDistribution;             // PowerHub
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;  // PowerHub Type
import edu.wpi.first.wpilibj2.command.CommandScheduler;     // Gives support for scheduling and descheduling tasks in Scheduler

public class Robot extends TimedRobot {
  // Creation of Autonomous functions IDs and Varriables
  private static final String dash_auto_1 = "SpeakerShot";
  private static final String dash_auto_2 = "AmpDump";
  private static final String dash_auto_3 = "SpeakerAnScram";
  private static final String dash_auto_4 = "AmpDumpAnScram";
  private static final String dash_auto_5 = "PathPlanner";
  private String dash_autoSelected;
  private final SendableChooser<String> dash_autoOptions = new SendableChooser<>();

  // Defining Motors
  public static final CANSparkMax drive_leftMaster = 
    new CANSparkMax(Constants.IDs.drive_leftFront_motor, MotorType.kBrushless);
  public static final CANSparkMax drive_leftSlave = 
    new CANSparkMax(Constants.IDs.drive_leftRear_motor, MotorType.kBrushless);
  public static final CANSparkMax drive_rightMaster = 
    new CANSparkMax(Constants.IDs.drive_rightFront_motor, MotorType.kBrushless);
  public static final CANSparkMax drive_rightSlave = 
    new CANSparkMax(Constants.IDs.drive_rightRear_motor, MotorType.kBrushless);
  public static final CANSparkMax lifter_left_motor =
    new CANSparkMax(Constants.IDs.lifter_left_motor, MotorType.kBrushless);
  public static final CANSparkMax lifter_right_motor =
    new CANSparkMax(Constants.IDs.lifter_right_motor, MotorType.kBrushless);
  public static final CANSparkMax intake_position_motor =
    new CANSparkMax(Constants.IDs.manipulator_posi_motor, MotorType.kBrushless);
  public static final CANSparkMax intake_feeder_motor =
    new CANSparkMax(Constants.IDs.manipulator_feed_motor, MotorType.kBrushed);
  public static final CANSparkMax shooter_left_motor =
    new CANSparkMax(Constants.IDs.shooter_left_motor, MotorType.kBrushless);
  public static final CANSparkMax shooter_right_motor =
    new CANSparkMax(Constants.IDs.shooter_right_motor, MotorType.kBrushless);
  // Defining Powerhub
  public static PowerDistribution power_hub =
    new PowerDistribution(Constants.IDs.power_hub, ModuleType.kRev);
  // Defining Encoders for Functions
  public static RelativeEncoder drive_left_encoder =
    drive_leftMaster.getEncoder();
  public static RelativeEncoder drive_right_encoder =
    drive_rightMaster.getEncoder();
  public static RelativeEncoder lifter_left_encoder =
    lifter_left_motor.getEncoder();
  public static RelativeEncoder lifter_right_encoder =
    lifter_right_motor.getEncoder();
  public static RelativeEncoder intake_position_encoder =
    intake_position_motor.getEncoder();
  public static RelativeEncoder intake_feeder_encoder = 
    intake_feeder_motor.getEncoder(SparkRelativeEncoder.Type.kQuadrature, 1024);
  public static RelativeEncoder shooter_left_encoder =
    shooter_left_motor.getEncoder();
  public static RelativeEncoder shooter_right_encoder =
    shooter_right_motor.getEncoder();
  // Defining Motor PIDs
  public static final SparkPIDController lifter_left_PID =
    lifter_left_motor.getPIDController();
  public static final SparkPIDController lifter_right_PID =
    lifter_right_motor.getPIDController();
  public static final SparkPIDController shooter_left_PID =
    shooter_left_motor.getPIDController();
  public static final SparkPIDController shooter_right_PID =
    shooter_right_motor.getPIDController();
  public static final SparkPIDController intake_position_PID =
    intake_position_motor.getPIDController();
  public static final SparkPIDController intake_feeder_PID =
    intake_feeder_motor.getPIDController();
  // Defining Digital IO
  public static final DigitalInput lifter_left_DIO =
    new DigitalInput(Constants.IDs.lifter_left_sensor);
  public static final DigitalInput lifter_right_DIO =
    new DigitalInput(Constants.IDs.lifter_right_sensor);
  public static final DigitalInput intake_retract_DIO =
    new DigitalInput(Constants.IDs.manupilator_sensor_retract);
  public static final DigitalInput intake_deploy_DIO =
    new DigitalInput(Constants.IDs.manipulator_sensor_deploy);
  // Defining Driver Controller
  public static final XboxController drivestation_driver =
    new XboxController(Constants.operator.controller_xBox_driver);
  public static final XboxController drivestation_operator =
    new XboxController(Constants.operator.controller_xBox_manip);
  // Drive Requirements
  public static final DifferentialDrive drive_difDrive =
    new DifferentialDrive(Robot.drive_leftMaster::set, Robot.drive_rightMaster::set);
  @Override
  public void robotInit() {
    // Prints codes GIT commit version, date, and build date
    System.out.println("|");
    System.out.println("****************************************************");
    System.out.println("Build Branch: "+ BuildConstants.MAVEN_NAME);
    System.out.println("GIT Revision: "+ BuildConstants.GIT_REVISION);
    System.out.println("Built on: "+ BuildConstants.BUILD_DATE + " @ " + BuildConstants.BUILD_UNIX_TIME);
    System.out.println("GIT_SHA: "+ BuildConstants.GIT_SHA);
    if(BuildConstants.DIRTY != 0) {System.out.println("|\n****************************************************\n****************************************************\n    ********** Fruit of the Poisonous Tree *************");}// Warning of uncommited changes in deployed build
    System.out.println("****************************************************");
    System.out.println("|");
    // Loads Choices into SmartDashboard Autonomous Chooser
    dash_autoOptions.setDefaultOption("Speaker Shoot and Wait", dash_auto_1);
    dash_autoOptions.addOption("Amp Dump and Wait", dash_auto_2);
    dash_autoOptions.addOption("Speaker Shoot and Move Over", dash_auto_3);
    dash_autoOptions.addOption("Amp Dump and Move Over", dash_auto_4);
    dash_autoOptions.addOption("Path Planner", dash_auto_5);
    SmartDashboard.putData("Auto choices", dash_autoOptions);
    // -----------------------------------------------------------
    power.setup();
    drive.startup();
    manipulator.startup();
  }
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    power.controlPeriodic();
  }
  @Override
  public void disabledInit() {}
  @Override
  public void disabledPeriodic() {}
  @Override
  public void disabledExit() {}
  @Override
  public void autonomousInit() {
    dash_autoSelected = dash_autoOptions.getSelected(); //Gets Selected Auto Command from DriverStation
  }
  @Override
  public void autonomousPeriodic() {
    switch(dash_autoSelected){
      case dash_auto_1:
        // Shoot into Speaker
        break;
      case dash_auto_2:
        // Dump note into amp
        break;
      case dash_auto_3:
        // Shoot into Speaker and Move out of the way
        break;
      case dash_auto_4:
        // Dump note into amp and Move out of the way
        break;
      case dash_auto_5:
        // Dump note into amp and Move out of the way
        break;
      default:
        // Action Taken with no action or nothing matches
        break;
    }
  }
  @Override
  public void autonomousExit() {}
  @Override
  public void teleopInit() {
    lifter.setup();
    manipulator.motorSetup();
  }
  @Override
  public void teleopPeriodic() {
    drive.controlPeriodic();
    lifter.controlPeriodic();
    manipulator.controlPeriodic();
  }
  @Override
  public void teleopExit() {}
  @Override
  public void testInit() {
    testingAnHealth.setup();
  }
  @Override
  public void testPeriodic() {
    testingAnHealth.periodic();
  }
  @Override
  public void testExit() {}
  @Override
  public void simulationInit() {
    drive.startup();
  }
  @Override
  public void simulationPeriodic() {
    drive.controlPeriodic();
  }
}
