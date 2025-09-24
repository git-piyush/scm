package com.scm.forms;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class MailForm {

    private String id;

    private String toMail;

    private String contactId;

    private String subject;

    private String message;

}
