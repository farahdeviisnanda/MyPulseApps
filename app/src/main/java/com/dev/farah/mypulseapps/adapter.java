package com.dev.farah.mypulseapps;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dev.farah.mypulseapps.model.ReactInsert;
import com.dev.farah.mypulseapps.model.ResponseUpload;

import java.util.List;

/**
 * Created by toshiba on 12/5/2016.
 */

public class adapter extends ArrayAdapter<ResponseUpload> {

    //    String url="http://services.hanselandpetal.com/photos/";
//    String url="http://192.168.8.105/jasasoftdroid/foto";
//    String url="https://www.jasasoftdroid.com/xproject/upload/foto";
    private Context context;
    private List<ResponseUpload> flowerList;
    public adapter(Context context, int resource, List<ResponseUpload> objects) {
        super(context, resource, objects);
        this.context = context;
        this.flowerList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_file,parent,false);
        //Flower flower = flowerList.get(position);
        ResponseUpload insert = flowerList.get(position);
//        TextView tv = (TextView) view.findViewById(R.id.name);
//        tv.setText(insert.getName());

        return view;
    }
}