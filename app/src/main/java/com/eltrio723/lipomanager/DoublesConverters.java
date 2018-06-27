package com.eltrio723.lipomanager;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class DoublesConverters {
    static Gson gson = new Gson();

    @TypeConverter
    public static Double stringToState(String string) {
        if (string == null) {
            return 0.0;
        }
        Type type = new TypeToken<Double>() {}.getType();
        return gson.fromJson(string, type);
    }

    @TypeConverter
    public static String StateToString(Double doub) {
        return gson.toJson(doub);
    }
}
