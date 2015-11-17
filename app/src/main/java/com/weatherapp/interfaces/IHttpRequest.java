package com.weatherapp.interfaces;

/**
 * Created by Klemen on 17.11.2015.
 */
public interface IHttpRequest<T> {

    void onComplete(T object);
    void onFailure();

}
