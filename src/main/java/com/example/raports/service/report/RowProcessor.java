package com.example.raports.service.report;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public interface RowProcessor {
    void processRow(Row row);

    /**
     * Initialize sheet
     * @param sheet initialize sheet for each processor
     */
    void initialize(Sheet sheet);

    /**
     * Sets the  flag which component should be invoked
     */
    boolean isEnable();

}
