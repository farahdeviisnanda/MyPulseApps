package com.dev.farah.mypulseapps;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dev.farah.mypulseapps.model.ReactInsert;
import com.dev.farah.mypulseapps.model.ResponseCategori;
import com.dev.farah.mypulseapps.model.ResponseUpload;
import com.dev.farah.mypulseapps.service.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    SharedPreferences sharedpreferences;

    Button submit, edit, cancel;
    EditText idprof,mobileNumber, saleprice;
    Spinner spday, spmonth, spyear, spphone, spnominal, spprovider, spcatfav, spprice;

    private List<ResponseCategori> categoriesList; //jenis provider
    private List<ResponseCategori> nominalList;
    private List<ResponseCategori> kodeList;
    private List<ResponseCategori> priceList;
    private ProgressDialog progressDialog;

    //Get shared preferences and change editbox
    public String phonenumber, etsaleprice, name, phonedit, pricedit;
    public int iduser;

    //Variable for spinner
    public String iphonearea, daybirth, monthbirth, yearbirth, snominal, sprovider, scategory, sprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        progressDialog = new ProgressDialog(InsertActivity.this);
        progressDialog.setMessage("Please Wait...");

        mobileNumber = (EditText) findViewById(R.id.mobileNumber);
        idprof = (EditText) findViewById(R.id.et_iduser);
        saleprice = (EditText) findViewById(R.id.et_salePrice);

        spday   = (Spinner) findViewById(R.id.spDay);
        spmonth = (Spinner) findViewById(R.id.spMonth);
        spyear  = (Spinner) findViewById(R.id.spYears);
        spphone = (Spinner) findViewById(R.id.spPhoneCode);
        spnominal = (Spinner) findViewById(R.id.spNominal);
        spprovider = (Spinner) findViewById(R.id.spProvider);
        spprice = (Spinner) findViewById(R.id.spCenterPrice);
        spcatfav = (Spinner) findViewById(R.id.spCategory);

        submit  = (Button) findViewById(R.id.submit);
        edit    = (Button) findViewById(R.id.btn_editprofile);
        cancel  = (Button)findViewById(R.id.btnCancel);
        submit.setOnClickListener(this);
        edit.setOnClickListener(this);
        cancel.setOnClickListener(this);

        sharedpreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);

        iduser = sharedpreferences.getInt("idKey",iduser);
        idprof.setText(""+iduser);
        name = sharedpreferences.getString("nameKey", name);
        phonedit = sharedpreferences.getString("phoneKey", phonedit);
        mobileNumber.setText(phonedit);
        pricedit = sharedpreferences.getString("priceKey", pricedit);
        saleprice.setText(pricedit);

        //Birthday items
        ///------Day Selection-----///////
        ArrayAdapter dayadapter = ArrayAdapter.createFromResource(this,
                R.array.day_array, R.layout.spinner_item);
        dayadapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spday.setAdapter(dayadapter);
        // Category Item Selected Listener
        spday.setOnItemSelectedListener(this);

        ///------Month Selection-----///////
        ArrayAdapter monthadapter = ArrayAdapter.createFromResource(this,
                R.array.month_array, R.layout.spinner_item);
        monthadapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spmonth.setAdapter(monthadapter);
        // Category Item Selected Listener
        spmonth.setOnItemSelectedListener(this);

        ///------Year Selection-----///////
        ArrayAdapter yearadapter = ArrayAdapter.createFromResource(this,
                R.array.year_array, R.layout.spinner_item);
        yearadapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spyear.setAdapter(yearadapter);
        // Category Item Selected Listener
        spyear.setOnItemSelectedListener(this);

        // Phone area Items
        ArrayAdapter pnadapter = ArrayAdapter.createFromResource(this,
                R.array.phone_array, R.layout.spinner_item);
        pnadapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spphone.setAdapter(pnadapter);
        // Phone Item Selected Listener
        spphone.setOnItemSelectedListener(this);

        /////Nominal Items
        getNominal();
        spnominal.setOnItemSelectedListener(this);

        //Kode Provider Items
        getKode();
        spprovider.setOnItemSelectedListener(this);

        // Category Items
        getCategori();
        // Category Item Selected Listener
        spcatfav.setOnItemSelectedListener(this);

        //Price Items
        getPrice();
        spprice.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                progressDialog.setMessage("Uploading...");
                progressDialog.show();

                phonenumber = mobileNumber.getText().toString();
                etsaleprice = saleprice.getText().toString();

                    Toast.makeText(getApplicationContext(),"id "+iduser+
                            "\nphonearea "+iphonearea+
                            "\nphonenumber "+phonenumber+
                            "\nday "+daybirth+
                            "\nmonth "+monthbirth+
                            "\nyear "+yearbirth+
                            "\nprice "+etsaleprice+
                            "\nprovider "+scategory
                            ,Toast.LENGTH_LONG).show();
                if(!phonenumber.isEmpty() && !etsaleprice.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Request on Server", Toast.LENGTH_SHORT).show();
                    processInsert(iduser, daybirth, monthbirth, yearbirth, phonenumber, snominal, scategory, sprice, etsaleprice);
                }

                else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Please fill form correctly", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_editprofile:
                startActivity(new Intent(getApplicationContext(), InsertActivity.class));
                finish();
                break;

            case R.id.btnCancel:
                dialogcancel();
                break;
        }

    }

    protected void dialogcancel(){
        AlertDialog.Builder build = new AlertDialog.Builder(InsertActivity.this);
        build.setTitle(R.string.dismiss);
        build.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                // TODO Auto-generated method stub
                dialog.dismiss();
//                startActivity(new Intent(getApplicationContext(),TrendingVideoActivity.class));
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        build.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        build.create().show();
    }

    private void saveDataLog(String phone, String price) {
        new PrefManager(this).saveDataLogger(phone, price);
    }

    /////////------------------------Upload activity------------------------////////////////////////

    private void processInsert(int idUser, String day, String month, String year, final String phone_number, String nominal, String provider, String realpr, final String price){
        ApiService apiService = ApiService.retrofit.create(ApiService.class);

        final ReactInsert user = new ReactInsert();
        user.setIdUser(idUser);
        user.setPhoneNumber(phone_number);
        user.setNominal(nominal);
        user.setJenisProvider(provider);
        user.setDay(day);
        user.setMonth(month);
        user.setYear(year);
        user.setHargaAsli(realpr);
        user.setHargaJual(price);
        user.getIdUser();

        Call<ResponseUpload> callProfile = apiService.upload(user);
        callProfile.enqueue(new Callback<ResponseUpload>() {
            @Override
            public void onResponse(Call<ResponseUpload> call, Response<ResponseUpload> response) {
                if(progressDialog!=null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    ResponseUpload responseModel = response.body();
                    if(!responseModel.isError()){
                        Toast.makeText(getApplicationContext(),"id user: "+iduser,Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"username: "+name,Toast.LENGTH_SHORT).show();

                        saveDataLog(phone_number, price);

                        Toast.makeText(getApplicationContext(), ""+responseModel.getMessage().toString(),Toast.LENGTH_SHORT).show();
                        //                startActivity(new Intent(getApplicationContext(),TrendingVideoActivity.class));
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), ""+responseModel.getMessage().toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseUpload> call, Throwable t) {
                if(progressDialog!=null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), "Connect server failed \n"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    ////////*************************END of upload activity==================///////////////////////

    /////----------------------------Spinner Item Setting------------------//////////////////


    //Our method to show list
    private void showList(){
        //String array to store all the book names
        String[] items = new String[nominalList.size()];

        //Traversing through the whole list to get all the names
        for(int i=0; i<nominalList.size(); i++){
            //Storing names to string array
            items[i] = nominalList.get(i).getNominal();
        }

        //Creating an array adapter for list view
        ArrayAdapter statadapter = new ArrayAdapter<String>(this,R.layout.spinner_item,items);
        statadapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        //Setting adapter to listview
        spnominal.setAdapter(statadapter);
    }

    private void getNominal(){
        ApiService apiService = ApiService.retrofit.create(ApiService.class);
        final ResponseCategori responseState = new ResponseCategori();
        //user.setUsername(username);
        responseState.getId();
        responseState.getNominal();

        Call<List<ResponseCategori>> call = apiService.categori();

        call.enqueue(new Callback<List<ResponseCategori>>() {
            @Override
            public void onResponse(Call<List<ResponseCategori>> list, Response<List<ResponseCategori>> response) {
                if(progressDialog!=null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    //Storing the data in our list
                    List<ResponseCategori> responseModel = response.body();
                    nominalList = responseModel;
                    //Calling a method to show the list
                    showList();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseCategori>> call, Throwable t) {
                if(progressDialog!=null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), "Connect server failed\n"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    ///Spinner kode Provider
    //Our method to show list
    private void kodeList(){
        //String array to store all the book names
        String[] items = new String[kodeList.size()];

        //Traversing through the whole list to get all the names
        for(int i=0; i<kodeList.size(); i++){
            //Storing names to string array
            items[i] = kodeList.get(i).getKodeProvider();
        }

        //Creating an array adapter for list view
        ArrayAdapter statadapter = new ArrayAdapter<String>(this,R.layout.spinner_item,items);
        statadapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        //Setting adapter to listview
        spprovider.setAdapter(statadapter);
    }

    private void getKode(){
        ApiService apiService = ApiService.retrofit.create(ApiService.class);
        final ResponseCategori responseState = new ResponseCategori();
        //user.setUsername(username);
        responseState.getId();
        responseState.getKodeProvider();

        Call<List<ResponseCategori>> call = apiService.categori();

        call.enqueue(new Callback<List<ResponseCategori>>() {
            @Override
            public void onResponse(Call<List<ResponseCategori>> list, Response<List<ResponseCategori>> response) {
                if(progressDialog!=null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    //Storing the data in our list
                    List<ResponseCategori> responseModel = response.body();
                    kodeList = responseModel;
                    //Calling a method to show the list
                    kodeList();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseCategori>> call, Throwable t) {
                if(progressDialog!=null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), "Connect server failed\n"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Our method to show list
    private void categoryList(){
        //String array to store all the book names
        String[] items = new String[categoriesList.size()];

        //Traversing through the whole list to get all the names
        for(int i=0; i<categoriesList.size(); i++){
            //Storing names to string array
            items[i] = categoriesList.get(i).getName();
        }

        //Creating an array adapter for list view
        ArrayAdapter catadapter = new ArrayAdapter<String>(this,R.layout.spinner_item,items);
        catadapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        //Setting adapter to listview
        spcatfav.setAdapter(catadapter);
    }

    private void getCategori(){
        ApiService apiService = ApiService.retrofit.create(ApiService.class);

        final ResponseCategori responseCategori = new ResponseCategori();
        //user.setUsername(username);
        responseCategori.getId();
        responseCategori.getName();
//        final String userJson  = new GsonBuilder().create().toJson(user);
        Call <List<ResponseCategori>> callCategori = apiService.categori();

        callCategori.enqueue(new Callback<List<ResponseCategori>>() {
            @Override
            public void onResponse(Call<List<ResponseCategori>> list, Response<List<ResponseCategori>> response) {
                if(progressDialog!=null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    //Storing the data in our list
                    List<ResponseCategori> responseModel = response.body();
                    categoriesList = responseModel;
                    //Calling a method to show the list
                    categoryList();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseCategori>> call, Throwable t) {
                if(progressDialog!=null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), "Connect server failed\n"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Our method to show list
    private void priceList(){
        //String array to store all the book names
        String[] items = new String[priceList.size()];

        //Traversing through the whole list to get all the names
        for(int i=0; i<priceList.size(); i++){
            //Storing names to string array
            items[i] = priceList.get(i).getHargaPusat();
        }

        //Creating an array adapter for list view
        ArrayAdapter statadapter = new ArrayAdapter<String>(this,R.layout.spinner_item,items);
        statadapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        //Setting adapter to listview
        spprice.setAdapter(statadapter);
    }

    private void getPrice(){
        ApiService apiService = ApiService.retrofit.create(ApiService.class);
        final ResponseCategori responseState = new ResponseCategori();
        //user.setUsername(username);
        responseState.getId();
        responseState.getHargaPusat();

        Call<List<ResponseCategori>> call = apiService.categori();

        call.enqueue(new Callback<List<ResponseCategori>>() {
            @Override
            public void onResponse(Call<List<ResponseCategori>> list, Response<List<ResponseCategori>> response) {
                if(progressDialog!=null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    //Storing the data in our list
                    List<ResponseCategori> responseModel = response.body();
                    priceList = responseModel;
                    //Calling a method to show the list
                    priceList();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseCategori>> call, Throwable t) {
                if(progressDialog!=null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), "Connect server failed\n"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /////*********************************************Spinner Item Setting*********************************************//////////////////

    ////////////////-----------------Spinner selected listener---------------------////////////////////////
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()){

            case R.id.spDay:
                daybirth = parent.getItemAtPosition(position).toString();
                //icategory=position+1;
                if(daybirth.equals("Day")){}
                else{// Showing selected spinner item
                    Toast.makeText(getApplicationContext(),"Selected day : " + daybirth, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.spMonth:
                monthbirth = parent.getItemAtPosition(position).toString();
                //icategory=position+1;
                if(monthbirth.equals("Month")){}
                else{// Showing selected spinner item
                    Toast.makeText(getApplicationContext(),"Selected Month : " + monthbirth, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.spYears:
                yearbirth = parent.getItemAtPosition(position).toString();
                //icategory=position+1;
                if(yearbirth.equals("Years")){}
                else{// Showing selected spinner item
                    Toast.makeText(getApplicationContext(),"Selected Year : " + yearbirth, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.spPhoneCode:
                iphonearea = parent.getItemAtPosition(position).toString();
                if(iphonearea.equals("+")){}
                else{}

                break;

            case R.id.spNominal:
                snominal = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spProvider:
                sprovider = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spCategory:
                scategory = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spCenterPrice:
                sprice = parent.getItemAtPosition(position).toString();
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
