package com.ifcompany.smart_house_01.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Retrofit_DeviceSimpleListModel {

    @SerializedName("items")
    private List<Retrofit_DeviceSimpeModel_item> items;


    public List<Retrofit_DeviceSimpeModel_item> getItem() {
        return items;
    }

    public void setItem(List<Retrofit_DeviceSimpeModel_item> item) {
        this.items = item;
    }


    public class Retrofit_DeviceSimpeModel_item{
        @SerializedName("id")
        private String id;  //id
        @SerializedName("temp")
        private float temp;   //온도
        @SerializedName("humi")
        private float humi;   //습도
        @SerializedName("cctv")
        private String cctv;    //cctv url
        @SerializedName("auto")
        private int auto;    //자동수동연결여부

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
        public float getTemp() {
            return temp;
        }
        public void setTemp(float temp) {
            this.temp = temp;
        }
        public float getHumi() {
            return humi;
        }
        public void setHumi(float humi) {
            this.humi = humi;
        }
        public String getCctv() {
            return cctv;
        }
        public void setCctv(String cctv) {
            this.cctv = cctv;
        }
        public int getAuto() {
            return auto;
        }
        public void setAuto(int auto) {
            this.auto = auto;
        }
    }

}
