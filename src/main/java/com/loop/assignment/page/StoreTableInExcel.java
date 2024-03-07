package com.loop.assignment.page;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class StoreTableInExcel {

    private final WebDriver driver;

    public StoreTableInExcel(WebDriver driver) {
        this.driver = driver;
    }


    public void storeTableInExcel(String filePath) {
        WebElement tableElement = driver.findElement(By.xpath("//*[@id='view-table-id']/div/table"));

        // Create an empty Excel workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Extracted Transaction Excel");

        // Extract and write headers
        List<WebElement> headerCells = tableElement.findElements(By.tagName("th"));
        List<String> headers = new ArrayList<>(); //or number of columns
        for (WebElement headerCell : headerCells) {
            headers.add(headerCell.getText());
        }

        dumpHeadersInExcel(sheet, headers, 0);
        // Extract and write data rows
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
        dumpDataInExcel(sheet,tableData);
        // Save the Excel file
        try {
            workbook.write(new FileOutputStream((
                    new File(filePath)
            )));
            System.out.println("Data extracted and saved to extracted_table_data.xlsx");
        } catch (Exception e) {
            File previousFile = new File("extracted_table_data.xlsx");
            // Check if the previous file exists and delete it
            if (previousFile.exists()) {
                previousFile.delete();
                System.out.println("Previous file deleted: " + previousFile.getName());
            }
            System.err.println("Error saving Excel file: " + e.getMessage());
        }
    }

    private static void dumpHeadersInExcel(XSSFSheet sheet, List<String> headers, int rowNum) {
        Row headerRow = sheet.createRow(rowNum);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i));  // Assuming headers have text
        }
    }

    private static void dumpDataInExcel(XSSFSheet sheet, String[][] tableData) {
        for (int i = 1; i <= tableData.length; i++) {
            Row sheetRow = sheet.createRow(i);
            for (int j = 0; j < tableData[0].length; j++) {
                Cell cell = sheetRow.createCell(j);
                cell.setCellValue(tableData[i-1][j]);
            }
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

