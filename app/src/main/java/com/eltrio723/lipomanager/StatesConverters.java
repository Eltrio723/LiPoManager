package com.eltrio723.lipomanager;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class StatesConverters {
    static Gson gson = new Gson();

    @TypeConverter
    public static State stringToState(String string) {
        if (string == null) {
            return State.OTHER;
        }
        Type type = new TypeToken<State>() {}.getType();
        return gson.fromJson(string, type);
    }

    @TypeConverter
    public static String StateToString(State state) {
        return gson.toJson(state);
    }
}
