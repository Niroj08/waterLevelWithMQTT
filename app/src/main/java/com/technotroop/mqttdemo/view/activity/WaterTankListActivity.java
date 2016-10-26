package com.technotroop.mqttdemo.view.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.daimajia.easing.linear.Linear;
import com.technotroop.mqttdemo.R;
import com.technotroop.mqttdemo.service.model.WaterTank;
import com.technotroop.mqttdemo.view.adapter.WaterTankAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by technotroop on 10/24/16.
 */


public class WaterTankListActivity extends AppCompatActivity {

    private ListView listViewTanks;
    private LinearLayout btnAddATank;
    private WaterTankAdapter adapter;
    private List<WaterTank> waterTank;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watertank);

        listViewTanks = (ListView) findViewById(R.id.listViewTanks);

        View footerView = ((LayoutInflater) getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.footer_addtank, null, false);

        listViewTanks.addFooterView(footerView);

        btnAddATank = (LinearLayout) footerView.findViewById(R.id.btnAddTank);
        btnAddATank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        WaterTank waterTankDemo = new WaterTank();
        waterTank = new ArrayList<>();

        for (int i = 0; i < 2; i++) {

            waterTankDemo.setBrandname("Test BrandName " + i);
            waterTankDemo.setVolume("Test Volume1 " + i);

            waterTank.add(waterTankDemo);
        }


        adapter = new WaterTankAdapter(waterTank);
        listViewTanks.setAdapter(adapter);
    }
}
