package edu.cs65.don.myruns.helpers;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;

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
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS" + TABLE_ENTRIES + "("
            + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
            + INPUT_TYPE + "INTEGER NOT NULL, "
            + ACTIVITY_TYPE + "INTEGER NOT NULL, "
            + DATE_TIME + "DATETIME NOT NULL, "
            + DURATION + "INTEGER NOT NULL, "
            + DISTANCE + "FLOAT, "
            + AVG_PACE + "FLOAT, "
            + AVG_SPEED + "FLOAT, "
            + CALORIES + "INTEGER, "
            + CLIMB + "FLOAT, "
            + HEARTRATE + "INTEGER, "
            + COMMENT + "TEXT, "
            + GPS_DATA + "BLOB );";

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
    public long insertEntry(ExerciseEntry entry) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(INPUT_TYPE, entry.mInputType);
        values.put(ACTIVITY_TYPE, entry.mActivityType);
        // for inserting our date object, we store as string. Not worrying about UTC for now.
        values.put(DATE_TIME, entry.mDateTime.toString());
        values.put(DURATION, entry.mDuration);
        values.put(DISTANCE, entry.mDistance);
        values.put(AVG_PACE, entry.mAvgPace);
        values.put(AVG_SPEED, entry.mAvgSpeed);
        values.put(CALORIES, entry.mCalorie);
        values.put(CLIMB, entry.mClimb);
        values.put(HEARTRATE, entry.mHeartRate);
        values.put(COMMENT, entry.mComment);
        // put ArrayList in as a blob
        byte[] ba = {};
        try {
            ba = Serializer.serialize(entry.mLocationList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteBuffer bb = ByteBuffer.wrap(ba);
        values.put(GPS_DATA, bb.toString());

        return db.insert(TABLE_ENTRIES, null, values);
    }
}
