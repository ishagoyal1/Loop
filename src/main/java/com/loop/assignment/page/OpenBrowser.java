package com.loop.assignment.page;

import com.loop.assignment.utility.WaitUtility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class OpenBrowser {

    public WebDriver openWebsiteInChrome(String website) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        WebDriver driver = new ChromeDriver();
        WaitUtility.waitSeconds(2);
        driver.get(website);
        WaitUtility.waitSeconds(2);
        driver.manage().window().maximize();
        WaitUtility.waitSeconds(4);
        return driver;
    }

}
