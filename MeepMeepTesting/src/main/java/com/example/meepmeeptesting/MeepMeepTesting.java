package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.TimeProducer;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import org.rowlandhall.meepmeep.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

import java.util.Vector;

public class MeepMeepTesting {
    public static final double TILE_SIZE = 24;

    private static void scoreSample(TrajectorySequenceBuilder builder, double x, double y) {
        builder.splineToSplineHeading(new Pose2d(x, y, Math.toRadians(90)), Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(0, -2 * TILE_SIZE, Math.toRadians(180)), Math.toRadians(180))
                .splineToSplineHeading(new Pose2d(-2.5 * TILE_SIZE, -2.5 * TILE_SIZE, Math.toRadians(225)), Math.toRadians(225))
                .splineToSplineHeading(new Pose2d(-2 * TILE_SIZE, -2 * TILE_SIZE, Math.toRadians(225)), Math.toRadians(225))
                .splineToSplineHeading(new Pose2d(0, -2 * TILE_SIZE, Math.toRadians(0)), Math.toRadians(180))
;
    }

    private static void strafe(TrajectorySequenceBuilder builder, double x, double y) {
        builder.strafeTo(new Vector2d(x * TILE_SIZE, y * TILE_SIZE));
    }

    private static void spline(TrajectorySequenceBuilder builder, double x, double y, double heading) {
        builder.lineToLinearHeading(new Pose2d(x * TILE_SIZE, y * TILE_SIZE, Math.toRadians(heading)));
    }

    private static void wait(TrajectorySequenceBuilder builder, double seconds) {
        builder.waitSeconds(seconds);
    }

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> {
                    var builder = drive.trajectorySequenceBuilder(new Pose2d(12, -72, Math.toRadians(90)));
//                            .setReversed(true);
                    strafe(builder, 0.25, -1.25);
                    strafe(builder, 2, -1.5);
                    wait(builder, 0.5);
                    spline(builder, -2.5, -2.5, 225);
                    wait(builder, 0.5);
                    // deposited first sample

                    spline(builder, 2.5, -1.5, 90);
                    return builder.build();
                });


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}