package com.loop.assignment.utility;

public class ComparisonUtil {

    public static boolean isEqual(float[] list1, float[] list2) {
        if (list1.length != list2.length) {
            return false; // Arrays are of different lengths, cannot be equal
        }
        for (int i = 0; i < list1.length; i++) {
            if (Math.abs(list1[i]-list2[i]) > 0.01) { //giving margin of error of 0.01 while comparing float
                return false;
            }
        }
        return true; // All elements are equal
    }
}
