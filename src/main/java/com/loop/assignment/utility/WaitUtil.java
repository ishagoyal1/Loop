package com.loop.assignment.utility;

public class WaitUtil {


    public static void waitSeconds(long sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
