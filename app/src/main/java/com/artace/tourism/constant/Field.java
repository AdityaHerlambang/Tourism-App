package com.artace.tourism.constant;

public class Field {

    public static final String TAG_PASSWORD = "password";
    public static final String TAG_USERNAME = "username";
    public static final String SESSION_STATUS = "session_status";
    public static final String LOGIN_SHARED_PREFERENCES = "login_shared_preferences";
    public static final String ID_USER = "id_user";



    public Field(){

    }

    public static String getTagPassword() {
        return TAG_PASSWORD;
    }

    public static String getIdUser() {
        return ID_USER;
    }

    public static String getTagUsername() {
        return TAG_USERNAME;
    }

    public static String getSessionStatus() {
        return SESSION_STATUS;
    }

    public static String getLoginSharedPreferences() {
        return LOGIN_SHARED_PREFERENCES;
    }

}