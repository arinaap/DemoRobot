package org.firstinspires.ftc.teamcode.DemoRobot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp (name = "ServoTest")
public class ServoTest extends LinearOpMode {

    DemoHardware robot = DemoHardware.getInstance();

    public void runOpMode(){
        robot.init(hardwareMap);

        telemetry.addData("Status", "Hello!");
        telemetry.update();

        waitForStart();

        int position = 0;
        boolean pressinga = false;
        boolean pressingb = false;

        while(opModeIsActive()){
            //servos can be contin mode where spin unitl stop spin, in that mode, go
            // negative with 0-0.49999, positive direction is 0.51-1

            robot.demoServo.setPosition(position);

            if(gamepad1.a && !pressinga){
                position += 0.05;
                pressinga = true;
            } else if(!gamepad1.a){
                pressinga = false;
            }

            if(gamepad1.b && !pressingb){
                position -= 0.05;
                pressingb = false;
            } else if(!gamepad1.b){
                pressingb = false;
            }

            telemetry.addData("Position", position);
            telemetry.addData("ActualServoPosition", robot.demoServo.getPosition());
            telemetry.update();

        }
    }
}
