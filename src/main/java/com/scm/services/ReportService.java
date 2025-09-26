package com.scm.services;

import com.scm.entities.Contact;
import com.scm.entities.Resume;
import org.apache.xmlbeans.impl.xb.xmlconfig.Extensionconfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ReportService {

    public byte[] exportContactsReport(List<Contact> contacts, Map<String, Object> params) throws Exception;

    public byte[] exportResume(Resume resume, Map<String, Object> params) throws Exception;
}
