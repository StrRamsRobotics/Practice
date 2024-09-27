package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.util.FieldUtil;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static final double TILE_SIZE = 24;

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        FieldUtil.setFIELD_HEIGHT(144);
        FieldUtil.setFIELD_WIDTH(144);

        int mode = 1;

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        switch (mode) {
            case 0:
                myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(12, -72, Math.toRadians(90)))
                        .strafeTo(new Vector2d(0.25 * TILE_SIZE, -1.25 * TILE_SIZE))
                        .waitSeconds(1) // deposit first specimen
                        .strafeTo(new Vector2d(2 * TILE_SIZE, -1.5 * TILE_SIZE))
                        .waitSeconds(1) // pick up first sample
                        .strafeToLinearHeading(new Vector2d(-1.5 * TILE_SIZE, -1.5 * TILE_SIZE), Math.toRadians(180))
                        .strafeToLinearHeading(new Vector2d(-2.5 * TILE_SIZE, -2.25 * TILE_SIZE), Math.toRadians(270))
                        .waitSeconds(1) // deposit first sample
                        .strafeToLinearHeading(new Vector2d(-2.5 * TILE_SIZE, -1.5 * TILE_SIZE), Math.toRadians(180))
                        .strafeToLinearHeading(new Vector2d(2.5 * TILE_SIZE, -1.5 * TILE_SIZE), Math.toRadians(90))
                        .waitSeconds(1) // pick up second sample
                        .strafeToLinearHeading(new Vector2d(-1.5 * TILE_SIZE, -1.5 * TILE_SIZE), Math.toRadians(180))
                        .splineTo(new Vector2d(-2.5 * TILE_SIZE, -2.25 * TILE_SIZE), Math.toRadians(270))
                        .waitSeconds(1) // deposit second sample
                        .strafeTo(new Vector2d(-2.5 * TILE_SIZE, -1.5 * TILE_SIZE))
                        .strafeTo(new Vector2d(2.5 * TILE_SIZE, -1.5 * TILE_SIZE))
                        .strafeTo(new Vector2d(2.5 * TILE_SIZE, -2.5 * TILE_SIZE)) // park
                        .build());

            case 1:
                myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-12, -72, Math.toRadians(90)))
                                .strafeTo(new Vector2d(-0.25 * TILE_SIZE, -1.25 * TILE_SIZE))
                                .waitSeconds(1) // deposit first specimen
                                .strafeToLinearHeading(new Vector2d(-1.5 * TILE_SIZE, -1.5 * TILE_SIZE), Math.toRadians(135))
                                .waitSeconds(1) // pick up first sample
                                .strafeToLinearHeading(new Vector2d(-2 * TILE_SIZE, -2.5 * TILE_SIZE), Math.toRadians(200))
                                .waitSeconds(1) // deposit first sample
                                .strafeToLinearHeading(new Vector2d(-2 * TILE_SIZE, -1.5 * TILE_SIZE), Math.toRadians(135))
                                .waitSeconds(1) // pick up second sample
                                .strafeToLinearHeading(new Vector2d(-2 * TILE_SIZE, -2.5 * TILE_SIZE), Math.toRadians(200))
                                .waitSeconds(1) // deposit second sample
                                .strafeToLinearHeading(new Vector2d(-2.3 * TILE_SIZE, -1.6 * TILE_SIZE), Math.toRadians(135))
                                .waitSeconds(1) // pick up third sample
                                .strafeToLinearHeading(new Vector2d(-2 * TILE_SIZE, -2.5 * TILE_SIZE), Math.toRadians(200))
                                .waitSeconds(1) // deposit third sample
                                .strafeToLinearHeading(new Vector2d(-1.5 * TILE_SIZE, -1.5 * TILE_SIZE), Math.toRadians(0))
                                .strafeTo(new Vector2d(2.5 * TILE_SIZE, -1.5 * TILE_SIZE))
                                .strafeTo(new Vector2d(2.5 * TILE_SIZE, -2.5 * TILE_SIZE)) // park
                                .build());
        }
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}