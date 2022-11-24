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

import static com.example.raports.service.report.Configure.UserConfig.EnableProcesses.CREATE_SHEET_FMP;

@Component
@Order(7)
@Slf4j
public class CreateSheetFMP extends AbstractRowProcessor implements RowProcessor {

    int customerColumIndex = -1;
    int numberRequestColumIndex = -1;
    int numberRequestDigitsColumnIndex = -1;

    @Autowired
    private ReportConst reportConst;

    @Autowired
    private UserConfig userConfig;

    Sheet newSheet = null;

    @Override
    public void processRow(Row row) {
        deleteRow(row);
    }

    /**
     * Method delete unnecessary rows in old sheet
     *
     * @param row gets the current row
     */
    private void deleteMainRow(Row row) {
        Cell cellCustomer = row.getCell(customerColumIndex);
        if (cellCustomer.getColumnIndex() == customerColumIndex && cellCustomer.getStringCellValue().equals(reportConst.getDpdFmpUValue())) {
            row.getSheet().removeRow(row.getSheet().getRow(row.getRowNum()));
        }
    }

    /**
     * Method delete unnecessary rows in new sheet
     *
     * @param row gets the current row
     */
    private void deleteRow(Row row) {
        Cell cellCustomer = row.getCell(customerColumIndex);
        if (cellCustomer.getColumnIndex() == customerColumIndex) {
            if (!cellCustomer.getStringCellValue().equals(reportConst.getDpdFmpUValue())) {
                newSheet.removeRow(newSheet.getRow(row.getRowNum()));
            }
        }
    }

    /**
     * Method copy sheet and create new
     *
     * @param sheet gets the current sheet
     */
    private void copySheet(Sheet sheet) {
        try {
            newSheet = sheet.getWorkbook().cloneSheet(0);
            newSheet.setSelected(true);
            newSheet.getWorkbook().setSheetName(2, "FMPv1");
        } catch (Exception e) {
            log.error("Sheet copy errore" + e.getMessage());
        }
    }

    /**
     * Method remove empoty  rows
     *
     * @param sheet gets the current sheet
     */
    private void removeEmptyRows(Sheet sheet) {
        Boolean isRowEmpty = Boolean.FALSE;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            if (sheet.getRow(i) == null) {
                isRowEmpty = true;
                sheet.shiftRows(i + 1, sheet.getLastRowNum() + 1, -1);
                i--;
                continue;
            }
            for (int j = 0; j < sheet.getRow(i).getLastCellNum(); j++) {
                if (sheet.getRow(i).getCell(j) == null || sheet.getRow(i).getCell(j).toString().trim().equals("")) {
                    isRowEmpty = true;
                } else {
                    isRowEmpty = false;
                    break;
                }
            }
            if (isRowEmpty == true) {
                sheet.shiftRows(i + 1, sheet.getLastRowNum() + 1, -1);
                i--;
            }
        }
    }

    @Override
    public void initialize(Sheet sheet) {
        try {
            customerColumIndex = getColumIndexByHeader(sheet.getRow(0), reportConst.getCustomerColumHeader());
            numberRequestDigitsColumnIndex = getColumIndexByHeader(sheet.getRow(0), reportConst.getNumberRequestDigitsColumnHeader());
            numberRequestColumIndex = getColumIndexByHeader(sheet.getRow(0), reportConst.getNumberRequestColumHeader());
        } catch (IllegalArgumentException e) {
            System.out.println(reportConst.getCanNotSetColumnIndex() + " " + e.getMessage());
        }
        copySheet(sheet);
    }

    @Override
    public boolean isEnable() {
        return userConfig.getIsEnableMap().get(CREATE_SHEET_FMP.getName());
    }
}
