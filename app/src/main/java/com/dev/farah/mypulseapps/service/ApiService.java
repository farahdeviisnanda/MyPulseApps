package com.dev.farah.mypulseapps.service;

import com.dev.farah.mypulseapps.model.ReactInsert;
import com.dev.farah.mypulseapps.model.ReactUser;
import com.dev.farah.mypulseapps.model.ResponseCategori;
import com.dev.farah.mypulseapps.model.ResponseSignIn;
import com.dev.farah.mypulseapps.model.ResponseSignup;
import com.dev.farah.mypulseapps.model.ResponseUpload;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiService {
    //IP Address localhost atau wifi/folder htdocs
    ///server : "http://jasasoftdroid.com/xproject/upload/android/"
            //"https://jasasoftdroid.com/xproject/upload/android/" "https://www.jasasoftdroid.com/xproject/upload/android/"
    ///IP softdroid ; "http://192.168.8.100/3dcell/"
    // server 3dCell = "http://www.3dcellku.pe.hu/3dcel/" "http://www.my3dcell.hol.es/"
    public static final String API_URL = "http://www.3dcellku.pe.hu/3dcel/" ;

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    Retrofit retrofit = new Retrofit
            .Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    @POST("register.php")
    public Call<ResponseSignup> signUp(
            @Body ReactUser user
    );

    @POST("login.php")
    public Call<ResponseSignIn> signIn(
            @Body ReactUser user
    );

    @POST("insert.php")
    public Call<ResponseUpload> upload(
            @Body ReactInsert insert
    );

    @GET("get_info.php")
    public Call <List<ResponseCategori>> categori();

    @GET("get_insert.php")
    public Call <List<ResponseUpload>> insert();

}