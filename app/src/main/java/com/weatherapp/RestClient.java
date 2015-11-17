package com.weatherapp;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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