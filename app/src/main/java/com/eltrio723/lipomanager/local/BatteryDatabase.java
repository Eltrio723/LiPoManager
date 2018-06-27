package com.eltrio723.lipomanager.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.eltrio723.lipomanager.Battery;

import static com.eltrio723.lipomanager.local.BatteryDatabase.DATABASE_VERSION;

@Database(entities = Battery.class, version = DATABASE_VERSION)
public abstract class BatteryDatabase extends RoomDatabase {
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="Battery-Database-Room";

    public abstract BatteryDAO batteryDAO();

    private static BatteryDatabase instance;

    public static BatteryDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,BatteryDatabase.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


}
