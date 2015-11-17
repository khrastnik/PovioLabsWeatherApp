package com.weatherapp.adapters;

/**
 * Created by Klemen on 17.11.2015.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weatherapp.R;

import java.util.Collections;
import java.util.List;

import com.weatherapp.interfaces.IHttpRequest;
import com.weatherapp.managers.WeatherCityManager;
import com.weatherapp.models.WeatherCityModel;


public class WeatherCityAdapter extends RecyclerView.Adapter<WeatherCityAdapter.MyViewHolder> {

    List<WeatherCityModel> cityItems = Collections.emptyList();

    private LayoutInflater inflater;
    private Context context;

    private TextView tvTemperature;

    public WeatherCityAdapter(Context context, List<WeatherCityModel> cityItems) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.cityItems = cityItems;
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

        setCityTemperature(holder.temperature,current);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void removeItem(int pos) {
        cityItems.remove(pos);
    }

    @Override
    public int getItemCount() {
        return cityItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cityName,temperature;

        public MyViewHolder(View itemView) {
            super(itemView);
            cityName = (TextView) itemView.findViewById(R.id.city_name);
            temperature = (TextView)itemView.findViewById(R.id.city_temperature);
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

    private void setCityTemperature(TextView tvTemp,WeatherCityModel weatherCityModel){
        new WeatherCityManager().getWeatherTemperature(context,tvTemp,weatherCityModel.getName());
    }
}
