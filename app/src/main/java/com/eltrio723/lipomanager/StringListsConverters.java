package com.eltrio723.lipomanager;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringListsConverters {
    static Gson gson = new Gson();

    @TypeConverter
    public static ArrayList<String> stringToList(String string) {
        if (string == null) {
            return (ArrayList) Collections.emptyList();
        }
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(string, type);
    }

    @TypeConverter
    public static String ListToString(List<String> list) {
        return gson.toJson(list);
    }
}
