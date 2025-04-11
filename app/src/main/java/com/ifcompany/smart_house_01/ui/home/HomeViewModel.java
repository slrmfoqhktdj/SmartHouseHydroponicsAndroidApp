package com.ifcompany.smart_house_01.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ifcompany.smart_house_01.retrofit.model.Retrofit_DeviceSimpleListModel;
import com.ifcompany.smart_house_01.ui.home.model.DeviceSimpleListModel;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<DeviceSimpleListModel>> itemlist;

    public MutableLiveData<ArrayList<DeviceSimpleListModel>> getItemlist()
    {
        if(itemlist == null)
            itemlist = new MutableLiveData<>(new ArrayList<>());
        return itemlist;
    }
    public void setItemlist(ArrayList<DeviceSimpleListModel> itemlist) {
        if(this.itemlist == null){
            this.itemlist = new MutableLiveData<>(itemlist);
        }
        this.itemlist.setValue(itemlist);
    }
}