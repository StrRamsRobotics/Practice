package org.firstinspires.ftc.teamcode;

import android.util.Pair;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.EOCV.vision.BlueCenterDetectionPipeline;
import org.firstinspires.ftc.teamcode.EOCV.vision.RedCenterDetectionPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;


public class Robot {
    public static MecanumDrive drive;
    private static OpenCvWebcam camera;
    private static CRServo clawRotateServo;
    // True if blue alliance, false if red alliance
    private static final boolean isBlue = true;
    private static final Pair<Integer, Integer> cameraResolution = new Pair<>(320, 240);


    public static void init(HardwareMap hardwareMap) {
        clawRotateServo = hardwareMap.get(CRServo.class, "wobbleGrabber");
        drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));
        FtcDashboard.start(null);
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "camera"), cameraMonitorViewId);
        // Set pipeline based on alliance color
        camera.setPipeline(isBlue ? new BlueCenterDetectionPipeline() : new RedCenterDetectionPipeline());
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(cameraResolution.first, cameraResolution.second, OpenCvCameraRotation.UPRIGHT);
                FtcDashboard.getInstance().startCameraStream(camera, 30);
            }
            @Override
            public void onError(int errorCode) {
            }
        });

    }
}