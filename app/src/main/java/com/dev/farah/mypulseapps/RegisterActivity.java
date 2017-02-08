package com.dev.farah.mypulseapps;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.farah.mypulseapps.model.ReactUser;
import com.dev.farah.mypulseapps.model.ResponseSignup;
import com.dev.farah.mypulseapps.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends Activity implements View.OnClickListener {

    Button register;
    EditText etusername, etemail, etpassword, etconfirmpass;
    TextView confirm;
    private ProgressDialog progressDialog;

    public String user,email,pass, confpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register=(Button)findViewById(R.id.btnregaccount);
        etusername=(EditText)findViewById(R.id.et_name);
        etemail=(EditText)findViewById(R.id.et_email);
        etpassword=(EditText)findViewById(R.id.et_passwordreg);
        etconfirmpass=(EditText)findViewById(R.id.et_confirmpasswordreg);
        confirm = (TextView) findViewById(R.id.tfConfirm);

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Please Wait...");
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnregaccount:
                progressDialog.show();

                user    = etusername.getText().toString();
                email   = etemail.getText().toString();
                pass    = etpassword.getText().toString();
                confpass= etconfirmpass.getText().toString();

                if(!user.isEmpty() && !email.isEmpty()&& !pass.isEmpty()&& !confpass.isEmpty()){
                    if(!(pass.equals(confpass))) {
                        confirm.setText("Password is not match");
                        etpassword.setText("");
                        etconfirmpass.setText("");
                        progressDialog.dismiss();
                    }
                    else{
                        confirm.setText("Password is match");
                        processSignUp(user, email, pass, confpass);
                    }
                }

                else {
                    Log.e(RegisterActivity.class.getSimpleName(), "username : "+user+
                            "\nemail : "+email+"\npass : "+pass+"\nconfirm pass : "+confpass);
                    Toast.makeText(getApplicationContext(),"Please fill form correctly", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void processSignUp(String username, String email, String password, String confrmpass){
        ApiService apiService = ApiService.retrofit.create(ApiService.class);

        final ReactUser user = new ReactUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setPassword(confrmpass);

        Call<ResponseSignup> callSignUp = apiService.signUp(user);

        callSignUp.enqueue(new Callback<ResponseSignup>() {
            @Override
            public void onResponse(Call<ResponseSignup> call, Response<ResponseSignup> response) {
                if(progressDialog!=null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    ResponseSignup responseModel = response.body();
                    if(!responseModel.isError()){
                        user.setUsername(responseModel.getUsername());
                        Toast.makeText(getApplicationContext(), ""+responseModel.getMessage().toString(),
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), ""+responseModel.getMessage().toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseSignup> call, Throwable t) {
                if(progressDialog!=null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), "Connect server failed\n"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
