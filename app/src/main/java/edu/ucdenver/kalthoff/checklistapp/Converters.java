package edu.ucdenver.kalthoff.checklistapp;

import android.util.Log;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Handles the conversion of complex object types to types that the database can handle
 */
public class Converters {
    /**
     * Converts a string to a Calendar date
     * @param str String to be converted, must be exact
     * @return Calendar
     */
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

    /**
     * Converts a Calendar object to a String
     * @param date Calendar
     * @return String
     */
    @TypeConverter
    public static String toString(Calendar date) {
        if (date != null) {
            return String.format(String.valueOf(date.getTime()));
        } else {
            return null;
        }

    }
}
