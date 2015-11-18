package com.weatherapp.managers;

/**
 * Created by Klemen on 18.11.2015.
 */
public class TemperatureConverter {

    private static final double CONST_KELVIN = 273.15;

    /**
     * Convert kelvin to celsius
     *
     * @param kelvin kelvin temperature
     * @return temperature in celsius
     */
    public double kelvinToCelsius(double kelvin) {
        return new RoundNumberManager().round(kelvin - CONST_KELVIN);
    }

}
