package com.ifcompany.smart_house_hydroponics.ui.dashboard.model;

import com.google.gson.annotations.SerializedName;

public class DeviceAppointModel {
    @SerializedName("name")
    private String name;  //name
    @SerializedName("startt")
    private String startt;  //시작시간
    @SerializedName("stopt")
    private String stopt;   //종료시간
    @SerializedName("onoff")
    private boolean onoff;   //onoff

    public DeviceAppointModel(){}
    public DeviceAppointModel(String name,String startt,String stopt,boolean onoff){
        this.name = name;
        this.startt = startt;
        this.stopt = stopt;
        this.onoff = onoff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getStartt() {
        return startt;
    }
    public void setStartt(String startt) {
        this.startt = startt;
    }
    public String getStopt() {
        return stopt;
    }
    public void setStopt(String stopt) {
        this.stopt = stopt;
    }
    public boolean getOnoff() {
        return onoff;
    }
    public void setOnoff(boolean onoff) {
        this.onoff = onoff;
    }

}
