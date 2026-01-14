package com.ifcompany.smart_house_hydroponics.retrofit.model.send;

import com.google.gson.annotations.SerializedName;

public class Retrofit_SendLoginModel {

    @SerializedName("email")
    private String Email;
    @SerializedName("pwd")
    private String PWD;

    public Retrofit_SendLoginModel(){

    }

    public Retrofit_SendLoginModel(String email, String pwd){
        this.Email = email;
        this.PWD = pwd;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPWD() {
        return PWD;
    }

    public void setPWD(String PWD) {
        this.PWD = PWD;
    }

}
