package com.example.raports.controller;

import com.example.raports.service.ReportService;
import com.example.raports.service.report.Configure.UserConfig;
import com.example.raports.util.ReportConst;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Piotr
 */
@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private ReportService reportService;
    @Autowired
    private ReportConst reportConst;

    @Autowired
    UserConfig userConfig;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("userConfig", new UserConfig());
        return "home";
    }

    @PostMapping(value = "/uploadFile", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> uploadFile(@RequestParam("file") MultipartFile file, @ModelAttribute("userConfig") UserConfig newUserConfig) {

        userConfig.setIsEnableMap(newUserConfig.getIsEnableMap());


        String fileName = file.getOriginalFilename();
        if (getExtension(fileName).equals("XLSX") || getExtension(fileName).equals("XLS")) {
            XSSFWorkbook workbook = reportService.processFile(file);
            reportService.writeWorkbook(workbook);
        }
        Resource resource = new ClassPathResource("temp/" + reportConst.getTempFileName());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + reportConst.getTempFileName() + "\"");
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    public static String getExtension(String fileName) {
        String ext = null;
        int i = fileName.lastIndexOf('.');
        if (i > 0 && i < fileName.length() - 1) {
            ext = fileName.substring(i + 1).toUpperCase();
        }
        return ext;
    }
}
