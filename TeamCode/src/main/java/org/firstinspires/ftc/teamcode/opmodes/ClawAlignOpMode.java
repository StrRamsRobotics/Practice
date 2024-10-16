package org.firstinspires.ftc.teamcode.opmodes;

import android.util.Pair;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.EOCV.vision.RedCenterDetectionPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@TeleOp(name = "Claw Align OpMode", group = "TeleOp")
public class ClawAlignOpMode extends OpMode {
    private CRServo crServo;
    private RedCenterDetectionPipeline pipeline;
    private OpenCvWebcam camera;
    private boolean running = false;
    private final Pair<Integer, Integer> cameraResolution = new Pair<>(320, 240);

    @Override
    public void init() {
        crServo = hardwareMap.get(CRServo.class, "wobbleGrabber");
        pipeline = new RedCenterDetectionPipeline();

        int cameraMonitoryViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "camera"), cameraMonitoryViewId);
        camera.setPipeline(pipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(cameraResolution.first, cameraResolution.second, OpenCvCameraRotation.UPRIGHT);
                FtcDashboard.getInstance().startCameraStream(camera, 30);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera Error", errorCode);
            }
        });
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

    @Override
    public void loop() {
        if (gamepad1.a && !running) {
            running = true;
        }

        if (running) {
            double angle = pipeline.getAngle();
            double TARGET_ANGLE = 0;
            double ANGLE_THRESHOLD = 5;
            if (minDistance(angle, TARGET_ANGLE) > ANGLE_THRESHOLD) {
                crServo.setPower(clockwiseDistance(angle, TARGET_ANGLE) < counterClockwiseDistance(angle, TARGET_ANGLE) ? 1 : -1);
            } else {
                crServo.setPower(0);
                running = false;
            }
        }
    }
}
