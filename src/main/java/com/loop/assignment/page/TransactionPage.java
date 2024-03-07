package com.loop.assignment.page;

import com.loop.assignment.utility.WaitUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class TransactionPage {
    private final WebDriver driver;

    public TransactionPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToTransactions() {
        WaitUtility.waitSeconds(3);
        driver.findElement(By.xpath("//span[contains(text(),'Transactions')]")).click();

    }
    public void applyFilters(){
        WaitUtility.waitSeconds(3);
        Actions actions = new Actions(driver);
        WebElement clickLocation = driver.findElement(By.xpath("//*[@data-testid='MuseumIcon']"));
        actions.moveToElement(clickLocation).click().build().perform();
        WaitUtility.waitSeconds(2);
        WebElement clear = driver.findElement(By.xpath("//*[contains(text(),'Clear')]"));
        actions.moveToElement(clear).click().build().perform();
        driver.findElement(By.xpath("//*[contains(text(),'Artisan Alchemy')]")).click();
        WaitUtility.waitSeconds(1);
        driver.findElement(By.xpath("//*[contains(text(),'Blissful Buffet')]")).click();
        driver.findElement(By.xpath("//*[@data-testid='applyBtn']")).click();
        WaitUtility.waitSeconds(3);
        WebElement clickMarketplace = driver.findElement(By.xpath("//*[contains(@class,'MuiSvgIcon-root MuiSvgIcon-fontSizeMedium css-12ksora')]"));
        actions.moveToElement(clickMarketplace).click().build().perform();
        WaitUtility.waitSeconds(2);
        WebElement clear2 = driver.findElement(By.xpath("//*[contains(text(),'Clear')]"));
        actions.moveToElement(clear2).click().build().perform();
        driver.findElement(By.xpath("//*[contains(text(),'Grubhub')]")).click();
        WaitUtility.waitSeconds(2);
        driver.findElement(By.xpath("//*[@data-testid='applyBtn']")).click();
        WaitUtility.waitSeconds(2);
        WebElement rowsPerPage = driver.findElement(By.xpath("//div[@class='MuiFormControl-root css-1nrlq1o-MuiFormControl-root']//div[@aria-haspopup='listbox']"));
        actions.moveToElement(rowsPerPage).click().build().perform();
        driver.findElement(By.xpath("//*[@id='menu-']/div[3]/ul/li[3]")).click();

    }


}
