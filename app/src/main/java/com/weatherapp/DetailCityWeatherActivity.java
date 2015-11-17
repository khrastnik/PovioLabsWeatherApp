package com.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DetailCityWeatherActivity extends AppCompatActivity {

    private TextView tvCityName,tvCurrentTemperature,tvHumidity,tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_city_weather);

        setToolbar();

        tvCityName = (TextView)findViewById(R.id.city_name_value);
        tvCurrentTemperature = (TextView)findViewById(R.id.temperature_value);
        tvHumidity = (TextView)findViewById(R.id.humidity_value);
        tvDescription = (TextView)findViewById(R.id.description_value);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){

            WeatherCityModel weatherCityItem = (WeatherCityModel) bundle.getSerializable("city");

            try {
                setWeatherDetail(weatherCityItem);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(DetailCityWeatherActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void setWeatherDetail(WeatherCityModel weatherCityItem) throws Exception {

        if(weatherCityItem == null){
            throw new Exception("Error, no weather data.");
        }

        tvCityName.setText(weatherCityItem.getName());
        tvCurrentTemperature.setText(String.format("%s °C", String.valueOf(weatherCityItem.getCurrentTemperature())));
        tvHumidity.setText(String.valueOf(weatherCityItem.getHumidity()));
        tvDescription.setText(weatherCityItem.getDescription());
    }

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
