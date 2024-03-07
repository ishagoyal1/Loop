package com.loop.assignment;

import com.loop.assignment.utility.Comparision;
import com.loop.assignment.page.*;
import main.java.com.loop.assignment.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

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
        //Comparision.isEqual(colSumList,grandTotalList);
         Assert.assertEquals(colSumList, grandTotalList);

         //Part II of assignment
//        TransactionPage transactions = new TransactionPage(driver);
//        transactions.navigateToTransactions();
//        transactions.applyFilters();
//        StoreTableInExcel storeTableInExcel = new StoreTableInExcel(driver);
//        storeTableInExcel.storeTable();


    }
}