package com.dev.farah.mypulseapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by farah on 12/5/16.
 */

public class ReactUser {
    @SerializedName("id_user")
    @Expose
    private int id_user;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;

    /**
     *
     * @return
     *     The idUser
     */
    public int getIdUser() {
        return id_user;
    }

    /**
     *
     * @param id_user
     *     The idUser
     */
    public void setIdUser(int id_user) {
        this.id_user = id_user;
    }

    /**
     *
     * @return
     *     The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     *     The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     *     The password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     *     The password
     */
    public void setPassword(String password) {
        this.password = password;
    }


}
