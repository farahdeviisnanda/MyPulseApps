package com.dev.farah.mypulseapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by FARAH on 18/11/2016.
 */
public class ResponseCategori {
    @SerializedName("id_categori")
    @Expose
    private int id_categori;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("nominal")
    @Expose
    public String nominal;
    @SerializedName("kode_provider")
    @Expose
    public String kodeProvider;
    @SerializedName("harga_pusat")
    @Expose
    public String hargaPusat;

    public ResponseCategori(){}

    public ResponseCategori(int id_categori, String nama, String nominal, String kodeProvider, String hargaPusat){
        this.id_categori    = id_categori;
        this.nama           = nama;
        this.nominal        = nominal;
        this.kodeProvider   = kodeProvider;
        this.hargaPusat     = hargaPusat;
    }

    public void setId(int id){
        this.id_categori = id;
    }

    public void setName(String nama){
        this.nama = nama;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public void setKodeProvider(String kodeProvider) {
        this.kodeProvider = kodeProvider;
    }

    public void setHargaPusat(String hargaPusat) {
        this.hargaPusat = hargaPusat;
    }

    public int getId(){
        return this.id_categori;
    }

    public String getName(){
        return this.nama;
    }

    public String getNominal() {return nominal;}
    public String getKodeProvider() {
        return kodeProvider;
    }
    public String getHargaPusat() {
        return hargaPusat;
    }
}
