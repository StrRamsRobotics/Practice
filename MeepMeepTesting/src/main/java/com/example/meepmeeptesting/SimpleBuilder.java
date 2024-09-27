package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;

public class SimpleBuilder {
    TrajectoryActionBuilder builder;
    private static final double TILE_SIZE = 24;

    public SimpleBuilder(TrajectoryActionBuilder builder) {
        this.builder = builder;
    }

    public void strafe(double x, double y) {
        builder = builder.strafeTo(new Vector2d(x * TILE_SIZE, y * TILE_SIZE));
    }

    public void wait(double seconds) {
        builder = builder.waitSeconds(seconds);
    }

    public void line(double x, double y, double heading) {
        builder = builder.strafeToLinearHeading(new Vector2d(x * TILE_SIZE, y * TILE_SIZE), Math.toRadians(heading));
    }

    public void spline(double x, double y, double heading) {
        builder = builder.splineTo(new Vector2d(x * TILE_SIZE, y * TILE_SIZE), Math.toRadians(heading));
    }

    public Action build() {
        return builder.build();
    }
}
