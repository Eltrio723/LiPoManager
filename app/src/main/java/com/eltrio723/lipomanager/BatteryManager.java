package com.eltrio723.lipomanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

class BatteryManager {
    private static final BatteryManager ourInstance = new BatteryManager();
    private static final String STORED_BATTERIES_KEY = "STORED_BATTERIES_KEY";

    List<Battery> batteries;
    Context context;

    static BatteryManager getInstance() {
        return ourInstance;
    }

    private BatteryManager() {
        batteries = new ArrayList<Battery>();
    }

    public void init(Context context){
        this.context = context;
        loadData();
    }

    void setBatteries(List<Battery> bats){
        batteries = bats;
    }

    List<Battery> getBatteries(){
        return batteries;
    }

    void addBattery(Battery bat){
        batteries.add(bat);
        storeData();
    }

    void removeBattery(Battery bat){
        batteries.remove(bat);
        storeData();
    }

    void clear(){
        batteries.clear();
    }


    void storeData(){
        SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = sPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(batteries);
        prefsEditor.putString(STORED_BATTERIES_KEY, json);
        prefsEditor.apply();
    }

    void loadData(){
        SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sPrefs.getString(STORED_BATTERIES_KEY,"");
        Type type = new TypeToken<List<Battery>>(){}.getType();
        this.batteries = gson.fromJson(json, type);
        if(batteries == null)
            batteries = new ArrayList<Battery>();
    }


    int size(){
        return batteries.size();
    }

    int countCharged(){
        int count = 0;
        for(Battery bat : batteries){
            if(bat.isCharged())
                count++;
        }
        return count;
    }



}
