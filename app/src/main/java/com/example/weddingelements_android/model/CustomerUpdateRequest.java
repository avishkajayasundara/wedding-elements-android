package com.example.weddingelements_android.model;

import java.io.Serializable;

public class CustomerUpdateRequest implements Serializable {
    String contactNo, address, password, email;

    public CustomerUpdateRequest(String contactNo, String address, String password, String email) {
        this.contactNo = contactNo;
        this.address = address;
        this.password = password;
        this.email = email;
    }

    public CustomerUpdateRequest() {
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
