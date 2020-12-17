package com.example.weddingelements_android.model;

import java.io.Serializable;

public class Customer extends SystemUser implements Serializable {
    private String firstName;
    private String lastName;
    private String dob;
    private String gender;

    public Customer(String email, String password, String address, String contactNo, String userRole, String status, String firstName, String lastName, String dob, String gender) {
        super(email, password, address, contactNo, userRole, status);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
    }

    public Customer(String firstName, String lastName, String dob, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
    }

    public Customer(String email, String password, String address, String contactNo, String userRole, String status) {
        super(email, password, address, contactNo, userRole, status);
    }

    public Customer() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
