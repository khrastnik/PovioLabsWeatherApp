package com.weatherapp;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;

public class DetailCityWeatherActivity extends AppCompatActivity {

    private TextView tvCityName, tvCurrentTemperature, tvHumidity, tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_city_weather);

        initGUI();

        getBundleData();

    }

    /**
     * Init GUI components
     */
    private void initGUI() {

        setToolbar();

        tvCityName = (TextView) findViewById(R.id.city_name_value);
        tvCurrentTemperature = (TextView) findViewById(R.id.temperature_value);
        tvHumidity = (TextView) findViewById(R.id.humidity_value);
        tvDescription = (TextView) findViewById(R.id.description_value);
    }

    /**
     * Get data from previous activity
     */
    private void getBundleData() {

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            String weatherCityItem = bundle.getString(Const.CITY_NAME);

            if (weatherCityItem != null) {
                getWeatherDataFromAPI(weatherCityItem);
            }

        }
    }

    /**
     * Get detail weather data by city name
     *
     * @param cityName name of city
     */
    private void getWeatherDataFromAPI(final String cityName) {

        RestClient.get(this, cityName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                WeatherCityModel weatherCityModel;

                try {

                    weatherCityModel = new WeatherCityManager().getWeatherDetailByJson(response);

                    weatherCityModel.setName(cityName);

                    setWeatherDetail(weatherCityModel);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(DetailCityWeatherActivity.this, "Weather data for selected city name is not available.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * Set weather detail data to GUI components
     *
     * @param weatherCityItem weather object
     */
    private void setWeatherDetail(WeatherCityModel weatherCityItem) {

        tvCityName.setText(weatherCityItem.getName());
        tvCurrentTemperature.setText(String.format("%s °C", String.valueOf(weatherCityItem.getCurrentTemperature())));
        tvHumidity.setText(String.valueOf(weatherCityItem.getHumidity()));
        tvDescription.setText(weatherCityItem.getDescription());
    }

    /**
     * Set toolbar and show back navigation arrow
     */
    private void setToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
