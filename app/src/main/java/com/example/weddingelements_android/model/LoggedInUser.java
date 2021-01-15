package com.example.weddingelements_android.model;

import java.io.Serializable;

public class LoggedInUser implements Serializable {

    private String username,userRole,sec_key;

    public LoggedInUser(String username, String userRole, String key) {
        this.username = username;
        this.userRole = userRole;
        this.sec_key = sec_key;
    }

    public String getKey() {
        return sec_key;
    }

    public void setKey(String sec_key) {
        this.sec_key = sec_key;
    }

    public LoggedInUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
