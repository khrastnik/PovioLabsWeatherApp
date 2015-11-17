package com.weatherapp.helpers;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.weatherapp.adapters.WeatherCityAdapter;

/**
 * Created by Klemen on 17.11.2015.
 */
public class RecyclerViewTouchHelper extends ItemTouchHelper.SimpleCallback {

    private WeatherCityAdapter mMovieAdapter;

    public RecyclerViewTouchHelper(WeatherCityAdapter movieAdapter){
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.mMovieAdapter = movieAdapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //TODO: Not implemented here
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //Remove item
        mMovieAdapter.remove(viewHolder.getAdapterPosition());
    }
}
