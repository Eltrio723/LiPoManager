package com.eltrio723.lipomanager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Battery {

    int id;
    int capacity;
    int discharge;
    int cells;
    float voltage;
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

    public Battery(int capacity, int discharge, int cells, String brand, Connector connector){
        id = 0;
        this.capacity = capacity;
        this.discharge = discharge;
        this.cells = cells;
        voltage = (float)(cells*3.7);
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

    float getVoltage() { return voltage; }

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

    void setVoltage(float voltage) { this.voltage = voltage; }

    void setBrand(String brand) { this.brand = brand; }

    void setBuyDate(Date buyDate) { this.buyDate = buyDate; }

    void setTimesUsed(int timesUsed) { this.timesUsed = timesUsed; }

    void setState(State state) { this.state = state; }

    void setNotes(ArrayList<String> notes) { this.notes = notes; }

    void setLastUsed(Date lastUsed) { this.lastUsed = lastUsed; }

    void setLastCharged(Date lastCharged) { this.lastCharged = lastCharged; }

    void setLastModified(Date lastModified) { this.lastModified = lastModified; }

    void setConnector(Connector connector) { this.connector = connector; }

    
}
