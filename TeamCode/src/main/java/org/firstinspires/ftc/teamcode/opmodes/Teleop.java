package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Robot;

@TeleOp
public class Teleop extends LinearOpMode {
    private boolean isHeld = false;

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

            // Claw open/close

            if (gamepad2.left_trigger >= 0.5 && gamepad2.left_trigger <= 1) {
                Robot.claw.setPower(1);
            }
            if (gamepad2.right_trigger >= 0.5 && gamepad2.right_trigger <= 1) {
                Robot.claw.setPower(-1);
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
        if (Robot.isBlue) {
            angle = Robot.bluePipeline.angle;
        } else {
            angle = Robot.redPipeline.angle;
        }
        double TARGET_ANGLE = 0;
        double ANGLE_THRESHOLD = 5;
        if (minDistance(angle, TARGET_ANGLE) > ANGLE_THRESHOLD) {
            Robot.clawRotate.setPosition(angle / 180);
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
