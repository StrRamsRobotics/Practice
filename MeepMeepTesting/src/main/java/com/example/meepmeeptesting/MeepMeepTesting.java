package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.util.FieldUtil;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    private static SimpleBuilder builder;
    private static RoadRunnerBotEntity myBot;
    private static final double TILE_SIZE = 24;

    private static void startAt(double x, double y, double heading) {
        builder = new SimpleBuilder(myBot.getDrive().actionBuilder(new Pose2d(x * TILE_SIZE, y * TILE_SIZE, Math.toRadians(heading))));
    }

    private static void strafe(double x, double y) {
        builder.strafe(x, y);
    }

    private static void waitS(double seconds) {
        builder.wait(seconds);
    }

    private static void line(double x, double y, double heading) {
        builder.line(x, y, heading);
    }

    private static void spline(double x, double y, double heading) {
        builder.spline(x, y, heading);
    }

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        FieldUtil.setFIELD_HEIGHT(144);
        FieldUtil.setFIELD_WIDTH(144);

        final Mode mode = Mode.RIGHT_RED_BASIC;
        myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        switch (mode) {
            case LEFT_RED -> {
                startAt(-0.5, -3, 90);
                strafe(-0.25, -1.25);
                waitS(1); // deposit first specimen
                line(-1.5, -1.5, 135);
                waitS(1); // pick up first sample
                line(-2, -2.5, 200);
                waitS(1); // deposit first sample
                line(-2, -1.5, 135);
                waitS(1); // pick up second sample
                line(-2, -2.5, 200);
                waitS(1); // deposit second sample
                line(-2.3, -1.6, 135);
                waitS(1); // pick up third sample
                line(-2, -2.5, 200);
                waitS(1); // deposit third sample
                line(-1.5, -1.5, 0);
                strafe(2.5, -1.5);
                line(2.5, -2.5, 90); // park
            }
            case RIGHT_RED -> {
                startAt(0.5, -3, 90);
                strafe(0.25, -1.25);
                waitS(1); // deposit first specimen
                line(1.5, -1.5, 45);
                waitS(1); // pick up first sample
                line(-1, -1.5, 122.5);
                line(-2, -2.5, 200);
                waitS(1); // deposit first sample
                line(-1, -1.5, 122.5);
                line(2, -1.5, 45);
                waitS(1); // pick up second sample
                line(-1, -1.5, 122.5);
                line(-2, -2.5, 200);
                waitS(1); // deposit second sample
                line(-1, -1.5, 0);
                line(2.5, -1.5, 90);
                strafe(2.5, -2.5); // park
            }
            case LEFT_BLUE -> {
                startAt(-0.5, 3, 90);
                strafe(-0.25, 1.25);
                waitS(1); // deposit first specimen
                line(-1.5, 1.5, 220);
                waitS(1); // pick up first sample
                line(2, 1.5, 220);
                line(2, 2.5, 45);
                waitS(1); // deposit first sample
                line(2, 1.5, 220);
                line(-2, 1.5, 220);
                waitS(1); // pick up second sample
                line(2, 1.5, 220);
                line(2, 2.5, 45);
                waitS(1); // deposit second sample
                line(2, 1.5, 45);
                line(-1.5, 1.5, 45);
                line(-2.5, 2.5,270);//park
            }
            case RED_STEAL-> {
                startAt(0.5, -3, 90);
                strafe(0.25, -1.25);
                waitS(1); // deposit first specimen
                line(1.5, -1.5, 85);
                line(2, 0.6, 85);
                waitS(1); // pick up first sample
                line(1, -1.5, 150);
                line(-2.5, -2, 250);
                waitS(1); // deposit first sample
                line(1, -1.5, 80);
                line(2.5, 0.6, 80);
                waitS(1); // pick up second sample
                line(1, -1.5, 150);
                line(-2.5, -2, 250);
                waitS(1); // deposit second
                strafe(1, -1.5);
                strafe(2.5, -2.5); // park
            }
            case LEFT_BLUE_BASIC-> {
                startAt(-0.5, 3, 270);
                strafe(-0.25, 1.5);
                waitS(1); // deposit first specimen
                line(-1.3, 1.6,91);
                strafe(-1.3, 0.6);
            }
            case RIGHT_BLUE_BASIC-> {
                startAt(0.5, 3, 270);
                strafe(0.25, 1.5);
                waitS(1); // deposit first specimen
                line(1.3, 1.6,91);
                strafe(1.3, 0.6);
            }
            case LEFT_RED_BASIC -> {
                startAt(-0.5, -3, 90);
                strafe(-0.25, -1.5);
                waitS(1); // deposit first specimen
                line(-1.3, -1.6,271);
                strafe(-1.3, -0.6);
            }
            case RIGHT_RED_BASIC -> {
                startAt(0.5, -3, 90);
                strafe(0.25, -1.5);
                waitS(1); // deposit first specimen
                line(1.3, -1.6,271);
                strafe(1.3, -0.6);
            }
        }

        myBot.runAction(builder.build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}