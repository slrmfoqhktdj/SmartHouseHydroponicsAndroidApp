package com.ifcompany.smart_house_hydroponics.retrofit.model.receive;


import com.google.gson.annotations.SerializedName;
import com.ifcompany.smart_house_hydroponics.retrofit.model.Retrofit_JWTPairModel;

public class Retrofit_ReceiveLoginModel {

    @SerializedName("item")
    private Retrofit_ReceiveLoginModel_item item;

    public Retrofit_ReceiveLoginModel_item getItem() {
        return item;
    }

    public void setItem(Retrofit_ReceiveLoginModel_item item) {
        this.item = item;
    }


    public class Retrofit_ReceiveLoginModel_item{
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

}



