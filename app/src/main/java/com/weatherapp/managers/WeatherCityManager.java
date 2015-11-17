package com.weatherapp.managers;

import android.content.Context;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.weatherapp.db.DatabaseHelper;
import com.weatherapp.interfaces.IHttpRequest;
import com.weatherapp.models.WeatherCityModel;
import com.weatherapp.rest.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Klemen on 17.11.2015.
 */
public class WeatherCityManager {

    private static final String TAG_WEATHER = "weather";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_MAIN = "main";
    private static final String TAG_TEMP = "temp";
    private static final String TAG_HUMIDITY = "humidity";

    public WeatherCityManager() {
    }

    /**
     * Get weather detail data from json object
     *
     * @param response json object
     * @return object weather data
     * @throws JSONException
     */
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

    /**
     * Get weather detail data from json object
     *
     * @param response json object
     * @return object weather data
     * @throws JSONException
     */
    public WeatherCityModel getWeatherTemperatureByJson(JSONObject response) throws JSONException {

        WeatherCityModel weatherCityModel = new WeatherCityModel();

        JSONObject main = response.getJSONObject(TAG_MAIN);

        // get temperature from main json object
        String temp = main.getString(TAG_TEMP);

        weatherCityModel.setCurrentTemperature(Double.valueOf(temp));

        return weatherCityModel;
    }


    /**
     * Add city data to db
     *
     * @param context         application context
     * @param weatherCityItem weather city object
     */
    public void addCity(Context context, WeatherCityModel weatherCityItem) {

        DatabaseHelper database = DatabaseHelper.instance(context);

        database.add(weatherCityItem);

    }

    /**
     * Get all cities from db
     *
     * @param context application context
     * @return list of cities
     */
    public List<WeatherCityModel> getAll(Context context) {

        DatabaseHelper database = DatabaseHelper.instance(context);

        return database.getAll();

    }

    /**
     * Delete item from db
     *
     * @param context application context
     * @param id      city id
     * @return true if item is deleted
     */
    public boolean deleteItem(Context context, int id) {

        DatabaseHelper databaseHelper = DatabaseHelper.instance(context);

        return databaseHelper.deleteItemById(id) == 1;
    }

    /**
     * Execute http get request and get weather detail data
     *
     * @param context     context
     * @param httpRequest http listener
     * @param cityName    name of city
     */
    public void getWeatherDetailDataFromAPI(final Context context, final IHttpRequest httpRequest, final String cityName) {

        RestClient.get(context, cityName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                WeatherCityModel weatherCityModel;

                try {

                    weatherCityModel = getWeatherDetailByJson(response);

                    weatherCityModel.setName(cityName);

                    //setWeatherDetail(weatherCityModel);
                    httpRequest.onComplete(weatherCityModel);

                } catch (Exception e) {
                    e.printStackTrace();
                    httpRequest.onFailure();
                }
            }
        });
    }

    /**
     * Execute GET request and get weather temperature by city name
     *
     * @param context       app context
     * @param tvTemperature temperature tex view
     * @param cityName      name of city
     */
    public void getWeatherTemperature(final Context context, final TextView tvTemperature, final String cityName) {

        RestClient.get(context, cityName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                WeatherCityModel weatherCityModel;

                try {

                    weatherCityModel = getWeatherTemperatureByJson(response);

                    weatherCityModel.setName(cityName);

                    tvTemperature.setText(String.format("%s °C", String.valueOf(weatherCityModel.getCurrentTemperature())));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
