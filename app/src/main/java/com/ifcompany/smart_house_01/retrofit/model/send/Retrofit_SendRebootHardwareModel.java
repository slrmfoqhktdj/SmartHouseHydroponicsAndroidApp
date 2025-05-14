package com.ifcompany.smart_house_01.retrofit.model.send;

import com.google.gson.annotations.SerializedName;

public class Retrofit_SendRebootHardwareModel {

    @SerializedName("id")
    private String id;

    public Retrofit_SendRebootHardwareModel(){

    }

    public Retrofit_SendRebootHardwareModel(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
