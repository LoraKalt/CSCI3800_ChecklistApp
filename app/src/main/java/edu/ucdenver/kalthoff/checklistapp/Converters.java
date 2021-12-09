package edu.ucdenver.kalthoff.checklistapp;

import android.util.Log;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Converters {
    @TypeConverter
    public static Calendar toDate(String str) {
        try {
            if (str != null) {
                Calendar date = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
                date.setTime(sdf.parse(str));
                return date;
            } else {
                return null;
            }
        } catch (ParseException pe) {
            Log.i("info", "Parse Exception: " + pe);
        }
        return null;
    }

    @TypeConverter
    public static String toString(Calendar date) {
        if (date != null) {
            return String.format(String.valueOf(date.getTime()));
        } else {
            return null;
        }

    }
}
