package com.technotroop.mqttdemo.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technotroop.mqttdemo.R;
import com.technotroop.mqttdemo.service.model.WaterTank;
import com.technotroop.mqttdemo.utils.MQTTUtils;

import java.util.ArrayList;

/**
 * Created by technotroop on 10/24/16.
 */

public class WaterTankAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private final ArrayList<WaterTank> waterTankList;
    private ViewHolder vh;

    public WaterTankAdapter(ArrayList<WaterTank> waterTankList) {

        this.waterTankList = waterTankList;
        mInflater = (LayoutInflater) MQTTUtils.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return waterTankList.size();
    }

    @Override
    public Object getItem(int position) {
        return waterTankList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.listitem_watertank, null);
            vh = new ViewHolder();

            vh.brandName = (TextView) convertView.findViewById(R.id.textBrandName);
            vh.volume = (TextView) convertView.findViewById(R.id.textVolume);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        WaterTank waterTank = waterTankList.get(position);

        vh.brandName.setText(waterTank.getBrandName());
        vh.volume.setText("Volume: " + waterTank.getVolume());

        return convertView;
    }

    public static class ViewHolder {

        private TextView brandName;
        private TextView volume;
    }
}
