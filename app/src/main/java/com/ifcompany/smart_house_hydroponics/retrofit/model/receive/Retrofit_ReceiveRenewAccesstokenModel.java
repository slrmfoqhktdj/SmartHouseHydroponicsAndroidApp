package com.ifcompany.smart_house_hydroponics.retrofit.model.receive;


import com.google.gson.annotations.SerializedName;
import com.ifcompany.smart_house_hydroponics.retrofit.model.Retrofit_JWTPairModel;

public class Retrofit_ReceiveRenewAccesstokenModel {

    @SerializedName("success")
    private  Boolean success;
    @SerializedName("email")
    private String email;
    @SerializedName("token_pair")
    private Retrofit_JWTPairModel token_pair;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Retrofit_JWTPairModel getToken_pair() {
        return token_pair;
    }

    public void setToken_pair(Retrofit_JWTPairModel token_pair) {
        this.token_pair = token_pair;
    }

}



