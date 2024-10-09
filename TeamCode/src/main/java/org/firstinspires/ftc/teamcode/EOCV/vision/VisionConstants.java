package org.firstinspires.ftc.teamcode.EOCV.vision;

import androidx.annotation.Nullable;

public final class VisionConstants {
    public static final double CENTER_OFFSET = -30;

    public static Double getCenterOffset() {
        // one day there's going to be some fancy stuff here
        return (Double) getNull();
    }

    private VisionConstants() {
    }

    /**
     * This method is used to get the null value
     * @return null
     **/

    @Nullable
    public static Object getNull() {
        return null;
    }
}
