package com.eltrio723.lipomanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.eltrio723.lipomanager.database.BatteryRepository;
import com.eltrio723.lipomanager.local.BatteryDataSource;
import com.eltrio723.lipomanager.local.BatteryDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

class BatteryManager {
    private static final BatteryManager ourInstance = new BatteryManager();
    private static final String STORED_BATTERIES_KEY = "STORED_BATTERIES_KEY";

    List<Battery> batteryList;
    Context context;

    private CompositeDisposable compositeDisposable;
    private BatteryRepository batteryRepository;
    private BatteryDatabase batteryDatabase;

    static BatteryManager getInstance() {
        return ourInstance;
    }

    private BatteryManager() {
        batteryList = new ArrayList<Battery>();
        compositeDisposable = new CompositeDisposable();
    }

    public void init(Context context){
        this.context = context;
        batteryDatabase = BatteryDatabase.getInstance(context);
        batteryRepository = BatteryRepository.getInstance(BatteryDataSource.getInstance(batteryDatabase.batteryDAO()));
        loadData();
    }

    void setBatteryList(List<Battery> bats){
        batteryList = bats;
    }

    List<Battery> getBatteryList(){
        return batteryList;
    }

    void addBattery(final Battery bat){
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception{
                batteryRepository.insertBattery(bat);
                e.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                               @Override
                               public void accept(Object o) throws Exception {
                                   Toast.makeText(context, R.string.battery_added, Toast.LENGTH_SHORT).show();
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(context, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                loadData();
                            }
                        }

                );
    }

    void deleteBattery(final Battery bat){
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception{
                batteryRepository.deleteBattery(bat);
                e.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                               @Override
                               public void accept(Object o) throws Exception {
                                   Toast.makeText(context, R.string.battery_deleted, Toast.LENGTH_SHORT).show();
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(context, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                loadData();
                            }
                        }

                );
    }

    void updateBattery(final Battery bat){
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception{
                batteryRepository.updateBattery(bat);
                e.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                               @Override
                               public void accept(Object o) throws Exception {
                                   Toast.makeText(context, R.string.battery_updated, Toast.LENGTH_SHORT).show();
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(context, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                loadData();
                            }
                        }

                );
    }

    void deleteAllBatteries(){
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception{
                batteryRepository.deleteAllBatteries();
                e.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                               @Override
                               public void accept(Object o) throws Exception {
                                   Toast.makeText(context, R.string.delete_all_batteries, Toast.LENGTH_SHORT).show();
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(context, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                loadData();
                            }
                        }

                );
    }

    Battery getBatteryById(int id){
        for (Battery bat : batteryList){
            if(bat.getId()==id){
                return bat;
            }
        }
        return null;
    }

    Battery getBatteryByIndex(int index){
        return batteryList.get(index);
    }


    void storeBattery(Battery bat, String currVolt){
        double currentVoltage;
        if(!currVolt.isEmpty())
            currentVoltage = Double.parseDouble(currVolt);
        else
            currentVoltage = 3.8*bat.getCells();
        bat.store(currentVoltage);
    }


    /*void storeData(){
        SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = sPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(batteryList);
        prefsEditor.putString(STORED_BATTERIES_KEY, json);
        prefsEditor.apply();
    }*/

    /*void loadData22(){
        SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sPrefs.getString(STORED_BATTERIES_KEY,"");
        Type type = new TypeToken<List<Battery>>(){}.getType();
        this.batteryList = gson.fromJson(json, type);
        if(batteryList == null)
            batteryList = new ArrayList<Battery>();
    }*/

    private void loadData(){
        Disposable disposable = batteryRepository.getAllBatteries()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Battery>>() {
                    @Override
                    public void accept(List<Battery> batteries) throws Exception {
                        onGetAllBatteriesSuccess(batteries);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(context,""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void onGetAllBatteriesSuccess(List<Battery> batteries) {
        batteryList.clear();
        batteryList.addAll(batteries);
    }


    int size(){
        return batteryList.size();
    }

    int countCharged(){
        int count = 0;
        for(Battery bat : batteryList){
            if(bat.isCharged())
                count++;
        }
        return count;
    }

    void startCharge(Battery bat){
        if(bat!=null)
            bat.startCharge();
    }

    void endCharge(Battery bat){
        if(bat!=null)
            bat.endCharge();
    }

    void startUse(Battery bat){
        if(bat!=null)
            bat.startUse();
    }

    void endUse(Battery bat){
        if(bat!=null)
            bat.endUse();
    }

    void endUse(Battery bat, String s){
        double currentVoltage;
        if(bat!=null){
            if(!s.isEmpty())
                currentVoltage = Double.parseDouble(s);
            else
                currentVoltage = 4.2*bat.getCells();
            bat.endUse(currentVoltage);
        }

    }

    State getState(Battery bat){
        if(bat!=null)
            return bat.getState();
        return State.OTHER;
    }


}
