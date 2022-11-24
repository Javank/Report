package com.example.raports.service;

import com.example.raports.service.report.RowIterator;
import com.example.raports.util.ReportConst;
import lombok.AllArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Piotr Powszuk
 */
@Service
@AllArgsConstructor
public class ReportService {

    @Autowired
    private final RowIterator rowIterator;

    @Autowired
    private ReportConst reportConst;

    /**
     * Method the method get a file for processing
     *
     * @param file the file that will be processed
     * @return workbook
     */
    public XSSFWorkbook processFile(MultipartFile file) {
        XSSFWorkbook workbook = getWorkbook(file);
        rowIterator.processReport(workbook);
        return workbook;
    }


    public XSSFWorkbook getWorkbook(MultipartFile file) {
        try (InputStream in = file.getInputStream()) {
            XSSFWorkbook workbook = new XSSFWorkbook(in);
            return workbook;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Method creates a new file and writ to him updated value from workbook
     *
     * @param workbook procesing document to write
     */
    public void writeWorkbook(XSSFWorkbook workbook) {
        try(FileOutputStream out = new FileOutputStream(reportConst.getTempDir() + reportConst.getTempFileName())){
            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
