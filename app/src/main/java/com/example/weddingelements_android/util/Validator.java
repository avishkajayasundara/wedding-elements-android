package com.example.weddingelements_android.util;

public class Validator {

    public static boolean passwordValidator(String pw1, String pw2){
        if(pw1.equals(pw2))
            return true;
        else
            return false;
    }
}
