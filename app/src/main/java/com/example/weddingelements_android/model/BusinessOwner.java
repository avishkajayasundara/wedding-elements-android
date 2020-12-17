package com.example.weddingelements_android.model;

public class BusinessOwner extends SystemUser {
    private String name;
    private String businessType;
    private String description;
    private String country;

    public BusinessOwner(String email, String password, String address, String contactNo, String userRole, String status, String name, String businessType, String description, String country) {
        super(email, password, address, contactNo, userRole, status);
        this.name = name;
        this.businessType = businessType;
        this.description = description;
        this.country = country;
    }

    public BusinessOwner(String name, String businessType, String description, String country) {
        this.name = name;
        this.businessType = businessType;
        this.description = description;
        this.country = country;
    }

    public BusinessOwner() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
