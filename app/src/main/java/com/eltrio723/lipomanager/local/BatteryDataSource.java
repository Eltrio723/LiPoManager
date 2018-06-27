package com.eltrio723.lipomanager.local;

import com.eltrio723.lipomanager.Battery;
import com.eltrio723.lipomanager.database.IBatteryDataSource;

import java.util.List;

import io.reactivex.Flowable;

public class BatteryDataSource implements IBatteryDataSource{

    private BatteryDAO batteryDAO;
    private static BatteryDataSource instance;

    public BatteryDataSource(BatteryDAO batteryDAO){
        this.batteryDAO = batteryDAO;
    }

    public static  BatteryDataSource getInstance(BatteryDAO batteryDAO){
        if(instance == null){
            instance = new BatteryDataSource(batteryDAO);
        }
        return instance;
    }

    @Override
    public Flowable<Battery> getBatteryById(int batteryID) {
        return batteryDAO.getBatteryById(batteryID);
    }

    @Override
    public Flowable<List<Battery>> getAllBatteries() {
        return batteryDAO.getAllBatteries();
    }

    @Override
    public void insertBattery(Battery... batteries) {
        batteryDAO.insertBattery(batteries);
    }

    @Override
    public void updateBattery(Battery... batteries) {
        batteryDAO.updateBattery(batteries);
    }

    @Override
    public void deleteBattery(Battery battery) {
        batteryDAO.deleteBattery(battery);
    }

    @Override
    public void deleteAllBatteries() {
        batteryDAO.deleteAllBatteries();
    }
}

