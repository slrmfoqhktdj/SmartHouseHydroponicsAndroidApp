package com.ifcompany.smart_house_hydroponics.retrofit.model.receive;


import com.google.gson.annotations.SerializedName;

public class Retrofit_ReceiveLogOutModel {

    @SerializedName("success")
    private  Boolean success;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}



