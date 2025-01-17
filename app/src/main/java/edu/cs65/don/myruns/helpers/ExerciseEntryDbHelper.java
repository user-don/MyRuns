package edu.cs65.don.myruns.helpers;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import org.joda.time.DateTime;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.TimeZone;

import edu.cs65.don.myruns.controllers.DataController;
import edu.cs65.don.myruns.models.ExerciseEntry;

/**
 * Helper class for managing DB interactions for ExerciseEntry objects.
 *
 * Created by don on 4/22/16.
 */
public class ExerciseEntryDbHelper extends SQLiteOpenHelper {

    public static final String TABLE_ENTRIES = "entries";
    public static final String DATABASE_NAME = "entries.db";
    public static final String COLUMN_ID = "_id";
    public static final String INPUT_TYPE = "input_type";
    public static final String ACTIVITY_TYPE = "activity_type";
    public static final String DATE_TIME = "date_time";
    public static final String DURATION = "duration";
    public static final String DISTANCE = "distance";
    public static final String AVG_PACE = "avg_pace";
    public static final String AVG_SPEED = "avg_speed";
    public static final String CALORIES = "calories";
    public static final String CLIMB = "climb";
    public static final String HEARTRATE = "heartrate";
    public static final String COMMENT = "comment";
    public static final String GPS_DATA = "gps_data";
    private static final int DATABASE_VERSION = 8;

    private String[] allColumns = { COLUMN_ID, INPUT_TYPE, ACTIVITY_TYPE, DATE_TIME,
        DURATION, DISTANCE, AVG_PACE, AVG_SPEED, CALORIES, CLIMB, HEARTRATE, COMMENT,
        GPS_DATA};

    // Database creation sql statement
    private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_ENTRIES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + INPUT_TYPE + " INTEGER NOT NULL, "
            + ACTIVITY_TYPE + " INTEGER NOT NULL, "
            + DATE_TIME + " INTEGER NOT NULL, "
            + DURATION + " INTEGER NOT NULL, "
            + DISTANCE + " FLOAT, "
            + AVG_PACE + " FLOAT, "
            + AVG_SPEED + " FLOAT, "
            + CALORIES + " INTEGER, "
            + CLIMB + " FLOAT, "
            + HEARTRATE + " INTEGER, "
            + COMMENT + " TEXT, "
            + GPS_DATA + " BLOB );";

    /**
     * Public constructor, because SQLiteOpenHelper does not automatically implement
     * @param context context of the application
     */
    public ExerciseEntryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * onCreate only gets called when the database (for current version number) does not already
     * exist.
     * @param db the database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    /**
     * onUpgrade() only called when the db file exists but the stored version number
     * is lower than the current one. Currently, this drops the existing table and
     * creates a new one.
     * @param db the database
     * @param oldVersion the old version of the database
     * @param newVersion the new version of the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ExerciseEntryDbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRIES);
        // Once deleted, create new database!
        onCreate(db);
    }

    // Insert a item given each column value
    public Long insertEntry(ExerciseEntry entry) {
        ExerciseEntry e = entry;
        if (entry == null) {
            e = new ExerciseEntry();
        }
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(INPUT_TYPE, e.mInputType);
        values.put(ACTIVITY_TYPE, e.mActivityType);
        // for inserting our date object, we store as ms from epoch
        values.put(DATE_TIME, e.storeDateTime());
        values.put(DURATION, e.mDuration);
        values.put(DISTANCE, e.mDistance);
        values.put(AVG_PACE, e.mAvgPace);
        values.put(AVG_SPEED, e.mAvgSpeed);
        values.put(CALORIES, e.mCalorie);
        values.put(CLIMB, e.mClimb);
        values.put(HEARTRATE, e.mHeartRate);
        values.put(COMMENT, e.mComment);
        // put ArrayList in as a blob

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream o = new DataOutputStream(b);
        for (LatLng loc : e.mLocationList) {
            try {
                o.writeUTF(loc.latitude + "&" + loc.longitude);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        byte[] ba = b.toByteArray();

        values.put(GPS_DATA, ba);
        e.id = db.insert(TABLE_ENTRIES, null, values);
        //Log.d("RUNS", "Saved e at " + e.id);
        //db.close();
        return e.id;

    }

    // Remove an entry by giving its index
    public void removeEntry(long rowIndex) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COLUMN_ID + " =?";
        String[] whereArgs = new String[] {String.valueOf(rowIndex)};
        int status = db.delete(TABLE_ENTRIES, whereClause, whereArgs);
        if (status != 1) {
            // something went wrong
            throw new Error("The number of entries deleted in the database"
            + " was not equal to one.");
        }
        db.close();
    }

    // Query a specific entry by its index.
    public ExerciseEntry fetchEntryByIndex(long rowId) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = COLUMN_ID + " = " + rowId;
        Cursor query = db.query(TABLE_ENTRIES, allColumns, selection,
                null, null, null, null);
        ExerciseEntry entry = new ExerciseEntry();
        if (query != null && query.moveToFirst()) {
            entry.id = query.getLong(query.getColumnIndex(COLUMN_ID));
            entry.mInputType = query.getInt(query.getColumnIndex(INPUT_TYPE));
            entry.mActivityType = query.getInt(query.getColumnIndex(ACTIVITY_TYPE));
            long timeFromEpoch = query.getInt(query.getColumnIndex(DATE_TIME));
            entry.mDateTime = new DateTime(timeFromEpoch * 1000);
            entry.mDuration = query.getInt(query.getColumnIndex(DURATION));
            entry.mDistance = query.getDouble(query.getColumnIndex(DISTANCE));
            entry.mAvgPace = query.getDouble(query.getColumnIndex(AVG_PACE));
            entry.mAvgSpeed = query.getDouble(query.getColumnIndex(AVG_SPEED));
            entry.mCalorie = query.getInt(query.getColumnIndex(CALORIES));
            entry.mClimb = query.getDouble(query.getColumnIndex(CLIMB));
            entry.mHeartRate = query.getInt(query.getColumnIndex(HEARTRATE));
            entry.mComment = query.getString(query.getColumnIndex(COMMENT));
            byte[] blob = query.getBlob(query.getColumnIndex(GPS_DATA));

            ByteArrayInputStream bais = new ByteArrayInputStream(blob);
            DataInputStream dis = new DataInputStream(bais);
            ArrayList<LatLng> locList = new ArrayList<>();
            try {
                while (dis.available() > 0) {
                    String latLngStr = dis.readUTF();
                    Iterable<String> split = Splitter.on('&')
                            .trimResults()
                            .omitEmptyStrings()
                            .split(latLngStr);
                    ArrayList<String> splitStr = Lists.newArrayList(split.iterator());
                    double lat = Double.parseDouble(splitStr.get(0));
                    double lon = Double.parseDouble(splitStr.get(1));
                    //Log.d("RUNS", "lat: " + String.valueOf(lat) + "lon: " + String.valueOf(lon));
                    LatLng latLng = new LatLng(lat,lon);
                    locList.add(latLng);
                }
                entry.mLocationList = locList;
            } catch (Exception e) {
                e.printStackTrace();
            }
            query.close();
        } else {
            throw new Error("Database query not formatted correctly");
        }
        return entry;
    }

    // Query the entire table, return all rows
    public ArrayList<ExerciseEntry> fetchEntries() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor query = db.rawQuery("select * from " + TABLE_ENTRIES, null);
        ArrayList<ExerciseEntry> entries = new ArrayList<>();
        if (query != null && query.moveToFirst()) {
            while (!query.isAfterLast()) {
                ExerciseEntry entry = new ExerciseEntry();
                entry.id = query.getLong(query.getColumnIndex(COLUMN_ID));
                //Log.d("RUNS", "Entry ID retrieved: " + entry.id);
                entry.mInputType = query.getInt(query.getColumnIndex(INPUT_TYPE));
                entry.mActivityType = query.getInt(query.getColumnIndex(ACTIVITY_TYPE));
                long timeFromEpoch = query.getInt(query.getColumnIndex(DATE_TIME));
                entry.mDateTime = new DateTime(timeFromEpoch * 1000);
                entry.mDuration = query.getInt(query.getColumnIndex(DURATION));
                entry.mDistance = query.getDouble(query.getColumnIndex(DISTANCE));
                entry.mAvgPace = query.getDouble(query.getColumnIndex(AVG_PACE));
                entry.mAvgSpeed = query.getDouble(query.getColumnIndex(AVG_SPEED));
                entry.mCalorie = query.getInt(query.getColumnIndex(CALORIES));
                entry.mClimb = query.getDouble(query.getColumnIndex(CLIMB));
                entry.mHeartRate = query.getInt(query.getColumnIndex(HEARTRATE));
                entry.mComment = query.getString(query.getColumnIndex(COMMENT));
                byte[] blob = query.getBlob(query.getColumnIndex(GPS_DATA));
                ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                DataInputStream dis = new DataInputStream(bais);
                ArrayList<LatLng> locList = new ArrayList<>();
                try {
                    while (dis.available() > 0) {
                        String latLngStr = dis.readUTF();
                        Iterable<String> split = Splitter.on('&')
                                .trimResults()
                                .omitEmptyStrings()
                                .split(latLngStr);
                        ArrayList<String> splitStr = Lists.newArrayList(split.iterator());
                        double lat = Double.parseDouble(splitStr.get(0));
                        double lon = Double.parseDouble(splitStr.get(1));
                        //Log.d("RUNS", "lat: " + String.valueOf(lat) + "lon: " + String.valueOf(lon));
                        LatLng latLng = new LatLng(lat,lon);
                        locList.add(latLng);
                    }
                    entry.mLocationList = locList;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                entries.add(entry);
                query.moveToNext();
            }
            query.close();
        }
        return entries;
    }
}
