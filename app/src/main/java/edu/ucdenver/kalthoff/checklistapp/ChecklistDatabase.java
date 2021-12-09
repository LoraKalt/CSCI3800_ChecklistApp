package edu.ucdenver.kalthoff.checklistapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * Handles the database
 */

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

