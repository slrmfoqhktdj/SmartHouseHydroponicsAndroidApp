package com.ifcompany.smart_house_01.retrofit.model.receive;


import com.google.gson.annotations.SerializedName;

public class Retrofit_ReceiveRebootSoftwareModel {

        @SerializedName("id")
        private String id;
        @SerializedName("result")
        private boolean result;

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public boolean getResult() {
            return result;
        }
        public void setResult(boolean result) {
            this.result = result;
        }


}



