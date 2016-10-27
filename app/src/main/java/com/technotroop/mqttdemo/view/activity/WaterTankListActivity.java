package com.technotroop.mqttdemo.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.technotroop.mqttdemo.R;
import com.technotroop.mqttdemo.controller.WaterTankController;
import com.technotroop.mqttdemo.service.model.WaterTank;
import com.technotroop.mqttdemo.view.adapter.WaterTankAdapter;
import com.technotroop.mqttdemo.view.interfaces.WaterTankInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by technotroop on 10/24/16.
 */


public class WaterTankListActivity extends AppCompatActivity implements WaterTankInterface {

    private ListView listViewTanks;
    private LinearLayout btnAddATank;
    private WaterTankAdapter adapter;
    private ArrayList<WaterTank> waterTankList;

    private WaterTankController waterTankController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watertank);


        listViewTanks = (ListView) findViewById(R.id.listViewTanks);

        listViewTanks.setVisibility(View.GONE);

        waterTankController = new WaterTankController(this);

        waterTankController.getWaterTanks();
        View footerView = ((LayoutInflater) getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.footer_addtank, null, false);

        listViewTanks.addFooterView(footerView);

        btnAddATank = (LinearLayout) footerView.findViewById(R.id.btnAddTank);
        btnAddATank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: add a tank from user itself
            }
        });
    }

    @Override
    public void onSuccessGetWaterTank(ArrayList<WaterTank> waterTankList) {

        this.waterTankList = waterTankList;
        adapter = new WaterTankAdapter(this.waterTankList);

        listViewTanks.setAdapter(adapter);
    }

    @Override
    public void onErrorGetWaterTank(String error) {

    }

    @Override
    public void onErrorNoConnection() {

    }
}
