package com.ifcompany.smart_house_01.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ifcompany.smart_house_01.retrofit.model.Retrofit_DeviceAppointListModel;
import com.ifcompany.smart_house_01.ui.dashboard.model.DeviceAppointModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;


public class PreferenceManager {
    private static PreferenceManager instance;
    private SharedPreferences sharedPreferences;
    private Gson gson;

//    private Gson gson;

    private PreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PreferenceModel.PreferenceName, Context.MODE_PRIVATE);
        gson = new GsonBuilder().create();
    }

    public static PreferenceManager PreferenceManager() {
        return instance;
    }

    public static synchronized PreferenceManager getInstance(Context context) {
        if (instance == null) {
            instance = new PreferenceManager(context.getApplicationContext());
        }
        return instance;
    }

    public void setString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public void setInt(String key, int value){
        sharedPreferences.edit().putInt(key,value).apply();
    }

    public int getInt(String key, int defalutValue){
        return sharedPreferences.getInt(key,defalutValue);
    }

    public ArrayList<String> getDeviceList(String key){
        String between = "//";
        return new ArrayList<>(Arrays.asList(sharedPreferences.getString(key,null).split(between)));
    }

    public void setDeviceList(String key, ArrayList<String> value){
        StringBuilder saveValue = new StringBuilder();
        String between = "//";

        for(int i = 0; i < value.size(); i ++){
            if(i == 0){
                saveValue.append(value.get(i));
            }else{
                saveValue.append(between).append(value.get(i));
            }
        }

        sharedPreferences.edit().putString(key,saveValue.toString()).apply();
    }

    public String getDeviceId(String nick){
        ArrayList<String> devicelist = new ArrayList<>(getDeviceList(PreferenceModel.device_list));
        String returnid = null;
        for(int i = 0 ; i < devicelist.size(); i++){
            if(devicelist.get(i).equals( nick )) {
                returnid =  devicelist.get(i);
                break;
            }
        }

        return returnid;
    }

    public Retrofit_DeviceAppointListModel getDeviceAppoint(String id){
        String key = id+"_apt";
        String value = sharedPreferences.getString(key,null);
        if(value == null) return  null;

        return gson.fromJson(value,Retrofit_DeviceAppointListModel.class);
    }

    public void setDeviceAppoint(Retrofit_DeviceAppointListModel model){
        String key = model.getId()+"_apt";
        String value = gson.toJson(model);
        sharedPreferences.edit().putString(key,value).apply();

    }

//    public void setArrayListNickName(ArrayList<DeviceNickNameModel> item){
//        String json = gson.toJson(item);
//        sharedPreferences.edit().putString(PreferenceModel.device_item_nick,json).apply();
//    }
//
//    public ArrayList<DeviceNickNameModel> getArrayListNickName(){
//        String json = sharedPreferences.getString(PreferenceModel.device_item_nick,null);
//        Type type = new TypeToken<ArrayList<DeviceNickNameModel>>() {}.getType();
//        return gson.fromJson(json,type);
//    }

}