package com.example.weddingelements_android.model;

public class LoggedInUser {
    private static LoggedInUser instance;
    private String username,userRole;

    private LoggedInUser(String username, String userRole) {
        this.username = username;
        this.userRole = userRole;
    }

    public static LoggedInUser getInstance() {
        if(instance ==null){
            instance = new LoggedInUser("aa@gmail.com","BUSINESS_OWNER");
        }
        return instance;
    }

    public static void setInstance(LoggedInUser instance) {
        LoggedInUser.instance = instance;
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
