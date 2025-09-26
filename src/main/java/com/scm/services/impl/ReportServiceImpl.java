package com.scm.services.impl;

import com.scm.entities.Contact;
import com.scm.entities.Resume;
import com.scm.services.ReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ResourceLoader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Value("${properties.jrxmlPath}")
    private String jrxmlPath;

    private final ResourceLoader resourceLoader;

    public ReportServiceImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public byte[] exportContactsReport(List<Contact> contacts, Map<String, Object> params) throws Exception {
        // Load .jrxml file
        System.out.println("dfg");
        InputStream reportStream = loadReport();

        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // Data source
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(contacts);

        // Fill report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

        // Export to PDF
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    @Override
    public byte[] exportResume(Resume resume, Map<String, Object> params) throws Exception {
        return new byte[0];
    }

    public InputStream loadReport() throws Exception {
        // For classpath resource:
        // Resource resource = resourceLoader.getResource("classpath:reports/contactlist.jrxml");

        // For file system path:
        Resource resource = resourceLoader.getResource("file:D:/scm2_files/jrxml/contactlist.jrxml");
        return resource.getInputStream();
    }

}
