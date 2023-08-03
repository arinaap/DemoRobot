package org.firstinspires.ftc.teamcode.DemoRobot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class DemoHardware {

    public DcMotor lf;
    public DcMotor lb;
    public DcMotor rf;
    public DcMotor rb;

    public DcMotor demoMotor;

    public Servo demoServo;

    public BNO055IMU gyro;

    public RevColorSensorV3 colorSensor;

    public static double maxSpeed = 1;

    private static DemoHardware myInstance = null;
    public static DemoHardware getInstance(){
        if(myInstance == null){
            myInstance = new DemoHardware();
        }

        return myInstance;
    }

    public void init(HardwareMap hwMap){
        try{
            lf = hwMap.get(DcMotor.class, "lf");
            lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            lf.setPower(0);
        } catch (Exception p_exception){
            lf = null;
        } try{

            lb = hwMap.get(DcMotor.class, "lf");
            lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            lb.setPower(0);

        } catch (Exception p_exception){
            lb = null;

        } try{

            rf = hwMap.get(DcMotor.class, "lf");
            rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rf.setPower(0);

        } catch (Exception p_exception){
            rf = null;

        } try{
            rb = hwMap.get(DcMotor.class, "lf");
            rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rb.setPower(0);

        } catch (Exception p_exception){
            rb = null;

        } try{
            demoMotor = hwMap.get(DcMotor.class, "demoMotor");
            demoMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            demoMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            demoMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            demoMotor.setPower(0);

        } catch (Exception p_exception) {
            rb = null;

        }try{
            gyro = hwMap.get(BNO055IMU.class, "gyro");
            BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
            parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
            parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            parameters.loggingEnabled = true;
            parameters.loggingTag = "gyro";
            parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
            gyro.initialize(parameters);

        } catch(Exception p_exception){
            gyro = null;

        } try{
            demoServo = hwMap.get(Servo.class, "demoServo");

        } catch(Exception p_exception){
            demoServo = null;

        } try{
            colorSensor = hwMap.get(RevColorSensorV3.class, "color");

        } catch(Exception p_exception){
            colorSensor = null;

        }
    }

    public void setPower(double fl, double bl, double fr, double br){
        if(lf != null){
            lf.setPower(Range.clip(fl, -maxSpeed, maxSpeed));
        }
        if(lb != null){
            lb.setPower(Range.clip(bl, -maxSpeed, maxSpeed));
        }
        if(rf != null){
            rf.setPower(Range.clip(fr, -maxSpeed, maxSpeed));
        }
        if(rb != null){
            rb.setPower(Range.clip(br, -maxSpeed, maxSpeed));
        }
    }
}
