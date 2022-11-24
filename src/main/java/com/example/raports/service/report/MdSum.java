package com.example.raports.service.report;

import com.example.raports.service.report.Configure.UserConfig;
import com.example.raports.util.ReportConst;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.raports.service.report.Configure.UserConfig.EnableProcesses.MD_SUM;

@Component
public class MdSum extends AbstractRowProcessor implements RowProcessor {

    int sumMdMonthColumnIndex = -1;
    int rowNumber;

    @Autowired
    private ReportConst reportConst;
    @Autowired
    private UserConfig userConfig;


    @Override
    public void processRow(Row row) {
    }


    private void addSummToMD(Sheet sheet) {
        Cell cell = sheet.getRow(0).getCell(sumMdMonthColumnIndex);
        String columnLetter = CellReference.convertNumToColString(cell.getColumnIndex());
        rowNumber = sheet.getLastRowNum();
        String formula = "SUM(" + columnLetter + 1 + ":" + columnLetter + rowNumber + ")";
        sheet.createRow(rowNumber + 1).createCell(sumMdMonthColumnIndex).setCellFormula(formula);

    }

    private void setFontStyle(Sheet sheet) {
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        CellStyle style = sheet.getWorkbook().createCellStyle();
        style.setFont(font);
        sheet.getRow(rowNumber + 1).getCell(sumMdMonthColumnIndex).setCellStyle(style);
    }


    @Override
    public void initialize(Sheet sheet) {
        try {
            sumMdMonthColumnIndex = getColumIndexByHeader(sheet.getRow(0), reportConst.getSumMdMonthColumnHeader());

        } catch (IllegalArgumentException e) {
            System.out.println(reportConst.getCanNotSetColumnIndex() + " " + e.getMessage());
        }

        addSummToMD(sheet);

        setFontStyle(sheet);
    }

    @Override
    public boolean isEnable() {
        return userConfig.getIsEnableMap().get(MD_SUM.getName());
    }

}

