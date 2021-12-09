package edu.ucdenver.kalthoff.checklistapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Database(entities = {Checklist.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class ChecklistDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "checklist.db";

    private static ChecklistDatabase checklistDB;

    public static ChecklistDatabase getInstance(Context context){
        if(checklistDB == null){
            checklistDB = Room.databaseBuilder(context, ChecklistDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return checklistDB;
    }

    public abstract  ChecklistDao checklistDao();


}

