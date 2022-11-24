package com.example.raports.service.report;

import com.example.raports.service.report.Configure.UserConfig;
import com.example.raports.util.ReportConst;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.raports.service.report.Configure.UserConfig.EnableProcesses.FMP_ISSUE_NUMBER;


@Component
@Order(6)
public class FmpIssueNumber extends AbstractRowProcessor implements RowProcessor {

    int customerColumIndex = -1;
    int numberRequestColumIndex = -1;
    int numberRequestDigitsColumnIndex = -1;

    @Autowired
    private ReportConst reportConst;
    @Autowired
    private UserConfig userConfig;

    @Override
    public void processRow(Row row) {
        Cell cellCustomer = row.getCell(customerColumIndex);
        if (cellCustomer.getColumnIndex() == customerColumIndex && cellCustomer.getStringCellValue().equals(reportConst.getDpdFmpUValue())) {
            Cell cellDigit = row.getCell(numberRequestDigitsColumnIndex);
            Pattern pattern = Pattern.compile("(?<number>\\d+$)", Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(cellDigit.getStringCellValue());
            while (matcher.find()) {
                row.getCell(numberRequestColumIndex).setCellValue(matcher.group(0));
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
    }

    @Override
    public boolean isEnable() {
        return userConfig.getIsEnableMap().get(FMP_ISSUE_NUMBER.getName());
    }
}
