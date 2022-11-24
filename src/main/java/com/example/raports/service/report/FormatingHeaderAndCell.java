package com.example.raports.service.report;

import com.example.raports.service.report.Configure.UserConfig;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.example.raports.service.report.Configure.UserConfig.EnableProcesses.FORMATING_HEADER_AND_CELL;

@Component
@Order(5)
public class FormatingHeaderAndCell implements RowProcessor {

    @Autowired
    private UserConfig userConfig;

    @Override
    public void processRow(Row row) {
        for (int i = 1; i < row.getSheet().getLastRowNum() + 1; i++) {
            row.getSheet().autoSizeColumn(i, false);
        }
    }

    @Override
    public void initialize(Sheet sheet) {
        sheet.getRow(0).setHeightInPoints((48));
        sheet.getWorkbook().setSheetName(0, "IT4EM");
        CellStyle wrap = sheet.getWorkbook().createCellStyle();
        wrap.setWrapText(true);
        sheet.getRow(0).setRowStyle(wrap);
    }

    @Override
    public boolean isEnable() {
        return userConfig.getIsEnableMap().get(FORMATING_HEADER_AND_CELL.getName());
    }
}
