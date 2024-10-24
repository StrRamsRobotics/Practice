package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Chassis;

@TeleOp
public class Teleop extends LinearOpMode {
    private boolean isAHeld = false;
    private boolean isYHeld = false; // deposit preset
    private boolean isXHeld = false; // pickup preset
    public Chassis chassis;
    private static final boolean IS_BLUE = true;

    @Override
    public void runOpMode() {
        this.chassis = new Chassis(hardwareMap, telemetry, IS_BLUE);
        chassis.logHelper.addData("Running", "Teleop");
        Gamepad gamepad1 = this.gamepad1;
        Gamepad gamepad2 = this.gamepad2;

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

            // Claw pickup

            if (gamepad1.y && !isYHeld) {
                isYHeld = true;
                pickup();
            }

            if (!gamepad1.y && isYHeld) {
                isYHeld = false;
                double armAngle = chassis.armPivot1.getCurrentPosition() * 180; // angle of the arm
                chassis.clawPitch.setPosition((90 - armAngle) / 180); // make the claw perpendicular to the ground
            }

            // Claw alignment

            if (gamepad1.a && !isAHeld) {
                isAHeld = true;
                alignClaw();
            }

            if (!gamepad1.a) {
                isAHeld = false;
            }
        }
    }

    private void pickup() {
        chassis.armPivot1.setTargetPosition(90);
        chassis.armPivot2.setTargetPosition(90);
        chassis.slidesPivot1.setTargetPosition(90);
        chassis.slidesPivot2.setTargetPosition(90);
    }

    private void alignClaw() {
        double angle;
        if (Chassis.isBlue) {
            angle = chassis.bluePipeline.angle;
        } else {
            angle = chassis.redPipeline.angle;
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
