package com.loop.assignment.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StoreTableInCSV {

    private final WebDriver driver;

    public StoreTableInCSV(WebDriver driver) {
        this.driver = driver;
    }

    public void extractTableAndStoreInCSV(String filePath) {
        WebElement tableElement = driver.findElement(By.xpath("//*[@id='view-table-id']/div/table"));

        // Extract and store headers
        List<WebElement> headerCells = tableElement.findElements(By.tagName("th"));
        List<String> headers = new ArrayList<>(); //or number of columns
        for (WebElement headerCell : headerCells) {
            headers.add(headerCell.getText());
        }

        // Extract and store data rows
        List<WebElement> tableRows = tableElement.findElements(By.tagName("tr"));
        String[][] tableData = new String[tableRows.size()][headers.size()];

        for (int rowNum = 1; rowNum < tableRows.size(); rowNum++) { // Skip header row
            List<WebElement> dataCells = tableRows.get(rowNum).findElements(By.tagName("td"));
            for (int colNum = 0; colNum < dataCells.size(); colNum++) {
                String cellValue = dataCells.get(colNum).getText();
                String rowspanValue = dataCells.get(colNum).getAttribute("rowspan");
                System.out.println("cell at rowNum-" + rowNum + ", colNum-"+ colNum +" is " + cellValue);
                if (rowspanValue != null && !rowspanValue.isBlank()){
                    populateMergedCells(Integer.parseInt(rowspanValue),rowNum-1, colNum, tableData, cellValue);
                } else {
                    populateDataInRow(rowNum-1,tableData, cellValue);
                }
            }
        }
        dumpDataInCSV(filePath,headers,tableData);
    }

    private static void dumpDataInCSV(String outputPath, List<String> headers, String[][] tableData) {
        try (FileWriter writer = new FileWriter(outputPath)) {
            for (int i = 0; i < headers.size(); i++) {
                writer.append(escapeSpecialCharacters(headers.get(i)));
                if (i < headers.size() - 1) {
                    writer.append(",");
                }
            }
            writer.append("\n");
            for (int i = 0; i < tableData.length-1; i++) {
                for (int j = 0; j < tableData[i].length; j++) {
                    System.out.println("Content at table " + i + "," + j + " is " + tableData[i][j]);
                    writer.append(escapeSpecialCharacters(tableData[i][j]));
                    if (j < tableData[i].length - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String escapeSpecialCharacters(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            // If the value contains a comma, double quote, or newline, wrap it in double quotes
            return "\"" + value.replace("\"", "\"\"") + "\"";
        } else {
            // Otherwise, return the original value
            return value;
        }
    }
    private void populateMergedCells(int rowSpan, int rowIndex, int columnIndex, String[][] tableData, String cellData) {
        for (int i = rowIndex; i < rowIndex + rowSpan; i++) {
            tableData[i][columnIndex] = cellData;
        }
    }
    private void populateDataInRow(int rowIndex, String[][] tableData, String cellData) {
        for (int i = 0; i < tableData[rowIndex].length; i++) {
            if(tableData[rowIndex][i]==null){
                tableData[rowIndex][i] = cellData;
                break;
            }
        }
    }
}

