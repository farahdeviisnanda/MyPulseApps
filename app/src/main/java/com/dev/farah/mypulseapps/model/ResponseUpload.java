package com.dev.farah.mypulseapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by isdzulqor on 11/8/16.
 */

public class ResponseUpload {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("date_sale")
    @Expose
    private String date_sale;

    @SerializedName("jenis_provider")
    @Expose
    private String jenis_provider;

    @SerializedName("phone_number")
    @Expose
    private String phone_number;

    @SerializedName("nominal")
    @Expose
    private String nominal;

    @SerializedName("harga_jual")
    @Expose
    private String harga_jual;

    private String message;
    private boolean error;

    public ResponseUpload(){}

    public ResponseUpload(int id, String date_sale, String phone_number, String nominal,String name, String harga_jual, String message, boolean error){
        this.id = id;
        this.date_sale = date_sale;
        this.phone_number = phone_number;
        this.nominal    = nominal;
        this.jenis_provider=name;
        this.harga_jual = harga_jual;
        this.message = message;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String nama){
        this.date_sale = nama;
    }
    public void setPhone_number(String phone_number){
        this.phone_number = phone_number;
    }

    public int getId(){
        return this.id;
    }
    public String getName(){return this.jenis_provider;}

    public String getDate_sale(){return this.date_sale;}

    public String getPhone_number() {return phone_number;}

    public String getHarga_jual() {
        return harga_jual;
    }

    public String getNominal() {
        return nominal;
    }
}
