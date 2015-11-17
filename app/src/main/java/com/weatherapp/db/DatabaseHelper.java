package com.weatherapp.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.weatherapp.models.WeatherCityModel;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "cities.db";

    // City table name
    private static final String TABLE_CITY = "city_table";

    // City Table Columns names
    private static final String COLUMN_CITY_ID = "id_city";
    private static final String COLUMN_CITY_NAME = "name";

    public static DatabaseHelper databaseHandler = null;

    // SINGLETON PATTERN
    public static DatabaseHelper instance(Context con) {

        if (databaseHandler == null && con != null) {
            databaseHandler = new DatabaseHelper(con.getApplicationContext());
        }

        return databaseHandler;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
        }
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableCorrectionFactorSql());
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY);
        // Create tables again
        onCreate(db);
    }

    // create table
    public String createTableCorrectionFactorSql() {
        String CREATE_CITY_TABLE = "CREATE TABLE " + TABLE_CITY
                + "(" + COLUMN_CITY_ID + " INTEGER PRIMARY KEY," // ID
                + COLUMN_CITY_NAME + " TEXT" + ")"; // NAME
        return CREATE_CITY_TABLE;
    }

    /**
     * Add new city
     */
    public void add(WeatherCityModel item) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CITY_NAME, item.getName());
            // Inserting Row
            db.insert(TABLE_CITY, null, values);
            db.close(); // Closing database connection
        } catch (Exception e) {
            db.close();
        }
    }


    /**
     * Get all cities from db
     *
     * @return list of cities
     */
    public List<WeatherCityModel> getAll() {

        List<WeatherCityModel> itemsList = new ArrayList<WeatherCityModel>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT * FROM " + TABLE_CITY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                WeatherCityModel corFactor = new WeatherCityModel();
                corFactor.setId(Integer.valueOf(cursor.getString(0)));
                corFactor.setName(cursor.getString(1));

                itemsList.add(corFactor);

            } while (cursor.moveToNext());
        }
        db.close();

        return itemsList;
    }


    /**
     * Delete one city by id
     * @param id city id
     * @return 1 if deleted is success
     */
    public int deleteItemById(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CITY, COLUMN_CITY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    /**
     * Get items count
     *
     * @return number of items
     */
    public int getItemsCount() {

        SQLiteDatabase db = this.getWritableDatabase();
        String countQuery = "SELECT count(*) FROM " + TABLE_CITY;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        db.close();
        return count;
    }
}
