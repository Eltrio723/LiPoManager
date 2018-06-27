package com.eltrio723.lipomanager;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ConnectorsConverters {
    static Gson gson = new Gson();

    @TypeConverter
    public static Connector stringToConnector(String string) {
        if (string == null) {
            return Connector.OTHER;
        }
        Type type = new TypeToken<Connector>() {}.getType();
        return gson.fromJson(string, type);
    }

    @TypeConverter
    public static String StateToString(Connector connector) {
        return gson.toJson(connector);
    }
}
