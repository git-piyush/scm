package com.scm.controllers;

import com.scm.entities.Contact;
import com.scm.entities.Resume;
import com.scm.repsitories.ContactRepo;
import com.scm.services.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {

   // @Autowired
    private final ReportService reportService;

    @Autowired
    private ContactRepo contactRepo;

    @Value("${properties.jrxmlPath}")
    private String jrxmlPath;

    @Autowired
    private ResourceLoader resourceLoader;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/contacts")
    public ResponseEntity<byte[]> getContactsReport() throws Exception {
        // Sample Data
        List<Contact> contacts = contactRepo.findAll();

        // Generate PDF
        byte[] pdf = reportService.exportContactsReport(contacts, new HashMap<>());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contacts.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/resume")
    public ResponseEntity<byte[]> resume(HttpServletResponse response) throws Exception {
        Resume resume = new Resume("Piyush Kumar",
                "Aryan Fountain Square, Attibele, Bangalore, Karnataka, 562107",
                "krpiyush51@gmail.com",
                "+91-9686722968",
                "To seek the challenging position in Software industry that needs innovation, creativity, dedication and enable\n" +
                "me to continue to work in a challenging and fast paced environment, leveraging my current knowledge and\n" +
                "fostering creativity with many learning opportunities.");

        List<Resume> data = Collections.singletonList(resume);

        InputStream reportStream = loadResumeReport();
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);
        byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contacts.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);

    }


    public InputStream loadResumeReport() throws Exception {
        // For classpath resource:
        // Resource resource = resourceLoader.getResource("classpath:reports/contactlist.jrxml");

        // For file system path:
        Resource resource = resourceLoader.getResource("file:D:/scm2_files/jrxml/resume.jrxml");
        return resource.getInputStream();
    }
}
