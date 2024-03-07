package com.loop.assignment;

import com.loop.assignment.utility.WaitUtility;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;


public class DataValidation {

    private final WebDriver driver;

    public DataValidation(WebDriver driver){
        this.driver=driver;
    }
    public float[] extractSumOfColumns() {
        WaitUtility.waitSeconds(15);
        float[] sumList = new float[]{0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};

        WebElement element = driver.findElement(By.xpath
                ("//*[@class='MuiFormControl-root css-1nrlq1o-MuiFormControl-root']//div[@aria-haspopup='listbox']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();

        WaitUtility.waitSeconds(3);
        driver.findElement(By.xpath("//*[@id='menu-']/div[3]/ul/li[3]")).click();
        WaitUtility.waitSeconds(2);
        WebElement table = driver.findElement(By.xpath("//*[@class='MuiTable-root css-l6sbfr-MuiTable-root']"));

        WebElement row = table.findElements(By.xpath("//*[@id='view-table-id']/div/table/tbody/tr[1]")).get(0);
        boolean isLastRow = false;
        while (true) {
            for (int i = 1; i <= 50; i++) {
                for (int j = 2; j <= 8; j++) {
                    try {
                        WebElement cells = row.findElement(By.xpath("//*[@id='view-table-id']/div/table/tbody/tr[" + i + "]/td["+ j + "]"));
                        String strCellValue = cells.getText();
                        String intCellValue = strCellValue.replace("$", "").replace(",", "");
                        System.out.println("i- " + i + " j- " + j + " cell- " + intCellValue );
                        float num = Float.parseFloat(intCellValue);
                        sumList[j] += num;
                    } catch (NoSuchElementException ex) {
                        System.out.println("Last row reached");
                        isLastRow = true;
                        break;
                    }
                }
                if (isLastRow) {
                    break;
                }
            }
            System.out.println("Sum is : " + sumList.toString());
           if (isLastRow) {
                break;
            }
            WebElement nextPage = driver.findElement(By.xpath("//button[@data-testid='pagination-next']//*[@data-testid='ChevronRightIcon']"));
            actions.moveToElement(nextPage).click().build().perform();
            WaitUtility.waitSeconds(3);
        }
        return sumList;
    }


}
