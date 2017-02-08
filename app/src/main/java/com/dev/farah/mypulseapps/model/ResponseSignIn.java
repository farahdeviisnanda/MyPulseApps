package com.dev.farah.mypulseapps.model;

/**
 * Created by isdzulqor on 10/23/16.
 */
public class ResponseSignIn {
    private String message, username;
    private boolean isError;
    private int id_user;

    public ResponseSignIn(){}

    public ResponseSignIn(int id_user,String message, String username, boolean isError) {
        this.message = message;
        this.username = username;
        this.isError = isError;
        this.id_user = id_user;
    }

    public int getIdUser() {
        return id_user;
    }

    /**
     *
     * @param idUser
     *     The idUser
     */
    public void setIdUser(int idUser) {
        this.id_user = idUser;
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
