package com.example.weddingelements_android.model;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    private String username;
    private String password;
    private String instanceId;
    private String sec_key;

    public LoginRequest(String username, String password, String instanceId, String sec_key) {
        this.username = username; 
        this.password = password;
        this.instanceId = instanceId;
        this.sec_key = sec_key;
    }

    public LoginRequest() {
    }

    public String getKey() {
        return sec_key;
    }

    public void setKey(String sec_key) {
        this.sec_key = sec_key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
}
