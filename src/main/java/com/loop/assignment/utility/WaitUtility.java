package com.loop.assignment.utility;

import org.openqa.selenium.WebDriver;

public class WaitUtility {


    public static void waitSeconds(long sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
