package com.dev.farah.mypulseapps;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.farah.mypulseapps.model.ResponseUpload;
import com.dev.farah.mypulseapps.service.ApiService;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity implements View.OnClickListener {

    //Strings to bind with intent will be used to send data to other activity
    public static final String KEY_ID = "key_book_id";
    public static final String KEY_NAME = "key_book_name";
    public static final String KEY_PRICE = "key_book_price";
    public static final String KEY_DATE = "key_book_date";
    public static final String KEY_PHONE = "key_book_phone";
    public static final String KEY_NOMINAL = "key_book_nominal";

    public int iduser;
    public String username;
    private ProgressDialog progressDialog;

    Button buttonmenu, buttonreact;
    ListView listView;

    SharedPreferences sharedpreferences;

    // array list for spinner adapter
    private List<ResponseUpload> insertList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(MainActivity.this);

        getDataUser(); //calling the preferences data
        //Initializing Button, TextView and listview
        buttonmenu = (Button) findViewById(R.id.btnmenu);
        buttonreact= (Button) findViewById(R.id.btnreact);

        listView = (ListView) findViewById(R.id.list);

        //Calling the method that will fetch data
        getList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ShowDetails.class);
                //Getting the requested book from the list
                ResponseUpload insert = insertList.get(position);

                //Adding book details to intent
                intent.putExtra(KEY_ID,insert.getId());
                intent.putExtra(KEY_DATE,insert.getDate_sale());
                intent.putExtra(KEY_PHONE, insert.getPhone_number());
                intent.putExtra(KEY_NOMINAL, insert.getNominal());
                intent.putExtra(KEY_NAME,insert.getName());
                intent.putExtra(KEY_PRICE,insert.getHarga_jual());

                //Starting another activity to show book details
                startActivity(intent);
            }
        });

        //set listener button
        buttonmenu.setOnClickListener(this); buttonreact.setOnClickListener(this);

    }

    private void getDataUser(){
        sharedpreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        Toast.makeText(getApplicationContext(),"id user: "+sharedpreferences.getInt("idKey",iduser)+
                "\nWelcome: "+sharedpreferences.getString("nameKey",username),Toast.LENGTH_SHORT).show();
    }

//------------------------Button Listener Activity--------------------------//////////////////////
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnmenu:
                // Create the instance of Menu
                PopupMenu popup = new PopupMenu(MainActivity.this, buttonmenu);
                // Inflating menu using xml file
                popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());

                // registering OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("Home")){
                            Intent i = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }

                        else if(item.getTitle().equals("Insert")){
                            startActivity(new Intent(getApplicationContext(),InsertActivity.class));
                        }

                        else if(item.getTitle().equals("Logout")){
                            dialog();
                        }

                        else {
                            Toast.makeText(MainActivity.this,
                                    "Kamu telah memilih : " + item.getTitle(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        return true;
                        }});
                    popup.show();
                break;

            case R.id.btnreact:
                startActivity(new Intent(this, InsertActivity.class));
                finish();
                break;
        }

    }
    //------------------------END Button Listener Activity--------------------------//////////////////////

    ///---------------------------Show data listener Listview----------------------///////////////////////
    /////---------------Array List Spinner to population database----------///

    //Our method to show list
    private void showList(){
        //String array to store all the book names
        String[] items = new String[insertList.size()];

        //Traversing through the whole list to get all the names
        for(int i=0; i<insertList.size(); i++){
            //Storing names to string array
            items[i] = insertList.get(i).getDate_sale();
        }
        //Creating an array adapter for list view
        ArrayAdapter catadapter = new ArrayAdapter<String>(this,R.layout.simple_item,items);
        //catadapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        //Setting adapter to listview
        listView.setAdapter(catadapter);
    }

    private void getList(){
        ApiService apiService = ApiService.retrofit.create(ApiService.class);

        final ResponseUpload responseCategori = new ResponseUpload();
        //user.setUsername(username);
        responseCategori.getId();
        responseCategori.getName();
//        final String userJson  = new GsonBuilder().create().toJson(user);
        Call<List<ResponseUpload>> callCategori = apiService.insert();

        callCategori.enqueue(new Callback<List<ResponseUpload>>() {
            @Override
            public void onResponse(Call<List<ResponseUpload>> list, Response<List<ResponseUpload>> response) {
                if(progressDialog!=null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    //Storing the data in our list
                    List<ResponseUpload> responseModel = response.body();
                    insertList = responseModel;
                    //Calling a method to show the list
                    showList();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseUpload>> call, Throwable t) {
                if(progressDialog!=null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), "Connect server failed\n"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    ///-----------------------User Logout Dialog Activity--------------------------------------------////////////////////

    private void logout(){ new PrefManager(this).userLogout();}

    protected void dialog(){
        AlertDialog.Builder build = new AlertDialog.Builder(MainActivity.this);
        build.setTitle(R.string.message);
        build.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                logout();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
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

    ///-----------------------END User Logout Dialog Activity---------------------------------------////////////////////

}
