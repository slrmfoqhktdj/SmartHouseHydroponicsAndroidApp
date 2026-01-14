package com.ifcompany.smart_house_hydroponics.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class Retrofit_JWTPairModel {
    @SerializedName("access_token")
    private String access_token;  //엑세스 토큰
    @SerializedName("refresh_token")
    private String refresh_token;
    @SerializedName("expires_in")
    private int expires_in;    //유효기간

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
    public int getExpires_in() { return expires_in; }
    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }


}
