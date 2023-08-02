package org.firstinspires.ftc.teamcode.DemoRobot;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

@Autonomous (name = "DemoAuto")
public class DemoAuto extends LinearOpMode {

    //timed based
    private ElapsedTime runtime = new ElapsedTime();

    DemoHardware robot = DemoHardware.getInstance();

    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashboardTelemetry = dashboard.getTelemetry();

    OpenCvCamera webCam;

    private int position = 1;

    private Pipeline detector;
    //
    public void runOpMode(){

        robot.init(hardwareMap);
        telemetry.addData("Status", "Hello, drivers!");
        //if using camera to keep updating and reading (like color sensor)
        telemetry.update();

        int cameraMonitorViewID = hardwareMap.appContext.getResources()
                .getIdentifier("cameraMonitorViewID", "id",
                        hardwareMap.appContext.getPackageName());
        detector = new Pipeline();
        webCam = OpenCvCameraFactory.getInstance().createWebcam
                (hardwareMap.get(WebcamName.class, "demoCam"),
                        cameraMonitorViewID);

        webCam.openCameraDevice();

        FtcDashboard.getInstance().startCameraStream(webCam, 0);
        webCam.startStreaming(640, 480, OpenCvCameraRotation.UPSIDE_DOWN);

        webCam.setPipeline(detector);

        while (!isStarted() && !isStopRequested()){
            position = detector.position;
            telemetry.addData("Position", position);
            telemetry.addData("totalA", detector.totalA);
            telemetry.update();

            dashboardTelemetry.addData("position", position);
            dashboardTelemetry.addData("totalA", detector.totalA);
            dashboardTelemetry.update();
        }

        waitForStart();

        move(62, 0.5);

        runtime.reset();
        while(runtime.seconds() < 5){
            robot.setPower(1, 1, 1, 1);
        }
        robot.setPower(0, 0, 0, 0);

    }

    public void move(double distanceMoving, double speedMoving){

        double wheelCircumference = 4 * Math.PI;
        double wheelMotor = 560;
        double ticks = (distanceMoving * (wheelMotor/wheelCircumference));

        robot.setPower(0, 0, 0, 0);

        robot.lf.setTargetPosition((int) Math.round(ticks));
        robot.rf.setTargetPosition((int) Math.round(ticks));
        robot.lb.setTargetPosition((int) Math.round(ticks));
        robot.rb.setTargetPosition((int) Math.round(ticks));

        robot.lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.setPower(speedMoving, speedMoving, speedMoving, speedMoving);
        while(opModeIsActive() && robot.lf.getCurrentPosition() + 10
                < ticks || robot.lf.getCurrentPosition() - 10 > ticks){
        }

        robot.setPower(0, 0, 0, 0);

        robot.lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void turning (double degrees) {

        robot.lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double currentAngle = robot.gyro.getAngularOrientation().firstAngle;
        double finalAngle = currentAngle + degrees;

        if (finalAngle > 180) {
            finalAngle -= 360;
        } else if (finalAngle < -180) {
            finalAngle += 360;
        }

        if(degrees >= 0) {
            double errorOfDegree = degrees;
            while ((Math.abs(errorOfDegree)) > 5) {
                robot.setPower(-0.0055 * errorOfDegree,
                        -0.0055 * errorOfDegree, 0.0055 * errorOfDegree,
                        0.0055 * errorOfDegree);
                errorOfDegree = finalAngle - robot.gyro.getAngularOrientation().firstAngle;

                if (errorOfDegree > 180) {
                    errorOfDegree -= 360;
                } else if (errorOfDegree > 180) {
                    errorOfDegree += 360;
                }

                errorOfDegree = Math.abs(errorOfDegree);
            }
        } else {
            double errorOfDegree = degrees;
            while ((Math.abs(errorOfDegree)) > 5) {
                robot.setPower(0.0055 * errorOfDegree,
                        0.0055 * errorOfDegree, -0.0055 * errorOfDegree,
                        -0.0055 * errorOfDegree);
                errorOfDegree = finalAngle - robot.gyro.getAngularOrientation().firstAngle;

                if (errorOfDegree > 180) {
                    errorOfDegree -= 360;
                } else if (errorOfDegree > 180) {
                    errorOfDegree += 360;
                }

                errorOfDegree = Math.abs(errorOfDegree);
            }
            robot.setPower(0, 0, 0, 0);

        }

    }
}
