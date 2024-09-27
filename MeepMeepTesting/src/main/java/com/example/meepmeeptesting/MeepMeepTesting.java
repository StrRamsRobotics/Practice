package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.core.util.FieldUtil;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import org.rowlandhall.meepmeep.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

public class MeepMeepTesting {
    public static final double TILE_SIZE = 24;

    private static void strafe(TrajectorySequenceBuilder builder, double x, double y) {
        builder.strafeTo(new Vector2d(x * TILE_SIZE, y * TILE_SIZE));
    }

    private static void line(TrajectorySequenceBuilder builder, double x, double y, double heading) {
        builder.lineToLinearHeading(new Pose2d(x * TILE_SIZE, y * TILE_SIZE, Math.toRadians(heading)));
    }

    private static void wait(TrajectorySequenceBuilder builder, double seconds) {
        builder.waitSeconds(seconds);
    }

    private static void spline(TrajectorySequenceBuilder builder, double x, double y, double heading) {
        builder.splineTo(new Vector2d(x * TILE_SIZE, y * TILE_SIZE), Math.toRadians(heading));
    }

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(720);
        FieldUtil.setFIELD_HEIGHT(144);
        FieldUtil.setFIELD_WIDTH(144);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> {
                    var builder = drive.trajectorySequenceBuilder(new Pose2d(12, -72, Math.toRadians(90)));
                    strafe(builder, 0.25, -1.25);
                    wait(builder, 1); // deposit first specimen
                    strafe(builder, 2, -1.5);
                    wait(builder, 1); // pick up first sample
                    line(builder, -1.5, -1.5, 180);
                    spline(builder, -2.5, -2.25, 270);
                    wait(builder, 1); // deposit first sample
                    line(builder, -2.5, -1.5, 180);
                    line(builder, 2.5, -1.5, 90);
                    wait(builder, 1); // pick up second sample
                    line(builder, -1.5, -1.5, 180);
                    spline(builder, -2.5, -2.25, 270);
                    wait(builder, 1); // deposit second sample
                    strafe(builder, -2.5, -1.5);
                    strafe(builder, 2.5, -1.5);
                    strafe(builder, 2.5, -2.5); // park

                    return builder.build();
                });


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}