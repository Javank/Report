package com.example.raports.service.report;

import com.example.raports.service.report.Configure.UserConfig;
import com.example.raports.util.ReportConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.example.raports.service.report.Configure.UserConfig.EnableProcesses.CREATE_COLUMNS_OVER_WORKED_MD;


@Component
@Order(3)
@Slf4j
public class CreateColumnOverworkedMD extends AbstractRowProcessor implements RowProcessor {

    int mdAmountWorkedPreviousMonthColumIndex = -1;
    int mdAmountWorkedSoFarColumnIndex = -1;
    int sumMdMonthColumnIndex = -1;

    @Autowired
    private ReportConst reportConst;
    @Autowired
    private UserConfig userConfig;

    private XSSFWorkbook workbook;

    @Override
    public void processRow(Row row) {
        addValueToNewColumn(row);
    }

    /**
     * Method  count the amount worked so far and add value o new column
     *
     * @param row  iterates rows
     */
    private void addValueToNewColumn(Row row) {

        double mdAmountWorked = row.getCell(mdAmountWorkedSoFarColumnIndex).getNumericCellValue();
        double sumMDMount = row.getCell(sumMdMonthColumnIndex).getNumericCellValue();
        row.createCell(mdAmountWorkedSoFarColumnIndex + 1).setCellValue(mdAmountWorked + sumMDMount);
    }

    /**
     * Method insert new column in sheet
     *
     * @param sheetIndex  sheet index
     * @param columnIndex column index
     */
    public void insertNewColumn(int sheetIndex, int columnIndex) {
        assert workbook != null;

        FormulaEvaluator evaluator = workbook.getCreationHelper()
                .createFormulaEvaluator();
        evaluator.clearAllCachedResultValues();

        Sheet sheet = workbook.getSheetAt(sheetIndex);
        int nrRows = getNumberOfRows(sheetIndex);
        int nrCols = getNrColumns(sheetIndex);

        for (int row = 0; row < nrRows; row++) {
            Row r = sheet.getRow(row);

            if (r == null) {
                continue;
            }

            for (int col = nrCols;
                 col > columnIndex;
                 col--) {
                Cell rightCell = r.getCell(col);
                if (rightCell != null) {
                    r.removeCell(rightCell);
                }

                Cell leftCell = r.getCell(col - 1);

                if (leftCell != null) {
                    Cell newCell = r.createCell(col, leftCell.getCellType());
                    cloneCell(newCell, leftCell);
                }
            }
            CellType cellType = CellType.BLANK;
            r.createCell(columnIndex, cellType);
        }
        XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
    }


    private static void cloneCell(Cell cNew, Cell cOld) {
        cNew.setCellComment(cOld.getCellComment());
        cNew.setCellStyle(cOld.getCellStyle());

        switch (cOld.getCellType()) {
            case BOOLEAN: {
                cNew.setCellValue(cOld.getBooleanCellValue());
                break;
            }
            case NUMERIC: {
                cNew.setCellValue(cOld.getNumericCellValue());
                break;
            }
            case STRING: {
                cNew.setCellValue(cOld.getStringCellValue());
                break;
            }
            case ERROR: {
                cNew.setCellValue(cOld.getErrorCellValue());
                break;
            }
            case FORMULA: {
                cNew.setCellFormula(cOld.getCellFormula());
                break;
            }
        }
    }


    /**
     * Method get number of rows
     *
     * @param sheetIndex  sheet index
     */
    public int getNumberOfRows(int sheetIndex) {
        assert workbook != null;
        int sheetNumber = workbook.getNumberOfSheets();

        if (sheetIndex >= sheetNumber) {
            throw new RuntimeException("Sheet index " + sheetIndex
                    + " invalid, we have " + sheetNumber + " sheets");
        }

        Sheet sheet = workbook.getSheetAt(sheetIndex);
        int rowNum = sheet.getLastRowNum() + 1;
        System.out.println("Found " + rowNum + " rows.");
        return rowNum;
    }


    /**
     * Method get number of columns
     *
     * @param sheetIndex  sheet index
     */
    public int getNrColumns(int sheetIndex) {
        assert workbook != null;

        Sheet sheet = workbook.getSheetAt(sheetIndex);
        Row headerRow = sheet.getRow(0);
        int nrCol = headerRow.getLastCellNum();
        System.out.println("Found " + nrCol + " columns.");
        return nrCol;
    }

    @Override
    public void initialize(Sheet sheet) {
        try {
            mdAmountWorkedPreviousMonthColumIndex = getColumIndexByHeader(sheet.getRow(0), reportConst.getMdAmountWorkedPreviousMonthColumnHeader());
            mdAmountWorkedSoFarColumnIndex = getColumIndexByHeader(sheet.getRow(0), reportConst.getMdAmountWorkedSoFarColumnHeader());
            sumMdMonthColumnIndex = getColumIndexByHeader(sheet.getRow(0), reportConst.getSumMdMonthColumnHeader());

            workbook = (XSSFWorkbook) sheet.getWorkbook();
            insertNewColumn(0, mdAmountWorkedSoFarColumnIndex + 1);
            sheet.getRow(0).createCell(mdAmountWorkedSoFarColumnIndex + 1).setCellValue(reportConst.getMdAmountWorkedPreviousMonthColumnHeader());

        } catch (IllegalArgumentException e) {
            System.out.println(reportConst.getCanNotSetColumnIndex() + " " + e.getMessage());
        }
    }

    @Override
    public boolean isEnable() {
        return userConfig.getIsEnableMap().get(CREATE_COLUMNS_OVER_WORKED_MD.getName());
    }
}
