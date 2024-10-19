package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "LEFT_BLUE_AUTO", group = "Autonomous")
public class LeftBlueAuto extends LinearOpMode {
    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(12, -72, Math.toRadians(-90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder builder = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(6, 30))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(36, 36), Math.toRadians(320))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(48, 54), Math.toRadians(45))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(48, 36), Math.toRadians(320))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(60, 54), Math.toRadians(45))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(72, 36), Math.toRadians(320))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(60, 54), Math.toRadians(45))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(60, 36), Math.toRadians(180))
                .strafeTo(new Vector2d(36, 30));

        Actions.runBlocking(
                new SequentialAction(
                        builder.build()
                )
        );
    }
}
