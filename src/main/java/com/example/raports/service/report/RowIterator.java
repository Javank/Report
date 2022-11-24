package com.example.raports.service.report;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * @author Piotr Powszuk
 */
@Component
public class RowIterator implements ReportProcessor {
    @Autowired
    List<RowProcessor> rowProcessorList;


    @Override
    public void processReport(XSSFWorkbook workbook) {
        for (RowProcessor rowProcessor : rowProcessorList) {
            Sheet sheet = workbook.getSheetAt(0);
            if (rowProcessor.isEnable()) {
                rowProcessor.initialize(sheet);
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    if (row.getRowNum() > 0) {
                        rowProcessor.processRow(row);
                    }
                }
            }
        }
    }
}
