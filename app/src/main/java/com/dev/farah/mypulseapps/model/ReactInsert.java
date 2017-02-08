package com.dev.farah.mypulseapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by toshiba on 12/5/2016.
 */

public class ReactInsert {

    @SerializedName("id_user")
    @Expose
    public int id_user;
    @SerializedName("day")
    @Expose
    public String day;
    @SerializedName("month")
    @Expose
    public String month;
    @SerializedName("year")
    @Expose
    public String year;
    @SerializedName("phone_number")
    @Expose
    public String phone_number;
    @SerializedName("nominal")
    @Expose
    public String nominal;
    @SerializedName("jenis_provider")
    @Expose
    public String jenisProvider;
    @SerializedName("harga_asli")
    @Expose
    public String harga_asli;
    @SerializedName("harga_jual")
    @Expose
    public String harga_jual;

    public ReactInsert() {}

    public ReactInsert(int id_user, String day, String month, String year,
                       String phone_number, String nominal, String jenisProvider, String hargaAsli, String hargaJual) {
        super();
        this.id_user = id_user;
        this.day = day;
        this.month = month;
        this.year = year;
        this.phone_number = phone_number;
        this.nominal = nominal;
        this.jenisProvider = jenisProvider;
        this.harga_asli = hargaAsli;
        this.harga_jual = hargaJual;
    }

    /**
     *
     * @return
     * The idUser
     */
    public int getIdUser() {
        return id_user;
    }

    /**
     *
     * @param id_user
     * The id_user
     */
    public void setIdUser(int id_user) {
        this.id_user = id_user;
    }

    /**
     *
     * @return
     * The day
     */
    public String getDay() {
        return day;
    }

    /**
     *
     * @param day
     * The day
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     *
     * @return
     * The month
     */
    public String getMonth() {
        return month;
    }

    /**
     *
     * @param month
     * The month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     *
     * @return
     * The year
     */
    public String getYear() {
        return year;
    }

    /**
     *
     * @param year
     * The year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     *
     * @return
     * The phoneNumber
     */
    public String getPhoneNumber() {
        return phone_number;
    }

    /**
     *
     * @param phoneNumber
     * The phone_number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phone_number = phoneNumber;
    }

    /**
     *
     * @return
     * The nominal
     */
    public String getNominal() {
        return nominal;
    }

    /**
     *
     * @param nominal
     * The nominal
     */
    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    /**
     *
     * @return
     * The jenisProvider
     */
    public String getJenisProvider() {
        return jenisProvider;
    }

    /**
     *
     * @param jenisProvider
     * The jenis_provider
     */
    public void setJenisProvider(String jenisProvider) {
        this.jenisProvider = jenisProvider;
    }

    /**
     *
     * @return
     * The hargaAsli
     */
    public String getHargaAsli() {
        return harga_asli;
    }

    /**
     *
     * @param hargaAsli
     * The harga_asli
     */
    public void setHargaAsli(String hargaAsli) {
        this.harga_asli = hargaAsli;
    }

    /**
     *
     * @return
     * The hargaJual
     */
    public String getHargaJual() {return harga_jual;}

    /**
     *
     * @param hargaJual
     * The harga_jual
     */
    public void setHargaJual(String hargaJual) {
        this.harga_jual = hargaJual;
    }

}
