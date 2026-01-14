package com.ifcompany.smart_house_hydroponics.retrofit.model.receive;


import com.google.gson.annotations.SerializedName;

public class Retrofit_ReceiveOSIDModel {

    @SerializedName("os_id")
    private String os_id;
    @SerializedName("result")
    private boolean result;

    public String getOs_id() {
        return os_id;
    }
    public void setOs_id(String os_id) {
        this.os_id = os_id;
    }
    public boolean getResult() {
        return result;
    }
    public void setResult(boolean result) {
        this.result = result;
    }

}



