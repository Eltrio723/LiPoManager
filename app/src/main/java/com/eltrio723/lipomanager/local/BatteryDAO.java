package com.eltrio723.lipomanager.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.eltrio723.lipomanager.Battery;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface BatteryDAO {
    @Query("SELECT * FROM batteries WHERE id=:batteryID")
    Flowable<Battery> getBatteryById(int batteryID);

    @Query("SELECT * FROM batteries")
    Flowable<List<Battery>> getAllBatteries();

    @Insert
    void insertBattery(Battery... batteries);

    @Update
    void updateBattery(Battery... batteries);

    @Delete
    void deleteBattery(Battery batteries);

    @Query("DELETE FROM batteries")
    void deleteAllBatteries();
}
