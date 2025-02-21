package org.firstinspires.ftc.teamcode.util;
@functionalInterface
public interface ease {
    ease easeP1 = x -> Math.pow(x, 2);
    ease easeP2 = x -> Math.pow(x, 3);
    ease easeP3 = x -> Math.pow(x, 4);
    ease easeP4 = x -> Math.pow(x, 5);
}