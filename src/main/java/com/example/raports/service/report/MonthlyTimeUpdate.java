package com.example.raports.service.report;

import com.example.raports.service.report.Configure.UserConfig;
import com.example.raports.util.ReportConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.example.raports.service.report.Configure.UserConfig.EnableProcesses.MONTHLY_TIME_UPDATE;

@Component
@Order(2)
@Slf4j
public class MonthlyTimeUpdate extends AbstractRowProcessor implements RowProcessor {

    int tccTimeColumnIndex = -1;
    int typeRequestColumnIndex = -1;
    int sumMinutesFromMonthColumnIndex = -1;
    int totalMinutesApprovedForRequestColumnIndex = -1;
    int sumMdMonthColumnIndex = -1;
    int totalSumMdApprovedForRequestColumnIndex = -1;
    int statusColumnIndex = -1;

    @Autowired
    private ReportConst reportConst;
    @Autowired
    private UserConfig userConfig;

    @Override
    public void processRow(Row row) {
        updatedSumOfMinutes(row);
    }

    private void updatedSumOfMinutes(Row row) {
        Cell cellTccTime = row.getCell(tccTimeColumnIndex);
        Cell cellType = row.getCell(typeRequestColumnIndex);
        Cell cellStatus = row.getCell(statusColumnIndex);

        if (cellTccTime != null && cellTccTime.getCellType() != CellType.BLANK && cellType.getStringCellValue().equals(reportConst.getStatusColumnValueServiceRequest())
                && checkStatusCondition(cellStatus)) {
            updatedSumMinMounth(row, cellTccTime, sumMinutesFromMonthColumnIndex);
            updatedSumMinMounth(row, cellTccTime, totalMinutesApprovedForRequestColumnIndex);
            countSumMD(row, sumMinutesFromMonthColumnIndex, sumMdMonthColumnIndex);
            countSumMD(row, totalMinutesApprovedForRequestColumnIndex, totalSumMdApprovedForRequestColumnIndex);
        }
    }
    /**
     * Method update sum of min in mounth
     *
     * @param row  row
     * @param cellTccTime  cell value
     * @param updatedColumnIndex  column index to update
     */
    private void updatedSumMinMounth(Row row, Cell cellTccTime, int updatedColumnIndex) {
        log.info("Row " + (row.getRowNum() + 1) + " " + row.getCell(updatedColumnIndex).getNumericCellValue() + " + " + cellTccTime.getNumericCellValue() + " = " + (int) (row.getCell(updatedColumnIndex).getNumericCellValue() + cellTccTime.getNumericCellValue()));
        row.getCell(updatedColumnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(row.getCell(updatedColumnIndex).getNumericCellValue() + cellTccTime.getNumericCellValue());
    }

    /**
     * Method count MD
     *
     * @param row  row
     * @param cellValue  cell value
     * @param updatedMDColumnIndex  column index to update
     */
    private void countSumMD(Row row, int cellValue, int updatedMDColumnIndex) {
        log.info("Count MD " + (row.getRowNum() + 1) + " Cellvalue " + cellValue + " Update Column " + updatedMDColumnIndex);
        double countValue = ((row.getCell(cellValue).getNumericCellValue() / 60) / 8);
        log.info("Count = " + countValue);
        row.getCell(updatedMDColumnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(countValue);
        CellStyle style = row.getSheet().getWorkbook().createCellStyle();
        DataFormat format = row.getSheet().getWorkbook().createDataFormat();
        style.setDataFormat(format.getFormat("0.00"));
        row.getCell(updatedMDColumnIndex).setCellStyle(style);
    }

    private boolean checkStatusCondition(Cell cell) {
        if (cell.getStringCellValue().equals(reportConst.getStatusColumnValueResolved()) || cell.getStringCellValue().equals(reportConst.getStatusColumnValueClosed())) {
            return true;
        }
        return false;
    }

    @Override
    public void initialize(Sheet sheet) {
            try {
                tccTimeColumnIndex = getColumIndexByHeader(sheet.getRow(0), reportConst.getTccTimeColumnHeader());
                statusColumnIndex = getColumIndexByHeader(sheet.getRow(0), reportConst.getStatusColumnHeader());
                typeRequestColumnIndex = getColumIndexByHeader(sheet.getRow(0), reportConst.getTypeRequestColumnHeader());
                sumMinutesFromMonthColumnIndex = getColumIndexByHeader(sheet.getRow(0), reportConst.getSumMinutesFromMonthColumnHeader());
                totalMinutesApprovedForRequestColumnIndex = getColumIndexByHeader(sheet.getRow(0), reportConst.getTotalMinutesApprovedForRequestColumnHeader());
                sumMdMonthColumnIndex = getColumIndexByHeader(sheet.getRow(0), reportConst.getSumMdMonthColumnHeader());
                totalSumMdApprovedForRequestColumnIndex = getColumIndexByHeader(sheet.getRow(0), reportConst.getTotalSumMdApprovedForRequestColumnHeader());
                statusColumnIndex = getColumIndexByHeader(sheet.getRow(0), reportConst.getStatusColumnHeader());

            } catch (IllegalArgumentException e) {
                System.out.println(reportConst.getCanNotSetColumnIndex() + " " + e.getMessage());
            }
    }

    @Override
    public boolean isEnable() {
       return userConfig.getIsEnableMap().get(MONTHLY_TIME_UPDATE.getName());
    }
}
