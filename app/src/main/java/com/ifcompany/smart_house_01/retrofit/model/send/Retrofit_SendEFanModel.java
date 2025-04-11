package com.ifcompany.smart_house_01.retrofit.model.send;

import com.google.gson.annotations.SerializedName;

public class Retrofit_SendEFanModel {

    @SerializedName("id")
    private String id;
    @SerializedName("value")
    private boolean value;

    public Retrofit_SendEFanModel(){

    }

    public Retrofit_SendEFanModel(String id, boolean value){
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

}
