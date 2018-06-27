package com.eltrio723.lipomanager.database;

import com.eltrio723.lipomanager.Battery;

import java.util.List;

import io.reactivex.Flowable;

public class BatteryRepository implements IBatteryDataSource {
    private IBatteryDataSource localDataSource;
    private static BatteryRepository instance;

    public BatteryRepository(IBatteryDataSource localDataSource){
        this.localDataSource = localDataSource;
    }

    public static BatteryRepository getInstance(IBatteryDataSource localDataSource){
        if(instance == null)
            instance = new BatteryRepository(localDataSource);
        return instance;
    }

    @Override
    public Flowable<Battery> getBatteryById(int batteryID) {
        return localDataSource.getBatteryById(batteryID);
    }

    @Override
    public Flowable<List<Battery>> getAllBatteries() {
        return localDataSource.getAllBatteries();
    }

    @Override
    public void insertBattery(Battery... batteries) {
        localDataSource.insertBattery(batteries);
    }

    @Override
    public void updateBattery(Battery... batteries) {
        localDataSource.updateBattery(batteries);
    }

    @Override
    public void deleteBattery(Battery battery) {
        localDataSource.deleteBattery(battery);
    }

    @Override
    public void deleteAllBatteries() {
        localDataSource.deleteAllBatteries();
    }
}
