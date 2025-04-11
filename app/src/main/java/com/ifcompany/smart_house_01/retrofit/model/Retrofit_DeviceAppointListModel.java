package com.ifcompany.smart_house_01.retrofit.model;

import com.google.gson.annotations.SerializedName;
import com.ifcompany.smart_house_01.ui.dashboard.model.DeviceAppointModel;

import java.util.List;

public class Retrofit_DeviceAppointListModel {
    @SerializedName("id")
    private String id;  //기기id
    @SerializedName("items")
    private List<DeviceAppointModel> items;
    @SerializedName("auto")
    private int auto;    //자동수동연결여부

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<DeviceAppointModel> getItem() {
        return items;
    }

    public void setItem(List<DeviceAppointModel> item) {
        this.items = item;
    }
    public int getAuto() { return auto; }
    public void setAuto(int auto) {
        this.auto = auto;
    }


}
