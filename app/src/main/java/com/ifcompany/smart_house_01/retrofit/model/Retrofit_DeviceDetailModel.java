package com.ifcompany.smart_house_01.retrofit.model;


public class Retrofit_DeviceDetailModel {

    private String id;  //id
    private boolean comp;  //컴프
    private boolean pump;   //펌프
    private float temp;   //온도
    private float humi;   //습도
    private boolean f_fan;  //유동팬
    private boolean e_fan; //배기팬
    private boolean aircon; //냉난방기
    private float co2_sensor;   //co2 센서
    private boolean co2_creator;    //co2 발생기
    private float ph_ec_sensor; //ph ec 센서
    private boolean led;    //led
    private String cctv;    //cctv url
    private int auto;   //자동수동연결여부
    private String b_time; // 최근 시작 시간

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getComp() {
        return comp;
    }

    public void setComp(boolean comp) {
        this.comp = comp;
    }

    public boolean getPump() {
        return pump;
    }

    public void setPump(boolean pump) {
        this.pump = pump;
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

    public boolean getF_fan() {
        return f_fan;
    }

    public void setF_fan(boolean f_fan) {
        this.f_fan = f_fan;
    }

    public boolean getE_fan() {
        return e_fan;
    }

    public void setE_fan(boolean e_fan) {
        this.e_fan = e_fan;
    }

    public boolean getAircon() {
        return aircon;
    }

    public void setAircon(boolean aircon) {
        this.aircon = aircon;
    }

    public float getCo2_sensor() {
        return co2_sensor;
    }

    public void setCo2_sensor(float co2_sensor) {
        this.co2_sensor = co2_sensor;
    }

    public boolean getCo2_creator() {
        return co2_creator;
    }

    public void setCo2_creator(boolean co2_creator) {
        this.co2_creator = co2_creator;
    }

    public float getPh_ec_sensor() {
        return ph_ec_sensor;
    }

    public void setPh_ec_sensor(float ph_ec_sensor) {
        this.ph_ec_sensor = ph_ec_sensor;
    }

    public boolean getLed() {
        return led;
    }

    public void setLed(boolean led) {
        this.led = led;
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

    public String getB_time() {
        return b_time;
    }

    public void setB_time(String b_time) {
        this.b_time = b_time;
    }




}