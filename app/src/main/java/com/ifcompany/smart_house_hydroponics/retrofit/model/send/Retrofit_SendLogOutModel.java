package com.ifcompany.smart_house_hydroponics.retrofit.model.send;

import com.google.gson.annotations.SerializedName;

public class Retrofit_SendLogOutModel {

    @SerializedName("email")
    private String Email;
    @SerializedName("refresh_token")
    private String Refresh_token;

    public Retrofit_SendLogOutModel(){

    }

    public Retrofit_SendLogOutModel(String email, String refresh_token){
        this.Email = email;
        this.Refresh_token = refresh_token;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getRefresh_token() {
        return Refresh_token;
    }

    public void setRefresh_token(String Refresh_token) {
        this.Refresh_token = Refresh_token;
    }

}
