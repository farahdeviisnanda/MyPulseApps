package com.dev.farah.mypulseapps;

/**
 * Created by toshiba on 12/5/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Class for Shared Preference
 */
public class PrefManager {

    Context context;

    public String MyPreferences = "LoginDetails";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    PrefManager(Context context) {
        this.context = context;
    }

    public void saveLoginDetails(String email, String password) {
        sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("Email", email);
        editor.putString("Password", password);
        editor.commit();
    }

    public void saveDataUser(int id, String username){
        sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("idKey", id);
        editor.putString("nameKey", username);
        editor.commit();
    }

    public void saveDataLogger(String phone, String price){
        sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("phoneKey", phone);
        editor.putString("priceKey", price);
        editor.commit();
    }

    public String getEmail() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Email", "");
    }

    public boolean isUserLogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean isEmailEmpty = sharedPreferences.getString("Email", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("Password", "").isEmpty();
        return isEmailEmpty || isPasswordEmpty;
    }

    public void userLogout(){
                SharedPreferences sharedpreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
    }
}