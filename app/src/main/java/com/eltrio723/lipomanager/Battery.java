package com.eltrio723.lipomanager;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "batteries")
public class Battery {

    static double minCellVoltage = 3.6;
    static double maxCellVoltage = 4.2;
    static double storageCellVoltage = 3.8;

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;

    @ColumnInfo(name = "capacity")
    int capacity;

    @ColumnInfo(name = "discharge")
    int discharge;

    @ColumnInfo(name = "cells")
    int cells;

    @ColumnInfo(name = "voltage")
    double voltage;

    @ColumnInfo(name = "currentVoltage")
    double currentVoltage;

    @ColumnInfo(name = "brand")
    String brand;

    @ColumnInfo(name = "buyDate")
    @TypeConverters(DatesConverters.class)
    Date buyDate;

    @ColumnInfo(name = "timesUsed")
    int timesUsed;

    @ColumnInfo(name = "state")
    @TypeConverters(StatesConverters.class)
    State state;

    @ColumnInfo(name = "notes")
    @TypeConverters(StringListsConverters.class)
    ArrayList<String> notes;

    @ColumnInfo(name = "lastUsed")
    @TypeConverters(DatesConverters.class)
    Date lastUsed;

    @ColumnInfo(name = "lastCharged")
    @TypeConverters(DatesConverters.class)
    Date lastCharged;

    @ColumnInfo(name = "lastModified")
    @TypeConverters(DatesConverters.class)
    Date lastModified;

    @ColumnInfo(name = "connector")
    @TypeConverters(ConnectorsConverters.class)
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
        lastUsed = Calendar.getInstance().getTime();
        lastCharged = Calendar.getInstance().getTime();
        lastModified = Calendar.getInstance().getTime();
        connector = Connector.OTHER;
    }

    public Battery(int id, int capacity, int discharge, int cells){
        this.id = id;
        this.capacity = capacity;
        this.discharge = discharge;
        this.cells = cells;
        voltage = cells*3.7;
        currentVoltage = voltage;
        this.brand = "";
        buyDate = Calendar.getInstance().getTime();
        timesUsed = 0;
        state = State.NEW;
        notes = new ArrayList<String>();
        lastUsed = Calendar.getInstance().getTime();
        lastCharged = Calendar.getInstance().getTime();
        lastModified = Calendar.getInstance().getTime();
        this.connector = Connector.OTHER;
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
        lastUsed = Calendar.getInstance().getTime();
        lastCharged = Calendar.getInstance().getTime();
        lastModified = Calendar.getInstance().getTime();
        this.connector = connector;
    }

    public Battery(int id, int capacity, int discharge, int cells, double currentVoltage, int timesUsed, String brand, State state, Connector connector, Date buyDate, Date lastUsed, Date lastCharged){
        this.id = id;
        this.capacity = capacity;
        this.discharge = discharge;
        this.cells = cells;
        voltage = cells*3.7;
        this.currentVoltage = currentVoltage;
        this.brand = brand;
        this.buyDate = buyDate;
        this.timesUsed = timesUsed;
        this.state = state;
        notes = new ArrayList<String>();
        this.lastUsed = lastUsed;
        this.lastCharged = lastCharged;
        lastModified = Calendar.getInstance().getTime();
        this.connector = connector;
    }


    public int getId() { return id; }

    public int getCapacity() { return capacity; }

    public int getDischarge() { return discharge; }

    public int getCells() { return cells; }

    public double getVoltage() { return voltage; }

    public double getCurrentVoltage() { return currentVoltage; }

    public String getBrand() { return brand; }

    public Date getBuyDate() { return buyDate; }

    public int getTimesUsed() { return timesUsed; }

    public State getState() { return state; }

    public ArrayList<String> getNotes() { return notes; }

    public Date getLastUsed() { return lastUsed; }

    public Date getLastCharged() { return lastCharged; }

    public Date getLastModified() { return lastModified; }

    public Connector getConnector() { return connector; }


    public void setId(int id){ this.id = id; }

    public void setCapacity(int capacity) { this.capacity = capacity; }

    public void setDischarge(int discharge) { this.discharge = discharge; }

    public void setCells(int cells) { this.cells = cells; }

    public void setVoltage(double voltage) { this.voltage = voltage; }

    public void setCurrentVoltage(double currentVoltage) { this.currentVoltage = currentVoltage; }

    public void setBrand(String brand) { this.brand = brand; }

    public void setBuyDate(Date buyDate) { this.buyDate = buyDate; }

    public void setTimesUsed(int timesUsed) { this.timesUsed = timesUsed; }

    public void setState(State state) { this.state = state; }

    public void setNotes(ArrayList<String> notes) { this.notes = notes; }

    public void setLastUsed(Date lastUsed) { this.lastUsed = lastUsed; }

    public void setLastCharged(Date lastCharged) { this.lastCharged = lastCharged; }

    public void setLastModified(Date lastModified) { this.lastModified = lastModified; }

    public void setConnector(Connector connector) { this.connector = connector; }


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
        if (state != State.DEPLETED){
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
            setLastUsed(Calendar.getInstance().getTime());
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
                setLastUsed(Calendar.getInstance().getTime());
            }
            else
                state = State.USED;
            return true;
        }
        return false;
    }

    Boolean startCharge() {
        if (state != State.CHARGING && state != State.CHARGED){
            state = State.CHARGING;
            return true;
        }
        return false;
    }

    Boolean endCharge() {
        if (state == State.CHARGING){
            setCurrentVoltage(getChargedVoltage());
            state = State.CHARGED;
            setLastCharged(Calendar.getInstance().getTime());
            return true;
        }
        return false;
    }


    void addNote(String s){
        notes.add(s);
    }

    void store(double cv){
        currentVoltage = cv;
        setState(State.STORED);
    }

    void deleteNote(int p){
        notes.remove(p);
    }

}
