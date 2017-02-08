package com.dev.farah.mypulseapps.model;

/**
 * Created by isdzulqor on 10/23/16.
 */

public class ResponseSignup {

    private String message, username;
    private boolean isError;

    public ResponseSignup(){}

    public ResponseSignup(String message, String username, boolean isError) {
        this.message = message;
        this.username = username;
        this.isError = isError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }


}
