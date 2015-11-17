package com.weatherapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Klemen on 17.11.2015.
 */
public class WeatherCityManager {

    private static final String TAG_WEATHER = "weather";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_MAIN = "main";
    private static final String TAG_TEMP = "temp";
    private static final String TAG_HUMIDITY = "humidity";


    private static List<WeatherCityModel> items;

    public WeatherCityManager() {

        if (items == null) {
            items = new ArrayList<>();
        }
    }

    public void addCity(WeatherCityModel weatherCityItem) {
        items.add(weatherCityItem);
    }

    public List<WeatherCityModel> getItems() {
        return items;
    }

    public List<WeatherCityModel> getFakeItems() {

        List<WeatherCityModel> items = new ArrayList<>();

        items.add(new WeatherCityModel("Maribor"));
        items.add(new WeatherCityModel("Celje"));
        items.add(new WeatherCityModel("Ljubljana"));
        items.add(new WeatherCityModel("Koper"));
        items.add(new WeatherCityModel("Laško"));

        return items;
    }

    public WeatherCityModel getWeatherDetailByJson(JSONObject response) throws JSONException {

        WeatherCityModel weatherCityModel = new WeatherCityModel();

        JSONArray weather = response.getJSONArray(TAG_WEATHER);

        // get  description from weather array
        for (int i = 0; i < weather.length(); i++) {
            JSONObject object = weather.getJSONObject(i);
            String description = object.getString(TAG_DESCRIPTION);
            weatherCityModel.setDescription(description);
        }

        JSONObject main = response.getJSONObject(TAG_MAIN);

        // get temperature from main json object
        String temp = main.getString(TAG_TEMP);

        // get humudity from main json object
        String humidity = main.getString(TAG_HUMIDITY);

        weatherCityModel.setCurrentTemperature(Double.valueOf(temp));
        weatherCityModel.setHumidity(Integer.valueOf(humidity));

        return weatherCityModel;
    }
}
