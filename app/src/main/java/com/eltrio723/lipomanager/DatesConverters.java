package com.eltrio723.lipomanager;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

public class DatesConverters {
    static Gson gson = new Gson();

    @TypeConverter
    public static Date stringToDate(String string) {
        if (string == null) {
            return Calendar.getInstance().getTime();
        }
        Type type = new TypeToken<Date>() {}.getType();
        return gson.fromJson(string, type);
    }

    @TypeConverter
    public static String DateToString(Date date) {
        return gson.toJson(date);
    }
}
