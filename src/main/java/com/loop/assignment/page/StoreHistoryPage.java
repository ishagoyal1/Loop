package com.loop.assignment.page;

import com.loop.assignment.utility.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StoreHistoryPage {
    private final WebDriver driver;

    public StoreHistoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHistoryByStore() {
        WaitUtil.waitSeconds(5);
        driver.findElement(By.xpath("//span[contains(text(),'3P Chargebacks')]")).click();
        WaitUtil.waitSeconds(3);
        driver.findElement(By.xpath("//span[contains(text(),'History by Store')]")).click();

    }

    public float[] extractSumOfColumns() {
        WaitUtil.waitSeconds(15);
        float[] sumList = null;

        WebElement element = driver.findElement(By.xpath
                ("//*[@class='MuiFormControl-root css-1nrlq1o-MuiFormControl-root']//div[@aria-haspopup='listbox']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();

        WaitUtil.waitSeconds(5);
        driver.findElement(By.xpath("//*[@id='menu-']/div[3]/ul/li[3]")).click();
        WaitUtil.waitSeconds(2);
        WebElement table = driver.findElement(By.xpath("//*[@class='MuiTable-root css-l6sbfr-MuiTable-root']"));
        String endPageText = driver.findElement(By.xpath("//h6[@class='MuiTypography-root MuiTypography-subtitle2 css-11mtq93-MuiTypography-root'][2]")).getText();
        int totalRows = extractNumberAfterOf(endPageText);
        System.out.println("endPageText is : " + totalRows);
        int rowProcessed = 0;
        while (true) {
            List<WebElement> rowList = table.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
            for (int i = 0; i < rowList.size() - 2 && rowProcessed < totalRows; i++) {
                WebElement row = rowList.get(i);
                List<WebElement> cellList = row.findElements(By.tagName("td"));
                if (sumList == null) {
                    sumList = new float[cellList.size()-2];
                }
                for (int j = 1; j < cellList.size() - 1; j++) {
                    WebElement cell = cellList.get(j);
                    String strCellValue = cell.getText();
                    if(strCellValue == null || strCellValue.isBlank()){
                        continue;
                    }
                    String intCellValue = strCellValue.replace("$", "").replace(",", "");
                    System.out.println("i- " + i + " j- " + j + " cell- " + intCellValue);
                    float num = Float.parseFloat(intCellValue);
                    sumList[j - 1] += num;
                }
                rowProcessed++;
            }
            for (int i = 0; i < sumList.length; i++) {
                System.out.println("Sum is : " + sumList[i]);
            }
            if (rowProcessed == (totalRows - 1)) {
                break;
            }
            WebElement nextPage = driver.findElement(By.xpath("//button[@data-testid='pagination-next']//*[@data-testid='ChevronRightIcon']"));
            actions.moveToElement(nextPage).click().build().perform();
            WaitUtil.waitSeconds(3);
        }
        return sumList;
    }

    public float[] extractGrandTotal() {
        WebElement table = driver.findElement(By.xpath("//*[@class='MuiTable-root css-l6sbfr-MuiTable-root']"));
        List<WebElement> rowList = table.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        WebElement lastRow = rowList.getLast();
        List<WebElement> cellList = lastRow.findElements(By.tagName("td"));
        float[] totalList = new float[cellList.size()-2];
        for (int j = 1; j < cellList.size() - 2; j++) {
            WebElement cell = cellList.get(j);
            String strCellValue = cell.getText();
            String intCellValue = strCellValue.replace("$", "").replace(",", "");
            totalList[j-1] = Float.parseFloat(intCellValue);

        }
        return totalList;
    }

    public static int extractNumberAfterOf(String input) {
        // Define a pattern to match numbers after the word "of"
        Pattern pattern = Pattern.compile("of (\\d+)");

        // Create a matcher with the input string
        Matcher matcher = pattern.matcher(input);

        // Find the first match
        if (matcher.find()) {
            // Extract and parse the matched number
            return Integer.parseInt(matcher.group(1));
        }

        // Return a default value or handle the case when no number is found
        return -1; // or throw an exception, return a special value, etc.
    }

}
