package com.ifcompany.smart_house_hydroponics.retrofit.model.send;

import com.google.gson.annotations.SerializedName;

public class Retrofit_SendRenewAccessModel {

    @SerializedName("refresh_token")
    private String refresh_token;

    public Retrofit_SendRenewAccessModel(){

    }

    public Retrofit_SendRenewAccessModel(String refresh_token){
        this.refresh_token = refresh_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

}
