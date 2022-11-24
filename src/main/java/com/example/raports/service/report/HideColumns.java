package com.example.raports.service.report;

import com.example.raports.service.report.Configure.UserConfig;
import com.example.raports.util.ReportConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.example.raports.service.report.Configure.UserConfig.EnableProcesses.HIDE_COLUMNS;

/**
 * @author Piotr Powszuk
 */
@Component
@Order(6)
@Slf4j
public class HideColumns extends AbstractRowProcessor implements RowProcessor {

    int tccIssueColumIndex = -1;
    int tccTimeColumnIndex = -1;
    int sumMinutesFromMonthColumnIndex = -1;
    int totalMinutesApprovedForRequestColumnIndex = -1;
    int totalAmountMinWorkedPreviousMonthColumnHeader = -1;

    @Autowired
    private ReportConst reportConst;
    @Autowired
    private UserConfig userConfig;

    @Override
    public void processRow(Row row) {
    }

    /**
     * Method hide columns
     *
     * @param cell
     */
    private void hideColumns(Cell cell) {
        cell.getSheet().setColumnHidden(cell.getColumnIndex(), true);
    }

    /**
     * Method find cell to hide
     *
     * @param cell
     */
    private boolean ColumnMatches(Cell cell) {
        int index = cell.getColumnIndex();
        if (index == tccIssueColumIndex || index == tccTimeColumnIndex || index == sumMinutesFromMonthColumnIndex || index == totalMinutesApprovedForRequestColumnIndex
                || index == totalAmountMinWorkedPreviousMonthColumnHeader) {
            return true;
        }
        return false;
    }

    @Override
    public void initialize(Sheet sheet) {
        try {
            tccIssueColumIndex = getColumIndexByHeader(sheet.getRow(0), reportConst.getTccIssuesColumnHeader());
            tccTimeColumnIndex = getColumIndexByHeader(sheet.getRow(0), reportConst.getTccTimeColumnHeader());
            sumMinutesFromMonthColumnIndex = getColumIndexByHeader(sheet.getRow(0), reportConst.getSumMinutesFromMonthColumnHeader());
            totalMinutesApprovedForRequestColumnIndex = getColumIndexByHeader(sheet.getRow(0), reportConst.getTotalMinutesApprovedForRequestColumnHeader());
            totalAmountMinWorkedPreviousMonthColumnHeader = getColumIndexByHeader(sheet.getRow(0), reportConst.getTotalAmountMinWorkedPreviousMonthColumnHeader());
        } catch (IllegalArgumentException e) {
            System.out.println(reportConst.getCanNotSetColumnIndex() + " " + e.getMessage());
        }

        sheet.getRow(0).forEach(cell -> {
            if (ColumnMatches(cell)) {
                hideColumns(cell);
            }
        });
    }

    @Override
    public boolean isEnable() {
        return userConfig.getIsEnableMap().get(HIDE_COLUMNS.getName());
    }
}
