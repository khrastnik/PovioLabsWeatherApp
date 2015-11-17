package com.weatherapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Klemen on 17.11.2015.
 */
public class WeatherCityManager {


    public List<WeatherCityModel> getFakeItems(){

        List<WeatherCityModel> items = new ArrayList<>();

        items.add(new WeatherCityModel("Maribor"));
        items.add(new WeatherCityModel("Celje"));
        items.add(new WeatherCityModel("Ljubljana"));
        items.add(new WeatherCityModel("Koper"));
        items.add(new WeatherCityModel("Laško"));

        return items;
    }
}
