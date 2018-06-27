package com.eltrio723.lipomanager.database;


import com.eltrio723.lipomanager.Battery;

import java.util.List;

import io.reactivex.Flowable;

public interface IBatteryDataSource {
    Flowable<Battery> getBatteryById(int batteryID);
    Flowable<List<Battery>> getAllBatteries();
    void insertBattery(Battery... batteries);
    void updateBattery(Battery... batteries);
    void deleteBattery(Battery batteries);
    void deleteAllBatteries();
}
