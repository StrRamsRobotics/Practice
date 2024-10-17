package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.EOCV.vision.BlueCenterDetectionPipeline;
import org.firstinspires.ftc.teamcode.EOCV.vision.RedCenterDetectionPipeline;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Chassis;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;

@TeleOp
public class Teleop extends LinearOpMode {
    private boolean isHeld = false;
    public RedCenterDetectionPipeline redPipeline;
    public BlueCenterDetectionPipeline bluePipeline;
    public Chassis chassis;
    private final boolean IS_BLUE = true;

    @Override
    public void runOpMode() {
        this.chassis = new Chassis(hardwareMap, telemetry);
        chassis.logHelper.addData("Running", "Teleop");
        Gamepad gamepad1 = this.gamepad1;
        Gamepad gamepad2 = this.gamepad2;
        bluePipeline = new BlueCenterDetectionPipeline();
        redPipeline = new RedCenterDetectionPipeline();
        chassis.camera.setPipeline(IS_BLUE ? bluePipeline : redPipeline);
        chassis.camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                chassis.camera.startStreaming(chassis.CAMERA_RESOLUTION.first, chassis.CAMERA_RESOLUTION.second, OpenCvCameraRotation.UPRIGHT);
                FtcDashboard.getInstance().startCameraStream(chassis.camera, 30);
            }
            @Override
            public void onError(int errorCode) {}
        });

        waitForStart();

        while (opModeIsActive()) {
            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x;

            chassis.drive.setDrivePowers(new PoseVelocity2d(new Vector2d(x, y), rx));

            // Claw open/close

            if (gamepad2.left_trigger >= 0.5 && gamepad2.left_trigger <= 1) {
                chassis.claw.setPower(1);
            }
            if (gamepad2.right_trigger >= 0.5 && gamepad2.right_trigger <= 1) {
                chassis.claw.setPower(-1);
            }

            // Claw alignment

            if (gamepad1.a && !isHeld) {
                isHeld = true;
                alignClaw();
            }

            if (!gamepad1.a) {
                isHeld = false;
            }
        }
    }

    private void alignClaw() {
        double angle;
        if (Chassis.isBlue) {
            angle = bluePipeline.angle;
        } else {
            angle = redPipeline.angle;
        }
        double TARGET_ANGLE = 0;
        double ANGLE_THRESHOLD = 5;
        if (minDistance(angle, TARGET_ANGLE) > ANGLE_THRESHOLD) {
            chassis.clawRotate.setPosition(angle / 180);
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
