package com.technotroop.mqttdemo.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technotroop.mqttdemo.R;
import com.technotroop.mqttdemo.service.model.City;
import com.technotroop.mqttdemo.service.model.WaterTank;
import com.technotroop.mqttdemo.utils.MQTTUtils;

import java.util.ArrayList;

/**
 * Created by technotroop on 10/27/16.
 */
public class CustomSpinnerAdapter extends BaseAdapter {

    ArrayList<City> cityList;

    private final LayoutInflater mInflater;
    private ViewHolder vh;

    public CustomSpinnerAdapter(ArrayList<City> cityList) {
        this.cityList = cityList;

        mInflater = (LayoutInflater) MQTTUtils.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.listitem_city_spinner, null);
            vh = new ViewHolder();

            vh.cityId = (TextView) convertView.findViewById(R.id.idCity);
            vh.cityName = (TextView) convertView.findViewById(R.id.cityName);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        City city = cityList.get(position);

        vh.cityId.setText(city.getId());
        vh.cityName.setText(city.getName());

        return convertView;
    }

    public static class ViewHolder {
        private TextView cityId;
        private TextView cityName;
    }
}
