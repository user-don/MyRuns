package edu.cs65.don.myruns.helpers;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.joda.time.DateTime;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    private static final int DATABASE_VERSION = 3;

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
    public void insertEntry(ExerciseEntry entry) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(INPUT_TYPE, entry.mInputType);
        values.put(ACTIVITY_TYPE, entry.mActivityType);
        // for inserting our date object, we store as ms from epoch
        values.put(DATE_TIME, entry.storeDateTime());
        values.put(DURATION, entry.mDuration);
        values.put(DISTANCE, entry.mDistance);
        values.put(AVG_PACE, entry.mAvgPace);
        values.put(AVG_SPEED, entry.mAvgSpeed);
        values.put(CALORIES, entry.mCalorie);
        values.put(CLIMB, entry.mClimb);
        values.put(HEARTRATE, entry.mHeartRate);
        values.put(COMMENT, entry.mComment);
        // put ArrayList in as a blob
//        byte[] ba = {};
//        try {
//            ba = Serializer.serialize(entry.mLocationList);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ByteBuffer bb = ByteBuffer.wrap(ba);
//        values.put(GPS_DATA, bb.toString());
        // TODO: Save the result code?? Yes do that...
        entry.id = db.insert(TABLE_ENTRIES, null, values);
        Log.d("RUNS", "Saved entry at " + entry.id);
        //db.close();
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
        String rowIdStr = Long.toString(rowId);
        String selection = COLUMN_ID + " = " + rowId;
        Log.d("RUNS", "Fetching entry at row " + rowId);
        Cursor query = db.query(TABLE_ENTRIES, allColumns, selection,
                null, null, null, null);
        ExerciseEntry entry = new ExerciseEntry();
        if (query != null && query.moveToFirst()) {
            entry.id = query.getLong(query.getColumnIndex(COLUMN_ID));
            entry.mInputType = query.getInt(query.getColumnIndex(INPUT_TYPE));
            entry.mActivityType = query.getInt(query.getColumnIndex(ACTIVITY_TYPE));
            // Do calendar mDateTime specially
            long timeFromEpoch = query.getInt(query.getColumnIndex(DATE_TIME));
            //        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
            //        cal.setTimeInMillis(timeFromEpoch);
            entry.mDateTime = new DateTime(timeFromEpoch * 1000);
            entry.mDuration = query.getInt(query.getColumnIndex(DURATION));
            entry.mDistance = query.getDouble(query.getColumnIndex(DISTANCE));
            entry.mAvgPace = query.getDouble(query.getColumnIndex(AVG_PACE));
            entry.mAvgSpeed = query.getDouble(query.getColumnIndex(AVG_SPEED));
            entry.mCalorie = query.getInt(query.getColumnIndex(CALORIES));
            entry.mClimb = query.getDouble(query.getColumnIndex(CLIMB));
            entry.mHeartRate = query.getInt(query.getColumnIndex(HEARTRATE));
            entry.mComment = query.getString(query.getColumnIndex(COMMENT));
            //        String gpsData = query.getString(query.getColumnIndex(GPS_DATA));
            //        byte[] bytes = gpsData.getBytes();
            //        ArrayList<LatLng> mLocationList = new ArrayList<>();
            //        try {
            //             mLocationList = Serializer.deserializeToArraylist(bytes);
            //        } catch (Exception e) {
            //            e.printStackTrace();
            //        }
            //        entry.mLocationList = mLocationList;
            query.close();
        } else {
            throw new Error("SHIT IS NOT WORKING");
        }
        return entry;
    }

    // Query the entire table, return all rows
    public ArrayList<ExerciseEntry> fetchEntries() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor query = db.rawQuery("select * from " + TABLE_ENTRIES, null);
//        Cursor query = db.query(TABLE_ENTRIES, allColumns, null,
//                null, null, null, null);
        ArrayList<ExerciseEntry> entries = new ArrayList<>();
        if (query != null && query.moveToFirst()) {
            while (!query.isAfterLast()) {
                ExerciseEntry entry = new ExerciseEntry();
                entry.id = query.getLong(query.getColumnIndex(COLUMN_ID));
                Log.d("RUNS", "Entry ID retrieved: " + entry.id);
                entry.mInputType = query.getInt(query.getColumnIndex(INPUT_TYPE));
                entry.mActivityType = query.getInt(query.getColumnIndex(ACTIVITY_TYPE));
                // Do calendar mDateTime specially
                long timeFromEpoch = query.getInt(query.getColumnIndex(DATE_TIME));
//                Calendar cal = Calendar.getInstance(TimeZone.getDefault());
//                cal.setTimeInMillis(timeFromEpoch * 1000);
                entry.mDateTime = new DateTime(timeFromEpoch * 1000);
                entry.mDuration = query.getInt(query.getColumnIndex(DURATION));
                entry.mDistance = query.getDouble(query.getColumnIndex(DISTANCE));
                entry.mAvgPace = query.getDouble(query.getColumnIndex(AVG_PACE));
                entry.mAvgSpeed = query.getDouble(query.getColumnIndex(AVG_SPEED));
                entry.mCalorie = query.getInt(query.getColumnIndex(CALORIES));
                entry.mClimb = query.getDouble(query.getColumnIndex(CLIMB));
                entry.mHeartRate = query.getInt(query.getColumnIndex(HEARTRATE));
                entry.mComment = query.getString(query.getColumnIndex(COMMENT));
                // do location list specially
//                String gpsData = query.getString(query.getColumnIndex(GPS_DATA));
//                byte[] bytes = gpsData.getBytes();
//                ArrayList<LatLng> mLocationList = new ArrayList<>();
//                try {
//                    mLocationList = Serializer.deserializeToArraylist(bytes);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                entry.mLocationList = mLocationList;
                entries.add(entry);
                query.moveToNext();
            }
            query.close();
        }
        return entries;
    }
}
