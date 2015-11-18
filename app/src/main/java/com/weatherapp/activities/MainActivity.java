package com.weatherapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weatherapp.helpers.Const;
import com.weatherapp.helpers.RecyclerViewIteClickHelper;
import com.weatherapp.R;
import com.weatherapp.adapters.WeatherCityAdapter;
import com.weatherapp.helpers.RecyclerViewTouchHelper;
import com.weatherapp.interfaces.IDeleteListener;
import com.weatherapp.managers.WeatherCityManager;

import java.util.List;

import com.weatherapp.models.WeatherCityModel;

public class MainActivity extends AppCompatActivity implements IDeleteListener {

    private static final int REQUEST_CODE_ADD_CITY = 1000;

    private RecyclerView recyclerView;
    private WeatherCityAdapter weatherCityAdapter;
    private RelativeLayout emptyListHintLayout;
    private List<WeatherCityModel> cityItems;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGUI();

        setRecyclerViewData();

        setRecyclerSwipeListener();
    }

    /**
     * Set recycler view swipe (left right) to dismiss item
     */
    private void setRecyclerSwipeListener() {

        ItemTouchHelper.Callback callback = new RecyclerViewTouchHelper(weatherCityAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }

    /**
     * Set pull to refresh recycler view items
     */
    private void setPullToRefresh() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                setRecyclerViewData();
            }
        });
    }

    /**
     * Get cities, check cities count and set recycler adapter
     */
    private void setRecyclerViewData() {

        setCityItems();

        checkEmptyRecyclerView();

        setAdapter();
    }

    /**
     * Create recycler view adapter
     */
    private void setAdapter() {

        if (weatherCityAdapter == null) {
            weatherCityAdapter = new WeatherCityAdapter(this, cityItems);
            recyclerView.setAdapter(weatherCityAdapter);
        } else {
            weatherCityAdapter.notifyDataSet(cityItems);
        }

        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * Get cities from database
     */
    private void setCityItems() {
        cityItems = new WeatherCityManager().getAll(this);
    }

    /**
     * Check if cities are added
     */
    private void checkEmptyRecyclerView() {

        int cityCount = cityItems.size();

        if (cityCount == 0) {
            emptyListHintLayout.setVisibility(View.VISIBLE);
        } else {
            emptyListHintLayout.setVisibility(View.GONE);
        }

    }

    /**
     * Start activity for add new city
     */
    private void startAddCityActivity() {

        Intent intent = new Intent(this, AddCityActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_CITY);

    }

    /**
     * Init gui components
     */
    private void initGUI() {

        setToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        emptyListHintLayout = (RelativeLayout) findViewById(R.id.empty_list_hint);

        setRecyclerViewItemListener();

        setPullToRefresh();

    }

    /**
     * Set recycler item click listener
     */
    private void setRecyclerViewItemListener() {

        RecyclerViewIteClickHelper.addTo(recyclerView).setOnItemClickListener(new RecyclerViewIteClickHelper.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Intent intent = new Intent(getBaseContext(), DetailCityWeatherActivity.class);
                intent.putExtra(Const.CITY_NAME, cityItems.get(position).getName());
                startActivity(intent);
            }
        });
    }

    /**
     * Set activity toolbar
     */
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_CODE_ADD_CITY) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user add new city
                // refresh list of cities
                setRecyclerViewData();
            }
        }
    }

    @Override
    public void onDeleteItem(int id) {

        new WeatherCityManager().deleteItem(this, id);

        Snackbar.make(findViewById(android.R.id.content), "City has been deleted.", Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.RED)
                .show();

        checkEmptyRecyclerView();

    }

    /**
     * Add new city listener
     * @param view view
     */
    public void onClickAddCity(View view) {
        startAddCityActivity();
    }
}
