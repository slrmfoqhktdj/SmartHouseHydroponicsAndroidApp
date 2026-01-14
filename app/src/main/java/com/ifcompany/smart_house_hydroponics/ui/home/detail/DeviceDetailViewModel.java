package com.ifcompany.smart_house_hydroponics.ui.home.detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DeviceDetailViewModel extends ViewModel {
    private MutableLiveData<String> device_id;
    private MutableLiveData<String> nick;
    private MutableLiveData<Boolean> comp;
    private MutableLiveData<Boolean> pump;
    private MutableLiveData<Float> temp;
    private MutableLiveData<Float> humi;
    private MutableLiveData<Boolean> ffan;
    private MutableLiveData<Boolean> efan;
    private MutableLiveData<Boolean> aircon;
    private MutableLiveData<Float> co2Sensor;
    private MutableLiveData<Boolean> co2Creator;
    private MutableLiveData<Float> phEcSensor;
    private MutableLiveData<Boolean> led;
    private MutableLiveData<String> cctv;
    private MutableLiveData<String> b_time;
    private MutableLiveData<Integer> auto;

    public MutableLiveData<String> getDevice_id() {
        if(device_id == null)
            device_id = new MutableLiveData<>(null);
        return device_id;
    }

    public void setDevice_id(String device_id) {
        if(this.device_id == null){
            this.device_id = new MutableLiveData<>(device_id);
        }
        this.device_id.setValue(device_id);
    }

    public MutableLiveData<String> getNick() {
        if(nick == null)
            nick = new MutableLiveData<>(null);
        return nick;
    }

    public void setNick(String nick) {
        if(this.nick == null){
            this.nick = new MutableLiveData<>(nick);
        }
        this.nick.setValue(nick);
    }

    public MutableLiveData<Boolean> getComp() {
        if(comp == null)
            comp = new MutableLiveData<>(false);
        return comp;
    }

    public void setComp(Boolean cump) {
        if(this.comp == null){
            this.comp = new MutableLiveData<>(cump);
        }
        this.comp.setValue(cump);
    }

    public MutableLiveData<Boolean> getPump() {
        if(pump == null)
            pump = new MutableLiveData<>(false);
        return pump;
    }

    public void setPump(Boolean pump) {
        if(this.pump == null){
            this.pump = new MutableLiveData<>(pump);
        }
        this.pump.setValue(pump);
    }

    public MutableLiveData<Float> getTemp() {
        if(temp == null)
            temp = new MutableLiveData<>(0.0f);
        return temp;
    }

    public void setTemp(Float temp) {
        if(this.temp == null){
            this.temp = new MutableLiveData<>(temp);
        }
        this.temp.setValue(temp);
    }

    public MutableLiveData<Float> getHumi() {
        if(humi == null)
            humi = new MutableLiveData<>(0.0f);
        return humi;
    }

    public void setHumi(Float humi) {
        if(this.humi == null){
            this.humi = new MutableLiveData<>(humi);
        }
        this.humi.setValue(humi);
    }

    public MutableLiveData<Boolean> getFfan() {
        if(ffan == null)
            ffan = new MutableLiveData<>(false);
        return ffan;
    }

    public void setFfan(Boolean ffan) {
        if(this.ffan == null){
            this.ffan = new MutableLiveData<>(ffan);
        }
        this.ffan.setValue(ffan);
    }

    public MutableLiveData<Boolean> getEfan() {
        if(efan == null)
            efan = new MutableLiveData<>(false);
        return efan;
    }

    public void setEfan(Boolean efan) {
        if(this.efan == null){
            this.efan = new MutableLiveData<>(efan);
        }
        this.efan.setValue(efan);
    }

    public MutableLiveData<Boolean> getAircon() {
        if(aircon == null)
            aircon = new MutableLiveData<>(false);
        return aircon;
    }

    public void setAircon(Boolean aircon) {
        if(this.aircon == null){
            this.aircon = new MutableLiveData<>(aircon);
        }
        this.aircon.setValue(aircon);
    }

    public MutableLiveData<Float> getCo2Sensor() {
        if(co2Sensor == null)
            co2Sensor = new MutableLiveData<>(0.0f);
        return co2Sensor;
    }

    public void setCo2Sensor(Float co2Sensor) {
        if(this.co2Sensor == null){
            this.co2Sensor = new MutableLiveData<>(co2Sensor);
        }
        this.co2Sensor.setValue(co2Sensor);
    }

    public MutableLiveData<Boolean> getCo2Creator() {
        if(co2Creator == null)
            co2Creator = new MutableLiveData<>(false);
        return co2Creator;
    }

    public void setCo2Creator(Boolean co2Creator) {
        if(this.co2Creator == null){
            this.co2Creator = new MutableLiveData<>(co2Creator);
        }
        this.co2Creator.setValue(co2Creator);
    }

    public MutableLiveData<Float> getPhEcSensor() {
        if(phEcSensor == null)
            phEcSensor = new MutableLiveData<>(0.0f);
        return phEcSensor;
    }

    public void setPhEcSensor(Float phEcSensor) {
        if(this.phEcSensor == null){
            this.phEcSensor = new MutableLiveData<>(phEcSensor);
        }
        this.phEcSensor.setValue(phEcSensor);
    }

    public MutableLiveData<Boolean> getLed() {
        if(led == null)
            led = new MutableLiveData<>(false);
        return led;
    }

    public void setLed(Boolean led) {
        if(this.led == null){
            this.led = new MutableLiveData<>(led);
        }
        this.led.setValue(led);
    }

    public MutableLiveData<String> getCctv() {
        if(cctv == null)
            cctv = new MutableLiveData<>(null);
        return cctv;
    }

    public void setCctv(String cctv) {
        if(this.cctv == null){
            this.cctv = new MutableLiveData<>(cctv);
        }
        this.cctv.setValue(cctv);
    }

    public MutableLiveData<String> getBtime() {
        if(b_time == null)
            b_time = new MutableLiveData<>(null);
        return b_time;
    }

    public void setBtime(String b_time) {
        if(this.b_time == null){
            this.b_time = new MutableLiveData<>(b_time);
        }
        this.b_time.setValue(b_time);
    }

    public MutableLiveData<Integer> getAuto() {
        if(auto == null)
            auto = new MutableLiveData<>(2);
        return auto;
    }

    public void setAuto(int auto) {
        if(this.auto == null){
            this.auto = new MutableLiveData<>(auto);
        }
        this.auto.setValue(auto);
    }




}