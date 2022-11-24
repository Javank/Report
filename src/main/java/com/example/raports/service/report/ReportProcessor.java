package com.example.raports.service.report;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public interface ReportProcessor {
    /**
     * Method iterates over the workbook
     *
     * @param workbook  a workbook object
     */
    void processReport(XSSFWorkbook workbook);
}
