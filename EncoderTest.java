package org.firstinspires.ftc.teamcode.DemoRobot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp (name = "EncoderTest")
public class EncoderTest extends LinearOpMode {

    DemoHardware robot = DemoHardware.getInstance();


    public void runOpMode(){

        robot.init(hardwareMap);

        telemetry.addData("Status", "Hello, driver!");
        telemetry.update();

        int position = 0;
        boolean pressinga = false;
        boolean pressingb = false;

        waitForStart();

        while(opModeIsActive()){

            //always need these three methods to test encoder
            position = robot.demoMotor.getCurrentPosition();
            robot.demoMotor.setTargetPosition(position);
            robot.demoMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if(gamepad1.a && !pressinga){
                position += 10;
                pressinga = true;
            } else if(!gamepad1.a){
                pressinga = false;
            }

            if(gamepad1.b && !pressingb){
                position -= 10;
                pressingb = false;
            } else if(!gamepad1.b){
                pressingb = false;
            }

            telemetry.addData("Position", position);
            telemetry.addData("ActualMotorPosition", robot.demoMotor.getCurrentPosition());
            telemetry.update();

        }
    }
}
