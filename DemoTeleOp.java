package org.firstinspires.ftc.teamcode.DemoRobot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "TeleOp")
public class DemoTeleOp extends LinearOpMode {
    DemoHardware robot = DemoHardware.getInstance();

    public void runOpMode(){
        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        if(robot.lf != null){
            robot.lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        if(robot.lb != null){
            robot.lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        if(robot.rf != null){
            robot.rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        if(robot.rb != null){
            robot.rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        waitForStart();

        boolean pressingb = false;
        boolean pressingx = false;
        boolean switcharoo = true;

        while(opModeIsActive()){

            double forward;
            double sideways;
            double turning;

            forward = gamepad1.left_stick_x;
            sideways = -gamepad1.right_stick_y;
            turning = -gamepad1.right_stick_y;

            double max = Math.max(Math.abs(forward - sideways - turning),
                    Math.max(Math.abs(forward + sideways - turning),
                            Math.max(Math.abs(forward + sideways + turning),
                            Math.abs(forward + turning - sideways))));
            if(max < robot.maxSpeed){
                robot.setPower(forward - sideways - turning,
                        forward + sideways - turning,
                        forward + sideways + turning,
                        forward + turning - sideways);
            } else{
                double scaleFactor = max/robot.maxSpeed;
                robot.setPower((forward - sideways - turning) * scaleFactor,
                        (forward + sideways - turning) * scaleFactor,
                        (forward + sideways + turning) * scaleFactor,
                        (forward + turning - sideways) * scaleFactor);
            }

            //Button must always be pressed
            if(gamepad2.a){
                //Have button do function
            }

            //Press button once
            if(gamepad2.b && !pressingb){
                //Function moves arm up 10 encoder ticks
                pressingb = true;
            } else if (!gamepad2.b){
                pressingb = false;
            }


            //Press same button for two actions
            if(gamepad2.x && !pressingx){
                if(switcharoo){
                    //claw opens
                    switcharoo = false;
                } else{

                    switcharoo = true;
                }
                pressingx = true;
            } else if(!gamepad2.x){
                pressingx = false;
            }

            //Pressing same button version 2
            if(gamepad2.x && !pressingx && switcharoo){
                switcharoo = false;
                pressingx = true;
            } else if(gamepad2.x && !pressingx && !switcharoo){
                switcharoo = true;
                pressingx = true;
            } else if(!gamepad2.x){
                pressingx = false;
            }

            if((gamepad2.right_trigger > 0.1)){
                //Use trigger as button to open claw for example
            }
        }
    }

}
