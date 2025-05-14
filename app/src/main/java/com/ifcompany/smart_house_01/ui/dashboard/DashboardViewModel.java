package com.ifcompany.smart_house_01.ui.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ifcompany.smart_house_01.ui.dashboard.model.DeviceAppointModel;

import java.util.ArrayList;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> deviceid;
    private MutableLiveData<Integer> auto;
    private MutableLiveData<ArrayList<String>> deviceid_list;
//    private MutableLiveData<ArrayList<DeviceAppointModel>> itemlist;
    private MutableLiveData<ArrayList<String>> devicenick_list;
    
    private MutableLiveData<String> compump_startt;
    private MutableLiveData<String> compump_stopt;
    private MutableLiveData<Boolean> compump_onoff;

    private MutableLiveData<String> ffan_startt;
    private MutableLiveData<String> ffan_stopt;
    private MutableLiveData<Boolean> ffan_onoff;

    private MutableLiveData<String> efan_startt;
    private MutableLiveData<String> efan_stopt;
    private MutableLiveData<Boolean> efan_onoff;

    private MutableLiveData<String> aircon_startt;
    private MutableLiveData<String> aircon_stopt;
    private MutableLiveData<Boolean> aircon_onoff;

    private MutableLiveData<String> co2creator_startt;
    private MutableLiveData<String> co2creator_stopt;
    private MutableLiveData<Boolean> co2creator_onoff;

    private MutableLiveData<String> led_startt;
    private MutableLiveData<String> led_stopt;
    private MutableLiveData<Boolean> led_onoff;
    
    
    public MutableLiveData<String> getDeviceid()
    {
        if(deviceid == null)
            deviceid = new MutableLiveData<>(null);
        return deviceid;
    }
    public void setDeviceid(String deviceid) {
        if(this.deviceid == null){
            this.deviceid = new MutableLiveData<>(deviceid);
        }
        this.deviceid.setValue(deviceid);
    }
    public MutableLiveData<Integer> getDeviceAuto()
    {
        if(auto == null)
            auto = new MutableLiveData<>(2);
        return auto;
    }
    public void setDeviceAuto(int deviceid) {
        if(this.auto == null){
            this.auto = new MutableLiveData<>(deviceid);
        }
        this.auto.setValue(deviceid);
    }

    public MutableLiveData<ArrayList<String>> getDeviceidList()
    {
        if(deviceid_list == null)
            deviceid_list = new MutableLiveData<>(null);
        return deviceid_list;
    }
    public void setDeviceidList(ArrayList<String> deviceid) {
        if(this.deviceid_list == null){
            this.deviceid_list = new MutableLiveData<>(deviceid);
        }
        this.deviceid_list.setValue(deviceid);
    }

    public MutableLiveData<ArrayList<String>> getDevicenickList()
    {
        if(devicenick_list == null)
            devicenick_list = new MutableLiveData<>(null);
        return devicenick_list;
    }
    public void setDevicenickList(ArrayList<String> devicenick) {
        if(this.devicenick_list == null){
            this.devicenick_list = new MutableLiveData<>(devicenick);
        }
        this.devicenick_list.setValue(devicenick);
    }

//    public MutableLiveData<ArrayList<DeviceAppointModel>> getItemlist()
//    {
//        if(itemlist == null)
//            itemlist = new MutableLiveData<>(new ArrayList<>());
//        return itemlist;
//    }
//    public void setItemlist(ArrayList<DeviceAppointModel> itemlist) {
//        if(this.itemlist == null){
//            this.itemlist = new MutableLiveData<>(itemlist);
//        }
//        this.itemlist.setValue(itemlist);
//    }





    public MutableLiveData<String> getCompump_startt()
    {
        if(compump_startt == null)
            compump_startt = new MutableLiveData<>(null);
        return compump_startt;
    }
    public void setCompump_startt(String time) {
        if(this.compump_startt == null){
            this.compump_startt = new MutableLiveData<>(time);
        }
        this.compump_startt.setValue(time);
    }

    public MutableLiveData<String> getCompump_stopt()
    {
        if(compump_stopt == null)
            compump_stopt = new MutableLiveData<>(null);
        return compump_stopt;
    }
    public void setCompump_stopt(String time) {
        if(this.compump_stopt == null){
            this.compump_stopt = new MutableLiveData<>(time);
        }
        this.compump_stopt.setValue(time);
    }

    public MutableLiveData<Boolean> getCompump_onoff()
    {
        if(compump_onoff == null)
            compump_onoff = new MutableLiveData<>(false);
        return compump_onoff;
    }
    public void setCompump_onoff(Boolean onoff) {
        if(this.compump_onoff == null){
            this.compump_onoff = new MutableLiveData<>(onoff);
        }
        this.compump_onoff.setValue(onoff);
    }



    public MutableLiveData<String> getFfan_startt()
    {
        if(ffan_startt == null)
            ffan_startt = new MutableLiveData<>(null);
        return ffan_startt;
    }
    public void setFfan_startt(String time) {
        if(this.ffan_startt == null){
            this.ffan_startt = new MutableLiveData<>(time);
        }
        this.ffan_startt.setValue(time);
    }

    public MutableLiveData<String> getFfan_stopt()
    {
        if(ffan_stopt == null)
            ffan_stopt = new MutableLiveData<>(null);
        return ffan_stopt;
    }
    public void setFfan_stopt(String time) {
        if(this.ffan_stopt == null){
            this.ffan_stopt = new MutableLiveData<>(time);
        }
        this.ffan_stopt.setValue(time);
    }

    public MutableLiveData<Boolean> getFfan_onoff()
    {
        if(ffan_onoff == null)
            ffan_onoff = new MutableLiveData<>(false);
        return ffan_onoff;
    }
    public void setFfan_onoff(Boolean onoff) {
        if(this.ffan_onoff == null){
            this.ffan_onoff = new MutableLiveData<>(onoff);
        }
        this.ffan_onoff.setValue(onoff);
    }


    public MutableLiveData<String> getEfan_startt()
    {
        if(efan_startt == null)
            efan_startt = new MutableLiveData<>(null);
        return efan_startt;
    }
    public void setEfan_startt(String time) {
        if(this.efan_startt == null){
            this.efan_startt = new MutableLiveData<>(time);
        }
        this.efan_startt.setValue(time);
    }

    public MutableLiveData<String> getEfan_stopt()
    {
        if(efan_stopt == null)
            efan_stopt = new MutableLiveData<>(null);
        return efan_stopt;
    }
    public void setEfan_stopt(String time) {
        if(this.efan_stopt == null){
            this.efan_stopt = new MutableLiveData<>(time);
        }
        this.efan_stopt.setValue(time);
    }

    public MutableLiveData<Boolean> getEfan_onoff()
    {
        if(efan_onoff == null)
            efan_onoff = new MutableLiveData<>(false);
        return efan_onoff;
    }
    public void setEfan_onoff(Boolean onoff) {
        if(this.efan_onoff == null){
            this.efan_onoff = new MutableLiveData<>(onoff);
        }
        this.efan_onoff.setValue(onoff);
    }


    public MutableLiveData<String> getAircon_startt()
    {
        if(aircon_startt == null)
            aircon_startt = new MutableLiveData<>(null);
        return aircon_startt;
    }
    public void setAircon_startt(String time) {
        if(this.aircon_startt == null){
            this.aircon_startt = new MutableLiveData<>(time);
        }
        this.aircon_startt.setValue(time);
    }

    public MutableLiveData<String> getAircon_stopt()
    {
        if(aircon_stopt == null)
            aircon_stopt = new MutableLiveData<>(null);
        return aircon_stopt;
    }
    public void setAircon_stopt(String time) {
        if(this.aircon_stopt == null){
            this.aircon_stopt = new MutableLiveData<>(time);
        }
        this.aircon_stopt.setValue(time);
    }

    public MutableLiveData<Boolean> getAircon_onoff()
    {
        if(aircon_onoff == null)
            aircon_onoff = new MutableLiveData<>(false);
        return aircon_onoff;
    }
    public void setAircon_onoff(Boolean onoff) {
        if(this.aircon_onoff == null){
            this.aircon_onoff = new MutableLiveData<>(onoff);
        }
        this.aircon_onoff.setValue(onoff);
    }


    public MutableLiveData<String> getCo2creator_startt()
    {
        if(co2creator_startt == null)
            co2creator_startt = new MutableLiveData<>(null);
        return co2creator_startt;
    }
    public void setCo2creator_startt(String time) {
        if(this.co2creator_startt == null){
            this.co2creator_startt = new MutableLiveData<>(time);
        }
        this.co2creator_startt.setValue(time);
    }

    public MutableLiveData<String> getCo2creator_stopt()
    {
        if(co2creator_stopt == null)
            co2creator_stopt = new MutableLiveData<>(null);
        return co2creator_stopt;
    }
    public void setCo2creator_stopt(String time) {
        if(this.co2creator_stopt == null){
            this.co2creator_stopt = new MutableLiveData<>(time);
        }
        this.co2creator_stopt.setValue(time);
    }

    public MutableLiveData<Boolean> getCo2creator_onoff()
    {
        if(co2creator_onoff == null)
            co2creator_onoff = new MutableLiveData<>(false);
        return co2creator_onoff;
    }
    public void setCo2creator_onoff(Boolean onoff) {
        if(this.co2creator_onoff == null){
            this.co2creator_onoff = new MutableLiveData<>(onoff);
        }
        this.co2creator_onoff.setValue(onoff);
    }

    public MutableLiveData<String> getLed_startt()
    {
        if(led_startt == null)
            led_startt = new MutableLiveData<>(null);
        return led_startt;
    }
    public void setLed_startt(String time) {
        if(this.led_startt == null){
            this.led_startt = new MutableLiveData<>(time);
        }
        this.led_startt.setValue(time);
    }

    public MutableLiveData<String> getLed_stopt()
    {
        if(led_stopt == null)
            led_stopt = new MutableLiveData<>(null);
        return led_stopt;
    }
    public void setLed_stopt(String time) {
        if(this.led_stopt == null){
            this.led_stopt = new MutableLiveData<>(time);
        }
        this.led_stopt.setValue(time);
    }

    public MutableLiveData<Boolean> getLed_onoff()
    {
        if(led_onoff == null)
            led_onoff = new MutableLiveData<>(false);
        return led_onoff;
    }
    public void setLed_onoff(Boolean onoff) {
        if(this.led_onoff == null){
            this.led_onoff = new MutableLiveData<>(onoff);
        }
        this.led_onoff.setValue(onoff);
    }

}