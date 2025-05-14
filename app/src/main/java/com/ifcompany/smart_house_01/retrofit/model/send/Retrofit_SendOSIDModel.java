package com.ifcompany.smart_house_01.retrofit.model.send;

import com.google.gson.annotations.SerializedName;

public class Retrofit_SendOSIDModel {

    @SerializedName("email")
    private String Email;
    @SerializedName("os_id")
    private String Os_id;

    public Retrofit_SendOSIDModel(){

    }

    public Retrofit_SendOSIDModel(String email, String os_id){
        this.Email = email;
        this.Os_id = os_id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getOs_id() {
        return Os_id;
    }

    public void setOs_id(String os_id) {
        this.Os_id = os_id;
    }

}
