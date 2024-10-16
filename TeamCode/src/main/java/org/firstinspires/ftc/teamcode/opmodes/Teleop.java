package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import com.qualcomm.robotcore.hardware.CRServoImplEx;



@TeleOp
public class Teleop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Gamepad gamepad1 = this.gamepad1;
        Gamepad gamepad2 = this.gamepad2;

        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));

        while (opModeIsActive()) {
            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x;
            drive.setDrivePowers(new PoseVelocity2d(new Vector2d(x, y), rx));
            CRServoImplEx servo = hardwareMap.get(CRServoImplEx.class, "servo");
            if(gamepad2.left_trigger >= 0.5 && gamepad2.left_trigger <= 1){
                servo.setPower(1);
            }
            if(gamepad2.right_trigger >= 0.5 && gamepad2.right_trigger <= 1){
                servo.setPower(-1);
            }
        }
    }
}
