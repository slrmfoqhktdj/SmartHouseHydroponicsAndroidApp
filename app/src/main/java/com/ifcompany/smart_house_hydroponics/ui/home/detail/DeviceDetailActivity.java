package com.ifcompany.smart_house_hydroponics.ui.home.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ifcompany.smart_house_hydroponics.R;
import com.ifcompany.smart_house_hydroponics.databinding.ActivityDeviceDetailBinding;
import com.ifcompany.smart_house_hydroponics.intent.KeyModel;
import com.ifcompany.smart_house_hydroponics.retrofit.Retrofit_Factory;
import com.ifcompany.smart_house_hydroponics.retrofit.Retrofit_Interface;
import com.ifcompany.smart_house_hydroponics.retrofit.model.Retrofit_DeviceDetailModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveAirconModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveCo2CreatorModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveCompModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveEFanModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveFFanModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveLEDModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceivePumpModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendAirconModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendCo2CreatorModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendCompModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendEFanModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendFFanModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendLEDModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendPumpModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceDetailActivity extends AppCompatActivity {

    private Retrofit_Interface retrofitService;
    private String tag = "DeviceDetailActivity";
    private DeviceDetailViewModel deviceDetailViewModel;

    private ActivityDeviceDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retrofitService = Retrofit_Factory.getInstance(getApplicationContext()).create();

        deviceDetailViewModel = new ViewModelProvider(this).get(DeviceDetailViewModel.class);


        binding = DataBindingUtil.setContentView(this,R.layout.activity_device_detail);
        binding.setViewmodel(deviceDetailViewModel);
        binding.setLifecycleOwner(this);

//        binding = ActivityDeviceDetailBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String device_id = intent.getStringExtra(KeyModel.DeviceId);
        String device_nick = intent.getStringExtra(KeyModel.Nick);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        deviceDetailViewModel.setDevice_id(device_id);
        deviceDetailViewModel.setNick(device_nick);

        TextView compTv = binding.deviceDetailComp;
        TextView pumpTv = binding.deviceDetailPump;
        TextView fFanTv = binding.deviceDetailFFan;
        TextView eFanTv = binding.deviceDetailEFan;
        TextView airconTv = binding.deviceDetailAircon;
        TextView co2CreatorTv = binding.deviceDetailCo2creator;
        TextView LEDTv = binding.deviceDetailLed;
        TextView CctvTv = binding.deviceDetailCctv;


        compTv.setOnClickListener(v -> {
            if(CheckAuto())
                sendCMDComp(deviceDetailViewModel.getDevice_id().getValue(),!deviceDetailViewModel.getComp().getValue());
        });

        pumpTv.setOnClickListener(v -> {
            if(CheckAuto())
                sendCMDPump(deviceDetailViewModel.getDevice_id().getValue(),!deviceDetailViewModel.getPump().getValue());
        });

        fFanTv.setOnClickListener(v -> {
            if(CheckAuto())
                sendCMDFFan(deviceDetailViewModel.getDevice_id().getValue(),!deviceDetailViewModel.getFfan().getValue());
        });

        eFanTv.setOnClickListener(v -> {
            if(CheckAuto())
                sendCMDEFan(deviceDetailViewModel.getDevice_id().getValue(),!deviceDetailViewModel.getEfan().getValue());
        });

        airconTv.setOnClickListener(v -> {
            if(CheckAuto())
                sendCMDAircon(deviceDetailViewModel.getDevice_id().getValue(),!deviceDetailViewModel.getAircon().getValue());
        });

        co2CreatorTv.setOnClickListener(v -> {
            if(CheckAuto())
                sendCMDCo2Creator(deviceDetailViewModel.getDevice_id().getValue(),!deviceDetailViewModel.getCo2Creator().getValue());
        });

        LEDTv.setOnClickListener(v -> {
            if(CheckAuto())
                sendCMDLED(deviceDetailViewModel.getDevice_id().getValue(),!deviceDetailViewModel.getLed().getValue());
        });

        CctvTv.setOnClickListener(v -> {
//            Intent websiteIntent = new Intent(Intent.ACTION_VIEW);
//            Uri uri = Uri.parse(deviceDetailViewModel.getCctv().toString());
//            websiteIntent.setData(uri);
//            startActivity(websiteIntent);
        });


        binding.deviceDetailRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDeviceInfo(deviceDetailViewModel.getDevice_id().getValue());
                binding.deviceDetailRefreshLayout.setRefreshing(false);
            }
        });


        getDeviceInfo(deviceDetailViewModel.getDevice_id().getValue());
    }

    //appbar에 있는 뒤로가기 리스너
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean CheckAuto(){
        if(deviceDetailViewModel.getAuto().getValue() == 1){
            return true;
        }else{
            Toast.makeText(getApplicationContext(),"기기가 수동모드이거나 연결이 불안정합니다.",Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void getDeviceInfo(String deviceid){
        retrofitService.GetDeviceInfo(deviceid).enqueue(new Callback<Retrofit_DeviceDetailModel>() {
            @Override
            public void onResponse(Call<Retrofit_DeviceDetailModel> call, Response<Retrofit_DeviceDetailModel> response) {
//                Log.d(tag,  response.body().getId());
//                Log.d(tag,  String.valueOf(response.body().getHumi()));
//                Log.d(tag,  String.valueOf(response.body().getCo2_creator()));

                Log.d(tag,  String.valueOf(response.body().getAuto()));
                deviceDetailViewModel.setComp(response.body().getComp());
                deviceDetailViewModel.setPump(response.body().getPump());
                deviceDetailViewModel.setTemp(response.body().getTemp());
                deviceDetailViewModel.setHumi(response.body().getHumi());
                deviceDetailViewModel.setFfan(response.body().getF_fan());
                deviceDetailViewModel.setEfan(response.body().getE_fan());
                deviceDetailViewModel.setAircon(response.body().getAircon());
                deviceDetailViewModel.setCo2Sensor(response.body().getCo2_sensor());
                deviceDetailViewModel.setCo2Creator(response.body().getCo2_creator());
                deviceDetailViewModel.setPhEcSensor(response.body().getPh_ec_sensor());
                deviceDetailViewModel.setLed(response.body().getLed());
                deviceDetailViewModel.setCctv(response.body().getCctv());
                deviceDetailViewModel.setBtime(response.body().getB_time());
                deviceDetailViewModel.setAuto(response.body().getAuto());
            }

            @Override
            public void onFailure(Call<Retrofit_DeviceDetailModel> call, Throwable t) {
                Log.d(tag, String.valueOf(t));
            }
        });
    }

    private void sendCMDComp(String deviceid,boolean cmd){
        retrofitService.CMD_Comp(new Retrofit_SendCompModel(deviceid,cmd)).enqueue(new Callback<Retrofit_ReceiveCompModel>() {
            @Override
            public void onResponse(Call<Retrofit_ReceiveCompModel> call, Response<Retrofit_ReceiveCompModel> response) {
                try{
                    Log.d(tag,"sendCMDComp : "+response.body().getId()+","+response.body().getResult());
                    if(!deviceid.equals(response.body().getId())){
                        return;
                    }

                    if(response.body().getResult()){
                        deviceDetailViewModel.setComp(cmd);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Retrofit_ReceiveCompModel> call, Throwable t) {
                Log.d(tag, String.valueOf(t));
            }
        });
    }

    private void sendCMDPump(String deviceid,boolean cmd){
        retrofitService.CMD_Pump(new Retrofit_SendPumpModel(deviceid,cmd)).enqueue(new Callback<Retrofit_ReceivePumpModel>() {
            @Override
            public void onResponse(Call<Retrofit_ReceivePumpModel> call, Response<Retrofit_ReceivePumpModel> response) {
                try{
                    Log.d(tag,"sendCMDPump : "+response.body().getId()+","+response.body().getResult());
                    if(!deviceid.equals(response.body().getId())){
                        return;
                    }

                    if(response.body().getResult()){
                        deviceDetailViewModel.setPump(cmd);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Retrofit_ReceivePumpModel> call, Throwable t) {
                Log.d(tag, String.valueOf(t));
            }
        });
    }

    private void sendCMDFFan(String deviceid,boolean cmd){
        retrofitService.CMD_FFan(new Retrofit_SendFFanModel(deviceid,cmd)).enqueue(new Callback<Retrofit_ReceiveFFanModel>() {
            @Override
            public void onResponse(Call<Retrofit_ReceiveFFanModel> call, Response<Retrofit_ReceiveFFanModel> response) {
                try{
                    Log.d(tag,"sendCMDFFan : "+response.body().getId()+","+response.body().getResult());
                    if(!deviceid.equals(response.body().getId())){
                        return;
                    }

                    if(response.body().getResult()){
                        deviceDetailViewModel.setFfan(cmd);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Retrofit_ReceiveFFanModel> call, Throwable t) {
                Log.d(tag, String.valueOf(t));
            }
        });
    }

    private void sendCMDEFan(String deviceid,boolean cmd){
        retrofitService.CMD_EFan(new Retrofit_SendEFanModel(deviceid,cmd)).enqueue(new Callback<Retrofit_ReceiveEFanModel>() {
            @Override
            public void onResponse(Call<Retrofit_ReceiveEFanModel> call, Response<Retrofit_ReceiveEFanModel> response) {
                try{
                    Log.d(tag,"sendCMDEFan : "+response.body().getId()+","+response.body().getResult());
                    if(!deviceid.equals(response.body().getId())){
                        return;
                    }

                    if(response.body().getResult()){
                        deviceDetailViewModel.setEfan(cmd);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Retrofit_ReceiveEFanModel> call, Throwable t) {
                Log.d(tag, String.valueOf(t));
            }
        });
    }

    private void sendCMDAircon(String deviceid,boolean cmd){
        retrofitService.CMD_Aircon(new Retrofit_SendAirconModel(deviceid,cmd)).enqueue(new Callback<Retrofit_ReceiveAirconModel>() {
            @Override
            public void onResponse(Call<Retrofit_ReceiveAirconModel> call, Response<Retrofit_ReceiveAirconModel> response) {
                try{
                    Log.d(tag,"sendCMDAircon : "+response.body().getId()+","+response.body().getResult());
                    if(!deviceid.equals(response.body().getId())){
                        return;
                    }

                    if(response.body().getResult()){
                        deviceDetailViewModel.setAircon(cmd);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Retrofit_ReceiveAirconModel> call, Throwable t) {
                Log.d(tag, String.valueOf(t));
            }
        });
    }

    private void sendCMDCo2Creator(String deviceid,boolean cmd){
        retrofitService.CMD_Co2Creator(new Retrofit_SendCo2CreatorModel(deviceid,cmd)).enqueue(new Callback<Retrofit_ReceiveCo2CreatorModel>() {
            @Override
            public void onResponse(Call<Retrofit_ReceiveCo2CreatorModel> call, Response<Retrofit_ReceiveCo2CreatorModel> response) {
                try{
                    Log.d(tag,"sendCMDCo2Creator : "+response.body().getId()+","+response.body().getResult());
                    if(!deviceid.equals(response.body().getId())){
                        return;
                    }

                    if(response.body().getResult()){
                        deviceDetailViewModel.setCo2Creator(cmd);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Retrofit_ReceiveCo2CreatorModel> call, Throwable t) {
                Log.d(tag, String.valueOf(t));
            }
        });
    }

    private void sendCMDLED(String deviceid,boolean cmd){
        retrofitService.CMD_LED(new Retrofit_SendLEDModel(deviceid,cmd)).enqueue(new Callback<Retrofit_ReceiveLEDModel>() {
            @Override
            public void onResponse(Call<Retrofit_ReceiveLEDModel> call, Response<Retrofit_ReceiveLEDModel> response) {
                try{
                    Log.d(tag,"sendCMDLED : "+response.body().getId()+","+response.body().getResult());
                    if(!deviceid.equals(response.body().getId())){
                        return;
                    }

                    if(response.body().getResult()){
                        deviceDetailViewModel.setLed(cmd);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Retrofit_ReceiveLEDModel> call, Throwable t) {
                Log.d(tag, String.valueOf(t));
            }
        });
    }

}