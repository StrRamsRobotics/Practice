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
