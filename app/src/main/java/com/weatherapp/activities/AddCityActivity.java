package com.weatherapp.activities;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.weatherapp.interfaces.IKeyboardListener;
import com.weatherapp.managers.KeyboardKeyManager;
import com.weatherapp.R;
import com.weatherapp.managers.WeatherCityManager;

import com.weatherapp.models.WeatherCityModel;

public class AddCityActivity extends AppCompatActivity implements IKeyboardListener {

    private EditText inputCityName;
    private TextInputLayout inputLayoutCityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        initGUI();

    }

    /**
     * Init gui components
     */
    private void initGUI() {

        setToolbar();

        inputCityName = (EditText) findViewById(R.id.input_city_name);
        inputLayoutCityName = (TextInputLayout) findViewById(R.id.input_layout_city_name);
        inputCityName.addTextChangedListener(new MyTextWatcher(inputCityName));

        new KeyboardKeyManager(this).setKeyboardDoneKeyListener(inputCityName);
    }

    /**
     * Set toolbar and back navigation arrow
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

    /**
     * Validate and add new city
     *
     * @param view button view
     */
    public void onClickAddCity(View view) {
        saveNewAddedCity();
    }


    /**
     * Save new added city to db
     */
    private void saveNewAddedCity() {

        boolean isValid = validateName();

        if (isValid) {

            String cityName = inputCityName.getText().toString();
            WeatherCityModel weatherCityModel = new WeatherCityModel(cityName);
            new WeatherCityManager().addCity(this,weatherCityModel);

            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void keyDonePressed() {
        saveNewAddedCity();
    }

    /**
     * Displays a warning when a user delete the city name
     */
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_city_name:
                    validateName();
                    break;
            }
        }
    }

    /**
     * Validate city name
     *
     * @return true if name is entered or false if not
     */
    private boolean validateName() {

        if (inputCityName.getText().toString().trim().isEmpty()) {
            inputLayoutCityName.setError(getString(R.string.err_city_name));
            return false;
        } else {
            inputLayoutCityName.setErrorEnabled(false);
        }
        return true;
    }

}
