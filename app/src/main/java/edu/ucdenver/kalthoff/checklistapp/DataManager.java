package edu.ucdenver.kalthoff.checklistapp;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;

public class DataManager {
    private SQLiteDatabase db;
    public DataManager(Context context){
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    public Cursor selectAll(){
        Cursor cursor = null;
        try{
            String query = "select * from todolist";
            //select * from contact where _id = %id
            //select * from contact order by name
            cursor = db.rawQuery(query, null);
        }
        catch(SQLException e){
            Log.i("info", "Selection Function: " + e.getMessage());
        }

        return cursor;
    }

    public void insert(String todo, int taskStatus, String dueDate, int priority){
        try{
            String query = "insert into todolist" +
                    "(todo, taskStatus ) values " +
                    "('" + todo + "', '" + taskStatus + "', '" +
                    dueDate + "', '" + priority + "')";
            db.execSQL(query);
        }
        catch(SQLException e){
            Log.i("info", "Insert: " + e.getMessage());
        }
    }

    private class MySQLiteOpenHelper extends SQLiteOpenHelper{
        public MySQLiteOpenHelper(Context context){
            super(context, "checklist", null, 1);

        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try{
                String query = "create table todolist (" +
                        "_id integer primary key autoincrement not null, " +
                        "todo text not null, " +
                        "taskStatus integer, " +
                        "dueDate integer, " +
                        "priority text)"; // need two date fields, boolean and priority num

                db.execSQL(query);
            }
            catch(SQLException e){
                Log.i("info", "MySQLOpen Helper onCreate: " + e.getMessage());
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {

        }
    }
}
