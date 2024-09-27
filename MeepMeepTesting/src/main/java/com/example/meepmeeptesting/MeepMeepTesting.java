package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.util.FieldUtil;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    private static SimpleBuilder builder;

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

        final int MODE = 1;

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        switch (MODE) {
            case 0:
                builder = new SimpleBuilder(myBot.getDrive().actionBuilder(new Pose2d(12, -72, Math.toRadians(90))));
                strafe(0.25, -1.25);
                waitS(1); // deposit first specimen
                strafe(2, -1.5);
                waitS(1); // pick up first sample
                line(-1.5, -1.5, 180);
                spline(-2.5, -2.25, 270);
                waitS(1); // deposit first sample
                line(-2.5, -1.5, 180);
                line(2.5, -1.5, 90);
                waitS(1); // pick up second sample
                line(-1.5, -1.5, 180);
                spline(-2.5, -2.25, 270);
                waitS(1); // deposit second sample
                strafe(-2.5, -1.5);
                strafe(2.5, -1.5);
                strafe(2.5, -2.5); // park
                break;
            case 1:
                builder = new SimpleBuilder(myBot.getDrive().actionBuilder(new Pose2d(-12, -72, Math.toRadians(90))));
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
                strafe(2.5, -2.5); // park
                break;
        }

        myBot.runAction(builder.build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}