package com.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;

public class ExcelUtil {

    @DataProvider(name = "excelLoginData")
    public static Object[][] getExcelData(){
        String workSheet = "src/main/resources/LoginData.xlsx";
        String dataSheet = "loginData";
        try{

            FileInputStream fis = new FileInputStream(workSheet);
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheet(dataSheet);

            if(sheet == null){
                throw new RuntimeException("Sheet "+dataSheet+" is empty...");
            }

            int rows = sheet.getPhysicalNumberOfRows();
            int cols = sheet.getRow(0).getPhysicalNumberOfCells();

            Object[][] data = new Object[rows-1][cols];

            DataFormatter formatter = new DataFormatter();
            for(int i=1; i < rows; i++){
                for(int j=0; j < cols; j++){
                    data[i-1][j] = formatter.formatCellValue(
                        sheet.getRow(i).getCell(j));
                }
            }

            workbook.close();
            fis.close();

            return data;

        }catch(IOException e){
            e.printStackTrace();
            throw new RuntimeException("Failed to read Excel file.", e);
        }

    }

}
