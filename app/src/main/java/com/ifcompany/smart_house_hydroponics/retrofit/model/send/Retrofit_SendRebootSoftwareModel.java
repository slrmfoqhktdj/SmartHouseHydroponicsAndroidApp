package com.ifcompany.smart_house_hydroponics.retrofit.model.send;

import com.google.gson.annotations.SerializedName;

public class Retrofit_SendRebootSoftwareModel {

    @SerializedName("id")
    private String id;

    public Retrofit_SendRebootSoftwareModel(){

    }

    public Retrofit_SendRebootSoftwareModel(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
