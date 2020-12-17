package com.example.weddingelements_android.model;

import java.io.Serializable;

public class Inquiry implements Serializable {
    private String inquiryId;
    private String name;
    private String email;
    private String contactNo;
    private String subject;
    private String message;

    public Inquiry(String inquiryId, String name, String email, String contactNo, String subject, String message) {
        this.inquiryId = inquiryId;
        this.name = name;
        this.email = email;
        this.contactNo = contactNo;
        this.subject = subject;
        this.message = message;
    }

    public Inquiry() {
    }

    public String getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(String inquiryId) {
        this.inquiryId = inquiryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
