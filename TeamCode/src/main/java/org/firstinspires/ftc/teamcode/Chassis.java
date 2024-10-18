package org.firstinspires.ftc.teamcode;

import android.util.Pair;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.EOCV.vision.BlueCenterDetectionPipeline;
import org.firstinspires.ftc.teamcode.EOCV.vision.RedCenterDetectionPipeline;
import org.firstinspires.ftc.teamcode.helpers.LogHelper;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;


public class Chassis {
    public MecanumDrive drive;
    public HardwareMap hardwareMap;
    public Telemetry telemetry;

    public Servo clawRotate;
    public CRServoImplEx claw;

    public OpenCvWebcam camera;
    public static final boolean isBlue = true;
    public final Pair<Integer, Integer> CAMERA_RESOLUTION = new Pair<>(320, 240);
    public FtcDashboard ftcDashboard = FtcDashboard.getInstance();
    public LogHelper logHelper = new LogHelper(this);
    public RedCenterDetectionPipeline redPipeline;
    public BlueCenterDetectionPipeline bluePipeline;

    public Chassis(HardwareMap hardwareMap, Telemetry telemetry) {
        initializeUtils(hardwareMap, telemetry);
        initializeMotors();
    }

    public Chassis(HardwareMap hardwareMap, Telemetry telemetry, boolean isBlue) {
        initializeUtils(hardwareMap, telemetry);
        initializeMotors();
        initializeCamera(isBlue);
    }

    public void initializeUtils(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
    }

    public void initializeMotors() {
        clawRotate = hardwareMap.get(Servo.class, "clawRotate");
        claw = hardwareMap.get(CRServoImplEx.class, "claw");

        drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));
    }

    public void initializeCamera(boolean isBlue) {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "camera"), cameraMonitorViewId);
        bluePipeline = new BlueCenterDetectionPipeline();
        redPipeline = new RedCenterDetectionPipeline();
        camera.setPipeline(isBlue ? bluePipeline : redPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(CAMERA_RESOLUTION.first, CAMERA_RESOLUTION.second, OpenCvCameraRotation.UPRIGHT);
                FtcDashboard.getInstance().startCameraStream(camera, 30);
            }
            @Override
            public void onError(int errorCode) {}
        });
    }
}