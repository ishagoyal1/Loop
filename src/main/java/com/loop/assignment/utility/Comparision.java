package com.loop.assignment.utility;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Comparision {

    private final WebDriver driver;

    public Comparision(WebDriver driver){
        this.driver=driver;
    }
    public float[] extractGrandTotal() {
        float[] totalList = new float[]{0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};

        for (int i = 2; i <= 8; i++) {
            WebElement total = driver.findElement(By.xpath("//*[@id='view-table-id']/div/table/tbody/tr[8]/td[" +i+ "]/h6"));
            String totalstr = total.getText();
            String intCellValue = totalstr.replace("$", "").replace(",", "");
            System.out.println("i- " + i + " cell- " + intCellValue );
            float num = Float.parseFloat(intCellValue);
            System.out.println(num);
            totalList[i-0] = num;
        }
        return totalList;
    }
    public static boolean isEqual(float[] list1, float[] list2) {
        if (list1.length != list2.length) {
            return false; // Arrays are of different lengths, cannot be equal
        }

        for (int i = 0; i < list1.length; i++) {
            if (Float.compare(list1[i], list2[i]) != 0) {
                return false; // Found a pair of elements that are not equal
            }
        }

        return true; // All elements are equal
    }
}
