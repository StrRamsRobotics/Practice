package org.firstinspires.ftc.teamcode.opmodes.actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.Chassis;

public class ClawAlignAction implements Action {
    private final Chassis chassis;

    public ClawAlignAction(Chassis chassis) {
        this.chassis = chassis;
    }

    @Override
    public boolean run(@NonNull TelemetryPacket p) {
        alignClaw();
        return false;
    }

    private void alignClaw() {
        double angle;
        if (Chassis.isBlue) {
            angle = chassis.bluePipeline.angle;
        }
        else {
            angle = chassis.redPipeline.angle;
        }
        double TARGET_ANGLE = 0;
        double ANGLE_THRESHOLD = 5;
        if (minDistance(angle, TARGET_ANGLE) > ANGLE_THRESHOLD) {
            chassis.clawRotate.setPosition(angle / 180);
        }
    }

    public double clockwiseDistance(double currentAngle, double targetAngle) {
        return (currentAngle - targetAngle + 360) % 360;
    }

    public double counterClockwiseDistance(double currentAngle, double targetAngle) {
        return (targetAngle - currentAngle + 360) % 360;
    }

    public double minDistance(double currentAngle, double targetAngle) {
        return Math.min(clockwiseDistance(currentAngle, targetAngle), counterClockwiseDistance(currentAngle, targetAngle));
    }

    @Override
    public void preview(@NonNull Canvas fieldOverlay) {
    }
}
