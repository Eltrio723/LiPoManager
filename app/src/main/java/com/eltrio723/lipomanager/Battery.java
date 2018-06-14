package com.eltrio723.lipomanager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Battery {

    static double minCellVoltage = 3.6;
    static double maxCellVoltage = 4.2;
    static double storageCellVoltage = 3.8;


    int id;
    int capacity;
    int discharge;
    int cells;
    double voltage;
    double currentVoltage;
    String brand;
    Date buyDate;
    int timesUsed;
    State state;
    ArrayList<String> notes;
    Date lastUsed;
    Date lastCharged;
    Date lastModified;
    Connector connector;


    public Battery(){
        id = 0;
        capacity = 0;
        discharge = 0;
        cells = 0;
        voltage = 0;
        currentVoltage = 0;
        brand = "";
        buyDate = new Date();
        timesUsed = 0;
        state = State.NEW;
        notes = new ArrayList<String>();
        lastUsed = new Date();
        lastCharged = new Date();
        lastModified = new Date();
        connector = Connector.XT60;
    }

    public Battery(int id, int capacity, int discharge, int cells, String brand, Connector connector){
        this.id = id;
        this.capacity = capacity;
        this.discharge = discharge;
        this.cells = cells;
        voltage = cells*3.7;
        currentVoltage = voltage;
        this.brand = brand;
        buyDate = Calendar.getInstance().getTime();
        timesUsed = 0;
        state = State.NEW;
        notes = new ArrayList<String>();
        lastUsed = new Date();
        lastCharged = new Date();
        lastModified = Calendar.getInstance().getTime();
        this.connector = connector;
    }



    int getId() { return id; }

    int getCapacity() { return capacity; }

    int getDischarge() { return discharge; }

    int getCells() { return cells; }

    double getVoltage() { return voltage; }

    double getCurrentVoltage() { return currentVoltage; }

    String getBrand() { return brand; }

    Date getBuyDate() { return buyDate; }

    int getTimesUsed() { return timesUsed; }

    State getState() { return state; }

    ArrayList<String> getNotes() { return notes; }

    Date getLastUsed() { return lastUsed; }

    Date getLastCharged() { return lastCharged; }

    Date getLastModified() { return lastModified; }

    Connector getConnector() { return connector; }


    void setId(int id){ this.id = id; }

    void setCapacity(int capacity) { this.capacity = capacity; }

    void setDischarge(int discharge) { this.discharge = discharge; }

    void setCells(int cells) { this.cells = cells; }

    void setVoltage(double voltage) { this.voltage = voltage; }

    void setCurrentVoltage(double currentVoltage) { this.currentVoltage = currentVoltage; }

    void setBrand(String brand) { this.brand = brand; }

    void setBuyDate(Date buyDate) { this.buyDate = buyDate; }

    void setTimesUsed(int timesUsed) { this.timesUsed = timesUsed; }

    void setState(State state) { this.state = state; }

    void setNotes(ArrayList<String> notes) { this.notes = notes; }

    void setLastUsed(Date lastUsed) { this.lastUsed = lastUsed; }

    void setLastCharged(Date lastCharged) { this.lastCharged = lastCharged; }

    void setLastModified(Date lastModified) { this.lastModified = lastModified; }

    void setConnector(Connector connector) { this.connector = connector; }


    double getDepletedVoltage(){
        return cells*minCellVoltage;
    }

    double getChargedVoltage(){
        return cells*maxCellVoltage;
    }

    double getStorageVoltage(){
        return cells*storageCellVoltage;
    }

    void increaseTimesUsed(){
        timesUsed++;
    }

    Boolean isCharged(){
        return state == State.CHARGED;
    }


    Boolean startUse() {
        if (state == State.CHARGED || state == State.USED){
            state = State.IN_USE;
            return true;
        }
        return false;
    }

    Boolean endUse() {
        if (state == State.IN_USE){
            setCurrentVoltage(getDepletedVoltage());
            state = State.DEPLETED;
            increaseTimesUsed();
            return true;
        }
        return false;
    }

    Boolean endUse(double currVolt) {
        if (state == State.IN_USE){
            setCurrentVoltage(currVolt);
            if(currVolt<=getDepletedVoltage()) {
                state = State.DEPLETED;
                increaseTimesUsed();
            }
            else
                state = State.USED;
            return true;
        }
        return false;
    }

    Boolean startCharge() {
        if (state == State.DEPLETED || state == State.USED){
            state = State.CHARGING;
            return true;
        }
        return false;
    }

    Boolean endCharge() {
        if (state == State.CHARGING){
            setCurrentVoltage(getChargedVoltage());
            state = State.CHARGED;
            return true;
        }
        return false;
    }


}
