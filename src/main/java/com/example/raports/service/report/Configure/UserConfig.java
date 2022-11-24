package com.example.raports.service.report.Configure;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
/**
 * @author Piotr Powszuk
 */
@Component
public class UserConfig {

    private Map<String, Boolean> isEnableMap = new HashMap<>();

    /**
     * Constructor set name cheackboxes
     */
    public UserConfig() {
        isEnableMap.put(EnableProcesses.MONTHLY_TIME_UPDATE.getName(), false);
        isEnableMap.put(EnableProcesses.MD_SUM.getName(), false);
        isEnableMap.put(EnableProcesses.HIDE_COLUMNS.getName(), false);
        isEnableMap.put(EnableProcesses.LOCK_COLUMN_AND_ROW.getName(), false);
        isEnableMap.put(EnableProcesses.FORMATING_HEADER_AND_CELL.getName(), false);
        isEnableMap.put(EnableProcesses.CREATE_SHEET_FMP.getName(), false);
        isEnableMap.put(EnableProcesses.CREATE_COLUMNS_OVER_WORKED_MD.getName(), false);
        isEnableMap.put(EnableProcesses.FMP_ISSUE_NUMBER.getName(), false);
    }

    public Map<String, Boolean> getIsEnableMap() {
        return isEnableMap;
    }

    public void setIsEnableMap(Map<String, Boolean> isEnableMap) {
        this.isEnableMap = isEnableMap;
    }

    public enum EnableProcesses {
        MONTHLY_TIME_UPDATE("Monthly time update"), MD_SUM("MD Sum"), HIDE_COLUMNS("Hide columns"), LOCK_COLUMN_AND_ROW("Lock columns and row"),
        FORMATING_HEADER_AND_CELL("Formating header and cell"), CREATE_SHEET_FMP("Create sheet FMP"), CREATE_COLUMNS_OVER_WORKED_MD("Create column MD"),
        FMP_ISSUE_NUMBER("FMP issue number");

        private final String name;

        EnableProcesses(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
