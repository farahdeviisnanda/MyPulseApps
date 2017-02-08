package com.dev.farah.mypulseapps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by toshiba on 12/6/2016.
 */

public class ShowDetails extends Activity {

    TextView tvId, tvDate, tvPhone, tvNominal, tvHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_file);

        tvId    =   (TextView) findViewById(R.id.iduser);
        tvDate  =   (TextView) findViewById(R.id.date);
        tvPhone =   (TextView) findViewById(R.id.name);
        tvNominal = (TextView) findViewById(R.id.nominal);
        tvHarga =   (TextView) findViewById(R.id.harga);

        //Getting intent
        Intent intent = getIntent();

        //Displaying values by fetching from intent
        tvId.setText(String.valueOf(intent.getIntExtra(MainActivity.KEY_ID, 0)));
        tvDate.setText(intent.getStringExtra(MainActivity.KEY_DATE));
        tvPhone.setText(intent.getStringExtra(MainActivity.KEY_PHONE));
        tvNominal.setText(intent.getStringExtra(MainActivity.KEY_NOMINAL));
        tvHarga.setText(intent.getStringExtra(MainActivity.KEY_PRICE));

    }
}
