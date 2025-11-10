package com.ifcompany.smart_house_01.retrofit.model.receive;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Retrofit_ReceiveAlertListModel {

    @SerializedName("user")
    private String user;
    @SerializedName("items")
    private List<Retrofit_ReceiveAlertListModel_item> items;


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    public List<Retrofit_ReceiveAlertListModel_item> getItem() {
        return items;
    }

    public void setItem(List<Retrofit_ReceiveAlertListModel_item> item) {
        this.items = item;
    }


    public class Retrofit_ReceiveAlertListModel_item{
        @SerializedName("id")
        private String id;
        @SerializedName("title")
        private String title;
        @SerializedName("content")
        private String content;
        @SerializedName("time")
        private String time;
        @SerializedName("read")
        private Boolean read;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Boolean getRead() {
            return read;
        }

        public void setRead(Boolean read) {
            this.read = read;
        }
    }

}



