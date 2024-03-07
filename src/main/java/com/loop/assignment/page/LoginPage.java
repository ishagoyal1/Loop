package com.loop.assignment.page;

import com.loop.assignment.utility.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

private final WebDriver driver;

public LoginPage(WebDriver driver){
    this.driver = driver;
}

    public void authenticationService(String username, String password) {
        driver.findElement(By.xpath("//a[@href='/login/password']")).click();
    WaitUtil.waitSeconds(2);
    driver.findElement(By.id(":r2:")).sendKeys(username);
    driver.findElement(By.id(":r3:")).sendKeys(password);
    driver.findElement(By.xpath("//*[contains(@data-testid,'LoginIcon')]")).click();
    WaitUtil.waitSeconds(2);

    }
}
