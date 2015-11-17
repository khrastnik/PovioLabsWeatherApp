package com.weatherapp.rest;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.weatherapp.R;
import com.weatherapp.managers.WeatherCityManager;
import com.weatherapp.models.WeatherCityModel;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Klemen on 17.11.2015.
 */
public class RestClient {

    private static AsyncHttpClient client = new AsyncHttpClient();

    /**
     * Execute get request
     * @param context application context
     * @param cityName name of city
     * @param responseHandler response handler
     */
    public static void get(Context context, String cityName, AsyncHttpResponseHandler responseHandler) {

        String url = context.getString(R.string.weather_api);

        RequestParams requestParams = new RequestParams();
        requestParams.add("appId", context.getString(R.string.weather_api_key));
        requestParams.add("q", cityName);

        client.get(url, requestParams, responseHandler);
    }


}