package com.example.raports.service.report;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


public abstract class AbstractRowProcessor implements RowProcessor {

    int getColumIndexByHeader(Row row, String headerName) throws IllegalArgumentException {
        for (Cell cell : row) {
            switch (cell.getCellType()) {
                case STRING:
                case NUMERIC:
                    try {
                        if (headerName.equals(cell.getStringCellValue())) {
                            return cell.getColumnIndex();
                        }
                        break;
                    } catch (Exception e) {
                        throw new IllegalArgumentException();
                    }
            }
        }
        return -1;
    }
}