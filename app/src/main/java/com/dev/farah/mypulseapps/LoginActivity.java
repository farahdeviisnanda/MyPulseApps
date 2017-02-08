package com.dev.farah.mypulseapps;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.farah.mypulseapps.model.ReactUser;
import com.dev.farah.mypulseapps.model.ResponseSignIn;
import com.dev.farah.mypulseapps.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity implements OnClickListener {

    Button btnLogin,btngoogle;
    EditText et_username,et_password;
    TextView registerlink;
    private CheckBox checkBoxRememberMe;

    private ProgressDialog progressDialog;
    public int iduser;
    public String pass,user, username;
    //variable for save preferences from database
    public String fname, lname, etphone, spingender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin=(Button)findViewById(R.id.btnlogin);
        btngoogle=(Button)findViewById(R.id.btn_signgoogle);
        registerlink=(TextView) findViewById(R.id.tvregister);
        et_username=(EditText)findViewById(R.id.et_username);
        et_password=(EditText)findViewById(R.id.et_password);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please Wait...");

        et_password.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            attemptLogin();
            }
        });

        checkBoxRememberMe = (CheckBox) findViewById(R.id.checkBox);
        //Here we will validate saved preferences
        if (!new PrefManager(this).isUserLogedOut()) {
            //user's email and password both are saved in preferences
            startHomeActivity();
        }

        btngoogle.setOnClickListener(this);
        registerlink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvregister:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                //finish();
                break;

            case R.id.btn_signgoogle:
                et_username.setText("admin@gmail.com");
                et_password.setText("admin");
                Toast.makeText(getApplicationContext(),"Success Sign With Google",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        progressDialog.show();

        // Reset errors.
        et_username.setError(null);
        et_password.setError(null);

        // Store values at the time of the login attempt.
        user = et_username.getText().toString();
        pass = et_password.getText().toString();

        boolean cancel = false;
        View focusView = null;
/*
        if(!user.isEmpty() && !pass.isEmpty()){
            processSignIn(user, pass);
        }*/

        // Check for a valid password, if the user entered one.
        if(!TextUtils.isEmpty(pass) && !isPasswordValid(pass)) {
            et_password.setError(getString(R.string.error_invalid_password));
            focusView = et_password;
            cancel = true;
            progressDialog.dismiss();
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(user)) {
            et_username.setError(getString(R.string.error_field_required));
            focusView = et_username;
            cancel = true;
            progressDialog.dismiss();
        } else if (!isEmailValid(user)) {
            et_username.setError(getString(R.string.error_invalid_email));
            focusView = et_username;
            cancel = true;
            progressDialog.dismiss();
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();

        } else {
            // save data in local shared preferences
            if (checkBoxRememberMe.isChecked())
                saveLoginDetails(user, pass);
                //startHomeActivity();
                processSignIn(user,pass);
        }
    }

    private void startHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveLoginDetails(String email, String password) {
        new PrefManager(this).saveLoginDetails(email, password);
    }

    private void saveDataUser(int id, String username){
        new PrefManager(this).saveDataUser(id, username);
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private void processSignIn(final String email, String password){
        ApiService apiService = ApiService.retrofit.create(ApiService.class);

        final ReactUser user = new ReactUser();
        //user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.getIdUser();

//        final String userJson  = new GsonBuilder().create().toJson(user);
        Call<ResponseSignIn> callSignIn = apiService.signIn(user);

        callSignIn.enqueue(new Callback<ResponseSignIn>() {
            @Override
            public void onResponse(Call<ResponseSignIn> call, Response<ResponseSignIn> response) {
                if(progressDialog!=null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    ResponseSignIn responseModel = response.body();
                    if(!responseModel.isError()){
                        user.setUsername(responseModel.getUsername());
                        iduser = responseModel.getIdUser();
                        username = responseModel.getUsername();
                        saveDataUser(iduser, username);

                        Toast.makeText(getApplicationContext(), "" +responseModel.getMessage().toString(),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "" +responseModel.getMessage().toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseSignIn> call, Throwable t) {
                if(progressDialog!=null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Toast.makeText(LoginActivity.this, "Connect server failed\n"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}