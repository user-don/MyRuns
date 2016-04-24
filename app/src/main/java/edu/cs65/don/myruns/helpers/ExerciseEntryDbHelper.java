package edu.cs65.don.myruns.helpers;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
