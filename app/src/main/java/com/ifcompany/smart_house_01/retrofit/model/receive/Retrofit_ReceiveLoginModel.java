package com.ifcompany.smart_house_01.retrofit.model.receive;


import com.google.gson.annotations.SerializedName;

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
        @SerializedName("email")
        private String email;
        @SerializedName("name")
        private String name;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}



