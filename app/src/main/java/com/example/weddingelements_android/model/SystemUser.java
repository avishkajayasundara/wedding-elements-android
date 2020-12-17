package com.example.weddingelements_android.model;

public class SystemUser {
    private String email;
    private String password;
    private String address;
    private String contactNo;
    private String userRole;
    private String status;

    public SystemUser(String email, String password, String address, String contactNo, String userRole, String status) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.contactNo = contactNo;
        this.userRole = userRole;
        this.status = status;
    }

    public SystemUser() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
