package com.loop.assignment;

import com.loop.assignment.page.*;
import com.loop.assignment.utility.ComparisonUtil;
import main.java.com.loop.assignment.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ConfigReader configReader = new ConfigReader();
        String username = configReader.getUsername();
        String password = configReader.getPassword();
        String website = configReader.getWebsite();

        OpenBrowser openBrowser = new OpenBrowser();
        WebDriver driver = openBrowser.openWebsiteInChrome(website);
        LoginPage login = new LoginPage(driver);
        login.authenticationService(username, password);
        //Part I of assignment
        StoreHistoryPage historyByStore = new StoreHistoryPage(driver);
        historyByStore.navigateToHistoryByStore();
        float[] colSumList = historyByStore.extractSumOfColumns();
        float[] grandTotalList = historyByStore.extractGrandTotal();
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("Value after summing up the columns : ");
        for (float v : colSumList) {
            System.out.print(df.format(v) + ", ");
        }
        System.out.println("\nGrand Total value : ");
        for (float v : grandTotalList) {
            System.out.print(df.format(v) + ", ");
        }
        Assert.assertTrue(ComparisonUtil.isEqual(colSumList,grandTotalList));
         //Part II of assignment
        TransactionPage transactions = new TransactionPage(driver);
        transactions.navigateToTransactions();
        transactions.applyFilters();
        StoreTableInCSV storeTableInExcel = new StoreTableInCSV(driver);
        storeTableInExcel.extractTableAndStoreInCSV(configReader.getExcelSavedPath());

    }
}