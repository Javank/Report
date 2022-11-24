package com.example.raports.service.report;

import com.example.raports.util.ReportConst;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 *
 * @author Piotr Powszuk
 */
@Component()
@Order(1)
@Slf4j
public class TCCTimeUpdate extends AbstractRowProcessor implements RowProcessor {
    int tccIssueColumIndex = -1;
    int statusColumnIndex = -1;
    int tccTimeColumnIndex = -1;

    @Autowired
    private ReportConst reportConst;

    @Override
    public void processRow(Row row) {

        findTCCIssues(row);
    }

    /**
     * Method sets the TCC Time value  from the method getTimeIssues
     *
     * @param row gets the current row
     */
    private void findTCCIssues(Row row) {
        Pattern pattern = Pattern.compile("\\d+");
        Cell cell = row.getCell(tccIssueColumIndex);
        if (cell != null && !cell.getStringCellValue().equals("") && cell.getCellType() != CellType.BLANK && cell.getColumnIndex() == tccIssueColumIndex) {
            String satusColumn = row.getCell(statusColumnIndex).getStringCellValue().toString();
            if (satusColumn.equals(reportConst.getStatusColumnValueResolved()) || satusColumn.equals(reportConst.getStatusColumnValueClosed())) {
                int indexRow = cell.getRowIndex();
                Matcher matcher = pattern.matcher(cell.getStringCellValue());
                int tccTime = 0;
                while (matcher.find()) {
                    log.info("Cell " + (tccIssueColumIndex + 1) + " Row " + (indexRow + 1) + " Value = " + matcher.group());
                    try {
                        int time = getTimeIssues(Integer.parseInt(matcher.group()));
                        tccTime = tccTime + time;
                    } catch (IllegalArgumentException | IOException e) {
                        System.out.println(reportConst.getTimeValueNotRetrieved() + " " + e.getLocalizedMessage());
                    }

                }
                if (tccTime > 0) {
                    log.info("Before: " + row.getCell(tccTimeColumnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    row.getCell(tccTimeColumnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(tccTime);
                    log.info("After: " + row.getCell(tccTimeColumnIndex).getNumericCellValue());
                }
            }
        }
    }

    /**
     * Method get TCC Time from web
     *
     * @param tccIssue the value based on which it will get tcc time
     * @return tccTime
     */
    private int getTimeIssues(int tccIssue) throws IllegalArgumentException, IOException {
        int tccTime = -1;
        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);
        try {
            HtmlPage page = client.getPage(reportConst.getUrlRaportTccTimeValue() + tccIssue);
            DomText domText = page.getFirstByXPath(reportConst.getXpathTccTime());
            if (domText == null) {
                throw new IllegalArgumentException();
            }
            return tccTime = Integer.parseInt(domText.getNodeValue());
        } catch (IOException e) {
            throw new IOException();
        }
    }

    @Override
    public void initialize(Sheet sheet) {
        try {
            tccIssueColumIndex = getColumIndexByHeader(sheet.getRow(0), reportConst.getTccIssuesColumnHeader());
            statusColumnIndex = getColumIndexByHeader(sheet.getRow(0), reportConst.getStatusColumnHeader());
            tccTimeColumnIndex = getColumIndexByHeader(sheet.getRow(0), reportConst.getTccTimeColumnHeader());

        }catch (IllegalArgumentException e){
            System.out.println(reportConst.getCanNotSetColumnIndex() +" " + e.getMessage());
        }
    }

    @Override
    public boolean isEnable() {
        return true;
    }
}
