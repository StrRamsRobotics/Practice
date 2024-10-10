package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.EOCV.vision.RedCenterDetectionPipeline;

@TeleOp(name="Claw Align OpMode", group = "TeleOp")
public class ClawAlignOpMode extends OpMode {
    private CRServo crServo;
    private final double TARGET_ANGLE = 0;
    private final double ANGLE_THRESHOLD = 5;
    private boolean buttonPressed = false;
    private RedCenterDetectionPipeline pipeline;

    @Override
    public void init() {
        crServo = hardwareMap.get(CRServo.class, "servo");
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

    public double flipAngle(double angle) {
        return (angle + 360) % 360 - 180;
    }

    @Override
    public void loop() {
        if (buttonPressed) {
            double angle = pipeline.getAngle();
            angle = (minDistance(flipAngle(angle), TARGET_ANGLE) < minDistance(angle, TARGET_ANGLE)) ? flipAngle(angle) : angle;

            if (angle > ANGLE_THRESHOLD) {
                crServo.setPower(clockwiseDistance(angle, TARGET_ANGLE) < counterClockwiseDistance(angle, TARGET_ANGLE) ? 1 : -1);
            } else {
                crServo.setPower(0);
            }
        }
    }
}
