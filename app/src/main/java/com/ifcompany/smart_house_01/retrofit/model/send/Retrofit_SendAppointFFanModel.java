package com.ifcompany.smart_house_01.retrofit.model.send;

import com.google.gson.annotations.SerializedName;

public class Retrofit_SendAppointFFanModel {

    @SerializedName("id")
    private String id;
    @SerializedName("startt")
    private String startt;
    @SerializedName("stopt")
    private String stopt;
    @SerializedName("onoff")
    private boolean onoff;
    public Retrofit_SendAppointFFanModel(){

    }

    public Retrofit_SendAppointFFanModel(String id, String startt , String stopt, boolean onoff){
        this.id = id;
        this.startt = startt;
        this.stopt = stopt;
        this.onoff = onoff;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    public boolean getOnoff(){return  onoff;}
    public void setOnoff(boolean onoff){this.onoff = onoff;}
}
