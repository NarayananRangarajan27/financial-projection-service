package com.finofo.financialprojection.service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class FinancialProjectionService {

    @Value("${balance.sheet.index}")
    int sheetIndex;

    public List<Map<String, Object>> calculateFinancialProjections(MultipartFile file, Map<String, Double> growthRates, int futureMonths) throws IOException {
        List<Map<String, Object>> projections = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(sheetIndex); // Provide the sheet index where data resides in the Excel

            // Map to store the row index for each category
            Map<String, Integer> categoryRowMap = new HashMap<>();
            categoryRowMap.put("Product Sales", -1);
            categoryRowMap.put("Service Sales", -1);
            categoryRowMap.put("Cost of Goods Sold", -1);
            categoryRowMap.put("Marketing", -1);
            categoryRowMap.put("Staff salaries", -1);

            // Scanning the sheet to find the row indices for each category
            for (int r = 0; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row != null) {
                    Cell cell = row.getCell(0);
                    if (cell != null && cell.getCellType() == CellType.STRING) {
                        String cellValue = cell.getStringCellValue().trim();
                        if (categoryRowMap.containsKey(cellValue)) {
                            categoryRowMap.put(cellValue, r);
                        }
                    }
                }
            }

            // Finding the last month and year from the month row. This is being done so that we can calculate projections for future months based upon input criteria
            int monthRowIndex = findMonthRow(sheet);
            if (monthRowIndex == -1) {
                throw new IllegalArgumentException("Month row not found in the Excel sheet.");
            }
            Row monthRow = sheet.getRow(monthRowIndex);
            int lastMonthColIndex = findLastMonthColumn(monthRow);
            if (lastMonthColIndex == -1) {
                throw new IllegalArgumentException("Last month column not found in the Excel sheet.");
            }
            LocalDate lastDate = monthRow.getCell(lastMonthColIndex).getLocalDateTimeCellValue().toLocalDate();

            // Parse data for each category
            Map<String, Double> lastValues = new HashMap<>();
            for (String category : categoryRowMap.keySet()) {
                int rowIndex = categoryRowMap.get(category);
                if (rowIndex != -1) {
                    lastValues.put(category, sheet.getRow(rowIndex).getCell(lastMonthColIndex).getNumericCellValue());
                }
            }

            // Calculating projections for each future month
            for (int month = 1; month <= futureMonths; month++) {
                Map<String, Object> monthProjection = new HashMap<>();
                LocalDate futureDate = lastDate.plusMonths(month);
                String futureMonthYear = futureDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + futureDate.getYear();
                monthProjection.put("Month", futureMonthYear);

                double totalSales = 0.0;
                double totalOperatingExpenses = 0.0;

                for (String category : lastValues.keySet()) {
                    double growthRate = growthRates.getOrDefault(category, 0.0); // This should be in decimal form
                    lastValues.put(category, lastValues.get(category) * (1 + growthRate)); // calculating the growth rate
                    monthProjection.put(category, lastValues.get(category));

                    if (category.equals("Product Sales") || category.equals("Service Sales")) {
                        totalSales += lastValues.get(category);
                    } else if (category.equals("Cost of Goods Sold") || category.equals("Marketing") || category.equals("Staff salaries")) {
                        totalOperatingExpenses += lastValues.get(category);
                    }
                }

                double netIncome = totalSales - totalOperatingExpenses;
                monthProjection.put("Total Sales", totalSales);
                monthProjection.put("Total Operating Expenses", totalOperatingExpenses);
                monthProjection.put("Net Income", netIncome);

                projections.add(monthProjection);
            }
        }
        return projections;
    }

    private int findMonthRow(Sheet sheet) {
        for (int r = 0; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row != null) {
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                        return r;
                    }
                }
            }
        }
        return -1; // Default if not found
    }

    private int findLastMonthColumn(Row monthRow) {
        for (int c = monthRow.getLastCellNum() - 1; c >= 0; c--) {
            Cell cell = monthRow.getCell(c);
            if (cell != null && cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                return c;
            }
        }
        return -1; // Default if not found
    }
}