package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.EOCV.vision.RedCenterDetectionPipeline;

@TeleOp(name="Claw Align OpMode", group = "TeleOp")
public class ClawAlignOpMode extends OpMode {
    private CRServo crServo;
    private final double TARGET_ANGLE = 0;
    private boolean buttonPressed = false;
    private RedCenterDetectionPipeline pipeline;

    @Override
    public void init() {
        crServo = hardwareMap.get(CRServo.class, "servo");
    }

    @Override
    public void loop() {
        if (buttonPressed) {
            double angle = pipeline.getAngle();
            boolean clockwise =
        }
    }
}
