package com.javaPlayground.IO_Operations;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import java.io.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/*
*    Keeps memory usage constant (streaming mode)
*    Uses three threads (reader, writer, and progress monitor)
*    Communicates safely through a BlockingQueue
*    Reports real-time progress using atomic counters
*    Can process datasets of millions of rows efficiently
*    Automatically cleans up resources after finishing
*/

public class CSVtoExcelMultithreaded {

    private static final int ROWS_IN_MEMORY = 1000;
    private static final int QUEUE_CAPACITY = 5000;
    private static final String POISON_PILL = "END_OF_FILE";
    private static final int PROGRESS_INTERVAL = 5000; // every 5k rows

    private static final AtomicLong linesRead = new AtomicLong(0);
    private static final AtomicLong linesWritten = new AtomicLong(0);

    public static void main(String[] args) {
        String csvFile = "src/main/resources/IO_operations/large_dataset.csv";
        String excelFile = "src/main/resources/IO_operations/report/large_dataset_excel_multithreaded.xlsx";

        BlockingQueue<String> queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);

        ExecutorService executor = Executors.newFixedThreadPool(3); // 1 producer, 1 consumer, 1 monitor

        executor.submit(() -> readCSV(csvFile, queue));
        executor.submit(() -> writeExcel(excelFile, queue));
        executor.submit(() -> monitorProgress());

        executor.shutdown();
    }

    // Producer thread
    private static void readCSV(String csvFile, BlockingQueue<String> queue) {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                queue.put(line);
                long count = linesRead.incrementAndGet();
                if (count % PROGRESS_INTERVAL == 0) {
                    System.out.println("[Reader] Lines read: " + count);
                }
            }
            queue.put(POISON_PILL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Consumer thread
    private static void writeExcel(String excelFile, BlockingQueue<String> queue) {
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(ROWS_IN_MEMORY);
             FileOutputStream fileOut = new FileOutputStream(excelFile)) {

            SXSSFSheet sheet = workbook.createSheet("Employee Data");
            sheet.trackAllColumnsForAutoSizing(); // needed for SXSSF

            CreationHelper createHelper = workbook.getCreationHelper();

            // --- Styles ---
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle salaryStyle = workbook.createCellStyle();
            DataFormat format = createHelper.createDataFormat();
            salaryStyle.setDataFormat(format.getFormat("#,##0.00"));

            CellStyle numberStyle = workbook.createCellStyle();
            numberStyle.setDataFormat(format.getFormat("0"));

            int rowNum = 0;
            while (true) {
                String line = queue.take();
                if (POISON_PILL.equals(line)) break;

                String[] values = line.split(",");
                Row row = sheet.createRow(rowNum++);

                for (int i = 0; i < values.length; i++) {
                    Cell cell = row.createCell(i);
                    String value = values[i].trim();

                    if (rowNum == 1) {
                        cell.setCellValue(value);
                        cell.setCellStyle(headerStyle);
                    } else {
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

                if (rowNum % 5000 == 0) {
                    sheet.flushRows(100); // free memory
                }

                long written = linesWritten.incrementAndGet();
                if (written % PROGRESS_INTERVAL == 0) {
                    System.out.println("[Writer] Lines written: " + written);
                }
            }

            for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(fileOut);
            System.out.println("✅ Excel file created successfully at: " + excelFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Monitor thread (optional but nice for long tasks)
    private static void monitorProgress() {
        try {
            while (true) {
                Thread.sleep(5000);
                long read = linesRead.get();
                long written = linesWritten.get();
                System.out.printf("[Progress] Read: %,d | Written: %,d%n", read, written);
                if (read == written && read > 0) {
                    System.out.println("✅ Processing complete.");
                    break;
                }
            }
        } catch (InterruptedException ignored) {}
    }
}
