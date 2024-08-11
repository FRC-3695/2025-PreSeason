package frc.robot;
import java.text.DecimalFormat;

//WPILib Libraries
import edu.wpi.first.hal.HALUtil;

public class utils {
    private utils() {}
    // *************************   Math   *************************
    public static double map(double x, double in_min, double in_max, double out_min, double out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
    // ************************   Logging   ***********************
    static DecimalFormat ft = new DecimalFormat("###0000.000"); 
    public static void Logging(int level, String event) {
        System.out.print("*** ");
        double time = HALUtil.getFPGATime();
        time = time/1000000;
        System.out.print(ft.format(time));
        switch (level) {
            case 0:
                System.out.print(" - Debug - ");
                break;
            case 1:
                System.out.print(" - Info - ");
                break;
            case 2:
                System.out.print(" - Error - ");
                break;
            case 3:
                System.out.print(" - ! Alert ! - ");
                break;
            case 4:
                System.out.print(" - (/) Warning (/) - ");
                break;
            case 5:
                System.out.print(" - </> Critical </> - ");
                break;
            default:

                break;
        }
        System.out.println(event);

    }
}
