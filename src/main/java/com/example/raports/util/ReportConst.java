package com.example.raports.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "report")
@Component
public class ReportConst {

    //Headers
    @Value("${report.tcc_issues_column_header}")
    private  String tccIssuesColumnHeader;
    @Value("${report.tcc_issues_column_header}")
    private  String statusColumnHeader;
    @Value("${report.tcc_time_column_header}")
    private String tccTimeColumnHeader;
    @Value("${report.type_request_column_header}")
    private String typeRequestColumnHeader;
    @Value("${report.sum_minutes_from_month_column_header}")
    private String sumMinutesFromMonthColumnHeader;
    @Value("${report.total_minutes_approved_for_request_column_header}")
    private String totalMinutesApprovedForRequestColumnHeader;
    @Value("${report.sum_md_month_column_header}")
    private String sumMdMonthColumnHeader;
    @Value("${report.total.sum_md_approved_for_request_column_header}")
    private String totalSumMdApprovedForRequestColumnHeader;
    @Value("${report.md_amount_worked_so_far_column_header}")
    private String mdAmountWorkedSoFarColumnHeader;
    @Value("${report.md_amount_worked_previous_month_column_header}")
    private String mdAmountWorkedPreviousMonthColumnHeader;
    @Value("${report.total_amount_min_worked_previous_month_column_header}")
    private String totalAmountMinWorkedPreviousMonthColumnHeader;
    @Value("${report.customer_colum_header}")
    private String customerColumHeader;
    @Value("${report.number.request_column_header}")
    private String numberRequestColumHeader;
    @Value("${report.number_request_digits_column_header}")
    private String numberRequestDigitsColumnHeader;



    //Conditions
    @Value("${report.status_column_value_resolved}")
    private  String statusColumnValueResolved;
    @Value("${report.status_column_value_closed}")
    private String statusColumnValueClosed;
    @Value("${report.status_column_value_service_request}")
    private String statusColumnValueServiceRequest;
    @Value("${report.dpd_fmp_u_value}")
    private String dpdFmpUValue;
    @Value("$report.monthly_time_update_component_name")
    private String monthlyTimeUpdateComponentName;

    //Error Messages
    @Value("${report.errorMessage.upload_file_error_message_no_found")
    private  String uploadFileErrorMessageNoFound;
    @Value("${report.error.upload_file_error_message_invalid_file}")
    private  String uploadFileErrorMessageInvalidFile;
    @Value("${report.error.can_not_set_column_index}")
    private String canNotSetColumnIndex;
    @Value("${report.error.time_value_not_retrieved}")
    private String timeValueNotRetrieved;

    //URLs
    @Value("${report.url_raport_tcc_time_value}")
    private  String urlRaportTccTimeValue;
    @Value("${report.temp_dir}")
    private String tempDir;
    @Value("${report.temp_file_name}")
    private String tempFileName;


    //Other
    @Value("${report.xpath_tcc_time}")
    private String xpathTccTime;

    public String getTccIssuesColumnHeader() {
        return tccIssuesColumnHeader;
    }

    public void setTccIssuesColumnHeader(String tccIssuesColumnHeader) {
        this.tccIssuesColumnHeader = tccIssuesColumnHeader;
    }

    public String getStatusColumnHeader() {
        return statusColumnHeader;
    }

    public void setStatusColumnHeader(String statusColumnHeader) {
        this.statusColumnHeader = statusColumnHeader;
    }

    public String getStatusColumnValueResolved() {
        return statusColumnValueResolved;
    }

    public void setStatusColumnValueResolved(String statusColumnValueResolved) {
        this.statusColumnValueResolved = statusColumnValueResolved;
    }

    public String getUploadFileErrorMessageNoFound() {
        return uploadFileErrorMessageNoFound;
    }

    public void setUploadFileErrorMessageNoFound(String uploadFileErrorMessageNoFound) {
        this.uploadFileErrorMessageNoFound = uploadFileErrorMessageNoFound;
    }

    public String getUploadFileErrorMessageInvalidFile() {
        return uploadFileErrorMessageInvalidFile;
    }

    public void setUploadFileErrorMessageInvalidFile(String uploadFileErrorMessageInvalidFile) {
        this.uploadFileErrorMessageInvalidFile = uploadFileErrorMessageInvalidFile;
    }

    public String getUrlRaportTccTimeValue() {
        return urlRaportTccTimeValue;
    }

    public void setUrlRaportTccTimeValue(String urlRaportTccTimeValue) {
        this.urlRaportTccTimeValue = urlRaportTccTimeValue;
    }

    public String getXpathTccTime() {
        return xpathTccTime;
    }

    public void setXpathTccTime(String xpathTccTime) {
        this.xpathTccTime = xpathTccTime;
    }

    public String getTempDir() {
        return tempDir;
    }

    public void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }

    public String getTempFileName() {
        return tempFileName;
    }

    public void setTempFileName(String tempFileName) {
        this.tempFileName = tempFileName;
    }

    public String getTccTimeColumnHeader() {
        return tccTimeColumnHeader;
    }

    public void setTccTimeColumnHeader(String tccTimeColumnHeader) {
        this.tccTimeColumnHeader = tccTimeColumnHeader;
    }

    public String getCanNotSetColumnIndex() {
        return canNotSetColumnIndex;
    }

    public void setCanNotSetColumnIndex(String canNotSetColumnIndex) {
        this.canNotSetColumnIndex = canNotSetColumnIndex;
    }

    public String getTimeValueNotRetrieved() {
        return timeValueNotRetrieved;
    }

    public void setTimeValueNotRetrieved(String timeValueNotRetrieved) {
        this.timeValueNotRetrieved = timeValueNotRetrieved;
    }

    public String getStatusColumnValueClosed() {
        return statusColumnValueClosed;
    }

    public void setStatusColumnValueClosed(String statusColumnValueClosed) {
        this.statusColumnValueClosed = statusColumnValueClosed;
    }

    public String getTypeRequestColumnHeader() {
        return typeRequestColumnHeader;
    }

    public void setTypeRequestColumnHeader(String typeRequestColumnHeader) {
        this.typeRequestColumnHeader = typeRequestColumnHeader;
    }

    public String getSumMinutesFromMonthColumnHeader() {
        return sumMinutesFromMonthColumnHeader;
    }

    public void setSumMinutesFromMonthColumnHeader(String sumMinutesFromMonthColumnHeader) {
        this.sumMinutesFromMonthColumnHeader = sumMinutesFromMonthColumnHeader;
    }

    public String getTotalMinutesApprovedForRequestColumnHeader() {
        return totalMinutesApprovedForRequestColumnHeader;
    }

    public void setTotalMinutesApprovedForRequestColumnHeader(String totalMinutesApprovedForRequestColumnHeader) {
        this.totalMinutesApprovedForRequestColumnHeader = totalMinutesApprovedForRequestColumnHeader;
    }

    public String getSumMdMonthColumnHeader() {
        return sumMdMonthColumnHeader;
    }

    public void setSumMdMonthColumnHeader(String sumMdMonthColumnHeader) {
        this.sumMdMonthColumnHeader = sumMdMonthColumnHeader;
    }

    public String getTotalSumMdApprovedForRequestColumnHeader() {
        return totalSumMdApprovedForRequestColumnHeader;
    }

    public void setTotalSumMdApprovedForRequestColumnHeader(String totalSumMdApprovedForRequestColumnHeader) {
        this.totalSumMdApprovedForRequestColumnHeader = totalSumMdApprovedForRequestColumnHeader;
    }

    public String getStatusColumnValueServiceRequest() {
        return statusColumnValueServiceRequest;
    }

    public void setStatusColumnValueServiceRequest(String statusColumnValueServiceRequest) {
        this.statusColumnValueServiceRequest = statusColumnValueServiceRequest;
    }

    public String getMdAmountWorkedSoFarColumnHeader() {
        return mdAmountWorkedSoFarColumnHeader;
    }

    public void setMdAmountWorkedSoFarColumnHeader(String mdAmountWorkedSoFarColumnHeader) {
        this.mdAmountWorkedSoFarColumnHeader = mdAmountWorkedSoFarColumnHeader;
    }

    public String getMdAmountWorkedPreviousMonthColumnHeader() {
        return mdAmountWorkedPreviousMonthColumnHeader;
    }

    public void setMdAmountWorkedPreviousMonthColumnHeader(String mdAmountWorkedPreviousMonthColumnHeader) {
        this.mdAmountWorkedPreviousMonthColumnHeader = mdAmountWorkedPreviousMonthColumnHeader;
    }


    public String getTotalAmountMinWorkedPreviousMonthColumnHeader() {
        return totalAmountMinWorkedPreviousMonthColumnHeader;
    }

    public void setTotalAmountMinWorkedPreviousMonthColumnHeader(String totalAmountMinWorkedPreviousMonthColumnHeader) {
        this.totalAmountMinWorkedPreviousMonthColumnHeader = totalAmountMinWorkedPreviousMonthColumnHeader;
    }

    public String getCustomerColumHeader() {
        return customerColumHeader;
    }

    public void setCustomerColumHeader(String customerColumHeader) {
        this.customerColumHeader = customerColumHeader;
    }

    public String getNumberRequestColumHeader() {
        return numberRequestColumHeader;
    }

    public void setNumberRequestColumHeader(String numberRequestColumHeader) {
        this.numberRequestColumHeader = numberRequestColumHeader;
    }

    public String getNumberRequestDigitsColumnHeader() {
        return numberRequestDigitsColumnHeader;
    }

    public void setNumberRequestDigitsColumnHeader(String numberRequestDigitsColumnHeader) {
        this.numberRequestDigitsColumnHeader = numberRequestDigitsColumnHeader;
    }

    public String getDpdFmpUValue() {
        return dpdFmpUValue;
    }

    public void setDpdFmpUValue(String dpdFmpUValue) {
        this.dpdFmpUValue = dpdFmpUValue;
    }

    public String getMonthlyTimeUpdateComponentName() {
        return monthlyTimeUpdateComponentName;
    }

    public void setMonthlyTimeUpdateComponentName(String monthlyTimeUpdateComponentName) {
        this.monthlyTimeUpdateComponentName = monthlyTimeUpdateComponentName;
    }
}
