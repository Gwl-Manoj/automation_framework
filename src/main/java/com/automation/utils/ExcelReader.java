package com.automation.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Excel Reader Utility
 * Reads data from Excel files for data-driven testing
 */
public class ExcelReader {
    private static final Logger logger = LoggerFactory.getLogger(ExcelReader.class);

    /**
     * Read data from Excel file
     * @param filePath Path to Excel file
     * @param sheetName Sheet name to read
     * @return List of maps containing row data (key=column name, value=cell value)
     */
    public static List<Map<String, String>> readExcelData(String filePath, String sheetName) {
        List<Map<String, String>> dataList = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                logger.error("Sheet '{}' not found in file: {}", sheetName, filePath);
                return dataList;
            }

            // Get header row (first row)
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                logger.error("Header row not found in sheet: {}", sheetName);
                return dataList;
            }

            // Get column names from header row
            List<String> columnNames = new ArrayList<>();
            for (Cell cell : headerRow) {
                columnNames.add(getCellValueAsString(cell));
            }

            // Read data rows
            int rowCount = sheet.getPhysicalNumberOfRows();
            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, String> rowData = new LinkedHashMap<>();
                for (int j = 0; j < columnNames.size(); j++) {
                    Cell cell = row.getCell(j);
                    String cellValue = getCellValueAsString(cell);
                    rowData.put(columnNames.get(j), cellValue);
                }

                // Only add non-empty rows
                if (!rowData.isEmpty() && rowData.values().stream().anyMatch(value -> !value.isEmpty())) {
                    dataList.add(rowData);
                }
            }

            logger.info("Read {} rows from sheet '{}' in file: {}", dataList.size(), sheetName, filePath);

        } catch (IOException e) {
            logger.error("Failed to read Excel file: {}", filePath, e);
        }

        return dataList;
    }

    /**
     * Read data from Excel file (first sheet)
     * @param filePath Path to Excel file
     * @return List of maps containing row data
     */
    public static List<Map<String, String>> readExcelData(String filePath) {
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            String sheetName = sheet.getSheetName();
            return readExcelData(filePath, sheetName);

        } catch (IOException e) {
            logger.error("Failed to read Excel file: {}", filePath, e);
            return Collections.emptyList();
        }
    }

    /**
     * Read single cell value from Excel
     * @param filePath Path to Excel file
     * @param sheetName Sheet name
     * @param rowNum Row number (0-based)
     * @param colNum Column number (0-based)
     * @return Cell value as string
     */
    public static String getCellValue(String filePath, String sheetName, int rowNum, int colNum) {
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                logger.error("Sheet '{}' not found", sheetName);
                return "";
            }

            Row row = sheet.getRow(rowNum);
            if (row == null) {
                logger.error("Row {} not found", rowNum);
                return "";
            }

            Cell cell = row.getCell(colNum);
            return getCellValueAsString(cell);

        } catch (IOException e) {
            logger.error("Failed to read cell value from file: {}", filePath, e);
            return "";
        }
    }

    /**
     * Get cell value as string
     * @param cell Cell to read
     * @return Cell value as string
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    // Format date cell
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    return dateFormat.format(cell.getDateCellValue());
                } else {
                    // Format numeric cell
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == Math.floor(numericValue)) {
                        return String.valueOf((long) numericValue);
                    } else {
                        return String.valueOf(numericValue);
                    }
                }

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case FORMULA:
                return cell.getCellFormula();

            case BLANK:
            default:
                return "";
        }
    }

    /**
     * Get all sheet names from Excel file
     * @param filePath Path to Excel file
     * @return List of sheet names
     */
    public static List<String> getSheetNames(String filePath) {
        List<String> sheetNames = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                sheetNames.add(workbook.getSheetName(i));
            }

        } catch (IOException e) {
            logger.error("Failed to read sheet names from file: {}", filePath, e);
        }

        return sheetNames;
    }

    /**
     * Get row count from a sheet
     * @param filePath Path to Excel file
     * @param sheetName Sheet name
     * @return Number of rows
     */
    public static int getRowCount(String filePath, String sheetName) {
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheet(sheetName);
            return sheet != null ? sheet.getPhysicalNumberOfRows() : 0;

        } catch (IOException e) {
            logger.error("Failed to get row count from file: {}", filePath, e);
            return 0;
        }
    }

    /**
     * Get column count from a sheet
     * @param filePath Path to Excel file
     * @param sheetName Sheet name
     * @return Number of columns
     */
    public static int getColumnCount(String filePath, String sheetName) {
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) return 0;

            Row headerRow = sheet.getRow(0);
            return headerRow != null ? headerRow.getPhysicalNumberOfCells() : 0;

        } catch (IOException e) {
            logger.error("Failed to get column count from file: {}", filePath, e);
            return 0;
        }
    }

    /**
     * Write data to Excel file
     * @param filePath Path to Excel file
     * @param sheetName Sheet name
     * @param data List of maps containing data to write
     */
    public static void writeExcelData(String filePath, String sheetName, List<Map<String, String>> data) {
        if (data.isEmpty()) {
            logger.warn("No data to write to Excel file");
            return;
        }

        Workbook workbook;
        File file = new File(filePath);

        try {
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                workbook = new XSSFWorkbook(fileInputStream);
                fileInputStream.close();
            } else {
                workbook = new XSSFWorkbook();
            }

            // Remove existing sheet if it exists
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet != null) {
                workbook.removeSheetAt(workbook.getSheetIndex(sheet));
            }

            // Create new sheet
            sheet = workbook.createSheet(sheetName);

            // Get column names from first row of data
            Set<String> columnNames = data.get(0).keySet();

            // Create header row
            Row headerRow = sheet.createRow(0);
            int colIndex = 0;
            for (String columnName : columnNames) {
                Cell cell = headerRow.createCell(colIndex++);
                cell.setCellValue(columnName);
            }

            // Create data rows
            int rowIndex = 1;
            for (Map<String, String> rowData : data) {
                Row row = sheet.createRow(rowIndex++);
                colIndex = 0;
                for (String columnName : columnNames) {
                    Cell cell = row.createCell(colIndex++);
                    cell.setCellValue(rowData.getOrDefault(columnName, ""));
                }
            }

            // Write to file
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                workbook.write(fileOutputStream);
                workbook.close();
            }

            logger.info("Data written to Excel file: {}", filePath);

        } catch (IOException e) {
            logger.error("Failed to write data to Excel file: {}", filePath, e);
        }
    }
}