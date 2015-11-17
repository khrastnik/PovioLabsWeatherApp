package com.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_CITY = 1000;

    private RecyclerView recyclerView;
    private WeatherCityAdapter weatherCityAdapter;
    private TextView tvEmptyListHint;
    private List<WeatherCityModel> cityItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGUI();

        setRecyclerViewData();

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

        weatherCityAdapter = new WeatherCityAdapter(this, cityItems);
        recyclerView.setAdapter(weatherCityAdapter);
    }

    /**
     * Get cities from database
     */
    private void setCityItems() {
        cityItems = new WeatherCityManager().getItems();
    }

    /**
     * Check if cities are added
     */
    private void checkEmptyRecyclerView() {

        int cityCount = cityItems.size();

        if (cityCount == 0) {
            tvEmptyListHint.setVisibility(View.VISIBLE);
        } else {
            tvEmptyListHint.setVisibility(View.GONE);
        }

    }

    /**
     * Init floating action button
     */
    private void initFab() {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startAddCityActivity();

            }
        });
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

        setRecyclerViewItemListener();

        tvEmptyListHint = (TextView) findViewById(R.id.empty_list_hint);

        initFab();
    }

    /**
     * Set recycler item click listener
     */
    private void setRecyclerViewItemListener() {

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
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
}
