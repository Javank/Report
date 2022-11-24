package com.example.raports.service.report;


import com.example.raports.service.report.Configure.UserConfig;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(5)
public class LockColumnAndRow implements RowProcessor {

    @Autowired
    private UserConfig userConfig;

    @Override
    public void processRow(Row row) {
    }

    @Override
    public void initialize(Sheet sheet) {
        sheet.createFreezePane(3, 1);
    }

    @Override
    public boolean isEnable() {
        return true;
    }
}
