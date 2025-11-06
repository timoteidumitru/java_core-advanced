package com.javaPlayground.IO_Operations;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;

public class CSVtoExcelConverter {

    public static void main(String[] args) {
        String csvFile = "src/main/resources/IO_operations/large_dataset.csv";
        String excelFile = "src/main/resources/IO_operations/report/large_dataset_excel.xlsx";

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile));
             Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(excelFile)) {

            Sheet sheet = workbook.createSheet("Employee Data");
            CreationHelper createHelper = workbook.getCreationHelper();

            // --- Define cell styles ---
            // Header style (bold, centered)
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Numeric style for Salary with 2 decimals
            CellStyle salaryStyle = workbook.createCellStyle();
            DataFormat format = createHelper.createDataFormat();
            salaryStyle.setDataFormat(format.getFormat("#,##0.00"));

            // Regular numeric style (for Age, ID)
            CellStyle numberStyle = workbook.createCellStyle();
            numberStyle.setDataFormat(format.getFormat("0"));

            String line;
            int rowNum = 0;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                Row row = sheet.createRow(rowNum);

                for (int i = 0; i < values.length; i++) {
                    Cell cell = row.createCell(i);
                    String value = values[i].trim();

                    // Apply header style to the first row
                    if (rowNum == 0) {
                        cell.setCellValue(value);
                        cell.setCellStyle(headerStyle);
                    } else {
                        // Handle numeric fields
                        if (i == 0 || i == 2) { // ID or Age
                            try {
                                cell.setCellValue(Integer.parseInt(value));
                                cell.setCellStyle(numberStyle);
                            } catch (NumberFormatException e) {
                                cell.setCellValue(value);
                            }
                        } else if (i == 3) { // Salary
                            try {
                                cell.setCellValue(Double.parseDouble(value));
                                cell.setCellStyle(salaryStyle);
                            } catch (NumberFormatException e) {
                                cell.setCellValue(value);
                            }
                        } else {
                            cell.setCellValue(value);
                        }
                    }
                }

                rowNum++;
            }

            // Auto-size columns
            for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(fileOut);
            System.out.println("Excel file created successfully with formatting at: report/large_dataset_excel.xlsx");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
