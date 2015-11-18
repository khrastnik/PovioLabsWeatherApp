package com.weatherapp.adapters;

/**
 * Created by Klemen on 17.11.2015.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.weatherapp.R;

import java.util.Collections;
import java.util.List;

import com.weatherapp.interfaces.IDeleteListener;
import com.weatherapp.managers.WeatherCityManager;
import com.weatherapp.models.WeatherCityModel;


public class WeatherCityAdapter extends RecyclerView.Adapter<WeatherCityAdapter.MyViewHolder> {

    List<WeatherCityModel> cityItems = Collections.emptyList();

    private LayoutInflater inflater;
    private Context context;
    private IDeleteListener deleteListener;

    public WeatherCityAdapter(Context context, List<WeatherCityModel> cityItems) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.cityItems = cityItems;
        this.deleteListener = (IDeleteListener) context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        WeatherCityModel current = cityItems.get(position);

        holder.cityName.setText(current.getName());

        setCityTemperature(holder.temperature, current);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * Remove item from recycler view
     * @param index item position
     */
    public void remove(int index) {

        int itemId = cityItems.get(index).getId();

        cityItems.remove(index);

        notifyItemRemoved(index);

        deleteListener.onDeleteItem(itemId);

    }

    @Override
    public int getItemCount() {
        return cityItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cityName, temperature;

        public MyViewHolder(View itemView) {
            super(itemView);
            cityName = (TextView) itemView.findViewById(R.id.city_name);
            temperature = (TextView) itemView.findViewById(R.id.city_temperature);
        }
    }

    /**
     * Notify dataset changed
     *
     * @param items refreshed items
     */
    public void notifyDataSet(List<WeatherCityModel> items) {

        this.cityItems = items;
        notifyDataSetChanged();
    }

    private void setCityTemperature(TextView tvTemp, WeatherCityModel weatherCityModel) {
        new WeatherCityManager().getWeatherTemperature(context, tvTemp, weatherCityModel.getName());
    }
}
