package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import org.rowlandhall.meepmeep.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

public class MeepMeepTesting {
    public static final double TILE_SIZE = 24;

    private static void scoreSample(TrajectorySequenceBuilder builder, double x, double y) {
        builder.splineToSplineHeading(new Pose2d(x, y, Math.toRadians(90)), Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(0, -2 * TILE_SIZE, Math.toRadians(180)), Math.toRadians(180))
                .splineToSplineHeading(new Pose2d(-2.5 * TILE_SIZE, -2.5 * TILE_SIZE, Math.toRadians(225)), Math.toRadians(225))
                .splineToSplineHeading(new Pose2d(-2 * TILE_SIZE, -2 * TILE_SIZE, Math.toRadians(225)), Math.toRadians(225))
                .splineToSplineHeading(new Pose2d(0, -2 * TILE_SIZE, Math.toRadians(0)), Math.toRadians(180));
    }

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> {
                    var builder = drive.trajectorySequenceBuilder(new Pose2d(12, -72, Math.toRadians(90)));
                    scoreSample(builder, 2 * TILE_SIZE, -1.5 * TILE_SIZE);
                    scoreSample(builder, 2.5 * TILE_SIZE, -1.5 * TILE_SIZE);
                    return builder.build();
                });


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}