package com.weatherapp.models;

import java.io.Serializable;

/**
 * Created by Klemen on 17.11.2015.
 */
public class WeatherCityModel implements Serializable{

    private int id;
    private String name;
    private double currentTemperature;
    private int humidity;
    private String description;

    public WeatherCityModel() {
    }

    public WeatherCityModel(String name) {
        this.name = name;
    }

    public WeatherCityModel(String name, double currentTemperature, int humidity, String description) {
        this.name = name;
        this.currentTemperature = currentTemperature;
        this.humidity = humidity;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "WeatherCityModel{" +
                "name='" + name + '\'' +
                ", currentTemperature=" + currentTemperature +
                ", humidity=" + humidity +
                ", description='" + description + '\'' +
                '}';
    }
}
