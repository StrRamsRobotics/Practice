package org.firstinspires.ftc.teamcode.opmodes;

import android.util.Pair;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.EOCV.vision.RedCenterDetectionPipeline;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.openftc.easyopencv.OpenCvWebcam;

@TeleOp
public class Teleop extends LinearOpMode {
    private Servo crServo;
    private RedCenterDetectionPipeline pipeline;
    private OpenCvWebcam camera;
    private boolean running = false;
    private final Pair<Integer, Integer> cameraResolution = new Pair<>(320, 240);

    @Override
    public void runOpMode() throws InterruptedException {
        Gamepad gamepad1 = this.gamepad1;
        Gamepad gamepad2 = this.gamepad2;

        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        while (opModeIsActive()) {
            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x;
            drive.setDrivePowers(new PoseVelocity2d(new Vector2d(x, y), rx));
            CRServoImplEx servo = hardwareMap.get(CRServoImplEx.class, "servo");
            if (gamepad2.left_trigger >= 0.5 && gamepad2.left_trigger <= 1) {
                servo.setPower(1);
            }
            if (gamepad2.right_trigger >= 0.5 && gamepad2.right_trigger <= 1) {
                servo.setPower(-1);
            }

            // Claw alignment

            if (gamepad1.a && !running) {
                running = true;
            }

            if (running) {
                alignClaw();
            }
        }
    }

    private void alignClaw() {
        double angle = pipeline.getAngle();
        double TARGET_ANGLE = 0;
        double ANGLE_THRESHOLD = 5;
        if (minDistance(angle, TARGET_ANGLE) > ANGLE_THRESHOLD) {
            crServo.setPosition(angle / 180);
            running = false;
        }
    }

    public double clockwiseDistance(double currentAngle, double targetAngle) {
        return (currentAngle - targetAngle + 360) % 360;
    }

    public double counterClockwiseDistance(double currentAngle, double targetAngle) {
        return (targetAngle - currentAngle + 360) % 360;
    }

    public double minDistance(double currentAngle, double targetAngle) {
        return Math.min(clockwiseDistance(currentAngle, targetAngle), counterClockwiseDistance(currentAngle, targetAngle));
    }
}
