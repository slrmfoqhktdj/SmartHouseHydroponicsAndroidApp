package com.ifcompany.smart_house_hydroponics.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ifcompany.smart_house_hydroponics.R;
import com.ifcompany.smart_house_hydroponics.databinding.FragmentDashboardBinding;
import com.ifcompany.smart_house_hydroponics.db.DataStoreManager;
//import com.ifcompany.smart_house_hydroponics.db.PreferenceManager;
//import com.ifcompany.smart_house_hydroponics.db.PreferenceModel;
import com.ifcompany.smart_house_hydroponics.retrofit.Retrofit_Factory;
import com.ifcompany.smart_house_hydroponics.retrofit.Retrofit_Interface;
import com.ifcompany.smart_house_hydroponics.retrofit.model.Retrofit_DeviceAppointListModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveAirconModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveCo2CreatorModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveCompModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveEFanModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveFFanModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveLEDModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendAppointAirconModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendAppointCo2CreatorModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendAppointComPumpModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendAppointEFanModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendAppointFFanModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendAppointLEDModel;
import com.ifcompany.smart_house_hydroponics.ui.DeviceStateModel;
import com.ifcompany.smart_house_hydroponics.ui.dashboard.model.DeviceAppointModel;
import com.ifcompany.smart_house_hydroponics.ui.dashboard.model.DeviceAppointNameModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private DashboardViewModel dashboardViewModel;
    private final String tag = "DashboardFragment";
//    private PreferenceManager pm ;
    private DataStoreManager ds;
    private Retrofit_Interface retrofitService;
    private Spinner deviceselected;
    private TextView devicestate;
    private final int initDevicelistPosition = 0;

    private interface SendAppointComPumpResultCallback{
        void onResult(boolean result);
    }
    private interface SendAppointFFanResultCallback{
        void onResult(boolean result);
    }
    private interface SendAppointEFanResultCallback{
        void onResult(boolean result);
    }
    private interface SendAppointAirconResultCallback{
        void onResult(boolean result);
    }
    private interface SendAppointCo2CreatorResultCallback{
        void onResult(boolean result);
    }
    private interface SendAppointLEDResultCallback{
        void onResult(boolean result);
    }




    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.e(tag,"OnCreate");

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

//        pm = PreferenceManager.getInstance(getActivity());
        ds = DataStoreManager.getInstance(getActivity());
        retrofitService = Retrofit_Factory.getInstance(getActivity()).create();

    }


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        Log.e(tag,"OnCreateView");

//        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_dashboard, container, false);
        binding.setViewmodel(dashboardViewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        View root = binding.getRoot();

        deviceselected = binding.appointDeviceSpinner;
        devicestate = binding.appointDeviceAuto;
        ConstraintLayout compump_con = binding.appointCompump;
        ConstraintLayout ffan_con = binding.appointFfan;
        ConstraintLayout efan_con = binding.appointEfan;
        ConstraintLayout aircon_con = binding.appointAircon;
        ConstraintLayout co2creator_con = binding.appointCo2creator;
        ConstraintLayout led_con = binding.appointLed;


        TextView compump_onoff = binding.appointCompumpOnoff;
        TextView ffan_onoff = binding.appointFfanOnoff;
        TextView efan_onoff = binding.appointEfanOnoff;
        TextView aircon_onoff = binding.appointAirconOnoff;
        TextView co2creator_onoff = binding.appointCo2creatorOnoff;
        TextView led_onoff = binding.appointLedOnoff;




        compump_con.setOnClickListener(v -> {

            if(dashboardViewModel.getDeviceAuto().getValue() != 1){
                notMassageAutoState();
                return;
            }

            String[] startT = dashboardViewModel.getCompump_startt().getValue().split(":");
            String[] stopT = dashboardViewModel.getCompump_stopt().getValue().split(":");

            TimeUpdateDialog(dashboardViewModel.getDeviceid().getValue(),DeviceAppointNameModel.ComPumpName,Integer.parseInt(startT[0]),Integer.parseInt(startT[1]),Integer.parseInt(stopT[0]),Integer.parseInt(stopT[1]));
        });

        ffan_con.setOnClickListener(v -> {

            if(dashboardViewModel.getDeviceAuto().getValue() != 1){
                notMassageAutoState();
                return;
            }

            String[] startT = dashboardViewModel.getFfan_startt().getValue().split(":");
            String[] stopT = dashboardViewModel.getFfan_stopt().getValue().split(":");

            TimeUpdateDialog(dashboardViewModel.getDeviceid().getValue(),DeviceAppointNameModel.FFanName,Integer.parseInt(startT[0]),Integer.parseInt(startT[1]),Integer.parseInt(stopT[0]),Integer.parseInt(stopT[1]));
        });

        efan_con.setOnClickListener(v -> {

            if(dashboardViewModel.getDeviceAuto().getValue() != 1){
                notMassageAutoState();
                return;
            }

            String[] startT = dashboardViewModel.getEfan_startt().getValue().split(":");
            String[] stopT = dashboardViewModel.getEfan_stopt().getValue().split(":");

            TimeUpdateDialog(dashboardViewModel.getDeviceid().getValue(),DeviceAppointNameModel.EFanName,Integer.parseInt(startT[0]),Integer.parseInt(startT[1]),Integer.parseInt(stopT[0]),Integer.parseInt(stopT[1]));
        });

        aircon_con.setOnClickListener(v -> {

            if(dashboardViewModel.getDeviceAuto().getValue() != 1){
                notMassageAutoState();
                return;
            }

            String[] startT = dashboardViewModel.getAircon_startt().getValue().split(":");
            String[] stopT = dashboardViewModel.getAircon_stopt().getValue().split(":");

            TimeUpdateDialog(dashboardViewModel.getDeviceid().getValue(),DeviceAppointNameModel.AirconName,Integer.parseInt(startT[0]),Integer.parseInt(startT[1]),Integer.parseInt(stopT[0]),Integer.parseInt(stopT[1]));
        });

        co2creator_con.setOnClickListener(v -> {

            if(dashboardViewModel.getDeviceAuto().getValue() != 1){
                notMassageAutoState();
                return;
            }

            String[] startT = dashboardViewModel.getCo2creator_startt().getValue().split(":");
            String[] stopT = dashboardViewModel.getCo2creator_stopt().getValue().split(":");

            TimeUpdateDialog(dashboardViewModel.getDeviceid().getValue(),DeviceAppointNameModel.Co2CreatorName,Integer.parseInt(startT[0]),Integer.parseInt(startT[1]),Integer.parseInt(stopT[0]),Integer.parseInt(stopT[1]));
        });

        led_con.setOnClickListener(v -> {

            if(dashboardViewModel.getDeviceAuto().getValue() != 1){
                notMassageAutoState();
                return;
            }

            String[] startT = dashboardViewModel.getLed_startt().getValue().split(":");
            String[] stopT = dashboardViewModel.getLed_stopt().getValue().split(":");

            TimeUpdateDialog(dashboardViewModel.getDeviceid().getValue(),DeviceAppointNameModel.LedName,Integer.parseInt(startT[0]),Integer.parseInt(startT[1]),Integer.parseInt(stopT[0]),Integer.parseInt(stopT[1]));
        });

        compump_onoff.setOnClickListener(v -> {

            if(dashboardViewModel.getDeviceAuto().getValue() != 1){
                notMassageAutoState();
                return;
            }

            onOnoffClickFunction(DeviceAppointNameModel.ComPumpName);
        });

        ffan_onoff.setOnClickListener(v -> {

            if(dashboardViewModel.getDeviceAuto().getValue() != 1){
                notMassageAutoState();
                return;
            }

            onOnoffClickFunction(DeviceAppointNameModel.FFanName);
        });

        efan_onoff.setOnClickListener(v -> {

            if(dashboardViewModel.getDeviceAuto().getValue() != 1){
                notMassageAutoState();
                return;
            }

            onOnoffClickFunction(DeviceAppointNameModel.EFanName);
        });

        aircon_onoff.setOnClickListener(v -> {

            if(dashboardViewModel.getDeviceAuto().getValue() != 1){
                notMassageAutoState();
                return;
            }

            onOnoffClickFunction(DeviceAppointNameModel.AirconName);
        });

        co2creator_onoff.setOnClickListener(v -> {

            if(dashboardViewModel.getDeviceAuto().getValue() != 1){
                notMassageAutoState();
                return;
            }

            onOnoffClickFunction(DeviceAppointNameModel.Co2CreatorName);
        });

        led_onoff.setOnClickListener(v -> {

            if(dashboardViewModel.getDeviceAuto().getValue() != 1){
                notMassageAutoState();
                return;
            }

            onOnoffClickFunction(DeviceAppointNameModel.LedName);
        });




        if(dashboardViewModel.getDeviceidList().getValue() == null){
//            ArrayList<String> deviceidlist = new ArrayList<>(pm.getDeviceList(PreferenceModel.device_list));
//            ArrayList<String> devicenicklist = (ArrayList<String>) deviceidlist.clone();
//            devicenicklist.replaceAll(key -> pm.getString(key, key));
//            dashboardViewModel.setDeviceidList(deviceidlist);
//            dashboardViewModel.setDevicenickList(devicenicklist);

            ds.getDeviceList(new DataStoreManager.GetDeviceListCallback() {
                @Override
                public void onSuccess(ArrayList<String> devices) {
                    ArrayList<String> devicenicklist = (ArrayList<String>)devices.clone();
                    devicenicklist.replaceAll(key -> ds.getStringSync(key, key));
                    dashboardViewModel.setDeviceidList(devices);
                    dashboardViewModel.setDevicenickList(devicenicklist);

                    initSpinnerAdapter();
                }

                @Override
                public void onError(Throwable t) {
                    Log.d(tag,t.toString());
                }
            });
        }else{
            getDeviceAppointList(dashboardViewModel.getDeviceid().getValue()==null? dashboardViewModel.getDeviceidList().getValue().get(initDevicelistPosition): dashboardViewModel.getDeviceid().getValue());
        }

        binding.fragmentAppointRefreshLayout.setOnRefreshListener(() -> {
            Log.d(tag,"refreshlayout");

            //닉 초기화
//            ArrayList<String> devicenicklist = new ArrayList<>(pm.getDeviceList(PreferenceModel.device_list));
//            devicenicklist.replaceAll(key -> pm.getString(key, key));
//            dashboardViewModel.setDeviceidList(devicenicklist);

            ds.getDeviceList(new DataStoreManager.GetDeviceListCallback() {
                @Override
                public void onSuccess(ArrayList<String> devices) {
                    devices.replaceAll(key -> ds.getStringSync(key, key));
                    dashboardViewModel.setDeviceidList(devices);
                }

                @Override
                public void onError(Throwable t) {
                    Log.d(tag,t.toString());
                }
            });
            
            
            getDeviceAppointList(dashboardViewModel.getDeviceidList().getValue().get(deviceselected.getSelectedItemPosition()) );
//            getDeviceAppointList(pm.getString(PreferenceModel.jwt_token,null),  "device01");
            binding.fragmentAppointRefreshLayout.setRefreshing(false);
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

//        dashboardViewModel.getDeviceid().observe(getViewLifecycleOwner(),s -> {
//            Log.d(tag,"get Deviceid Change = "+ s);
//            if(s!=null){
//                getDeviceAppointList(pm.getString(PreferenceModel.jwt_token,null), s);
//            }
//
//        });

        dashboardViewModel.getDeviceAuto().observe(getViewLifecycleOwner(),a ->{
            switch (a){
                case DeviceStateModel.pass:
                    devicestate.setTextColor(ContextCompat.getColor(requireContext(),R.color.device_state_pass));
                    break;
                case DeviceStateModel.auto:
                    devicestate.setTextColor(ContextCompat.getColor(requireContext(),R.color.device_state_auto));
                    break;
                default:
                    devicestate.setTextColor(ContextCompat.getColor(requireContext(),R.color.device_state_disconnect));
                    break;
            }
        });
    }

    private void notMassageAutoState(){
        Toast.makeText(getActivity(),"현재 기기의 상태가 수동이거나 서버와 연결되지 않은 상태입니다. 다시 확인해주세요.",Toast.LENGTH_LONG).show();
    }

    private void notMassageFail(){
        Toast.makeText(getActivity(),"명령에 실패했습니다. 기기를 다시 확인해주세요.",Toast.LENGTH_LONG).show();
    }

    private void getDeviceAppointList(String device){
        retrofitService.GetDeviceAppointList(device).enqueue(new Callback<Retrofit_DeviceAppointListModel>() {
            @Override
            public void onResponse(Call<Retrofit_DeviceAppointListModel> call, Response<Retrofit_DeviceAppointListModel> response) {

                String deviceid = response.body().getId();
                int deviceAuto = response.body().getAuto();

                dashboardViewModel.setDeviceAuto(deviceAuto);


                List<DeviceAppointModel> list = response.body().getItem();

                ArrayList<DeviceAppointModel> arrayList = new ArrayList<>(list);

                Log.d(tag,"getDeviceAppointList : "+ deviceid + " size : "+arrayList.size());

//                if(arrayList.size()  == dashboardViewModel.getItemlist().getValue().size()){
//                    return;
//                }


                if(arrayList.size() == 0){
                    dashboardViewModel.setCompump_startt(null);
                    dashboardViewModel.setCompump_stopt(null);
                    dashboardViewModel.setCompump_onoff(false);
                    dashboardViewModel.setFfan_startt(null);
                    dashboardViewModel.setFfan_stopt(null);
                    dashboardViewModel.setFfan_onoff(null);
                    dashboardViewModel.setEfan_startt(null);
                    dashboardViewModel.setEfan_stopt(null);
                    dashboardViewModel.setEfan_onoff(null);
                    dashboardViewModel.setAircon_startt(null);
                    dashboardViewModel.setAircon_stopt(null);
                    dashboardViewModel.setAircon_onoff(null);
                    dashboardViewModel.setCo2creator_startt(null);
                    dashboardViewModel.setCo2creator_stopt(null);
                    dashboardViewModel.setCo2creator_onoff(null);
                    dashboardViewModel.setLed_startt(null);
                    dashboardViewModel.setLed_stopt(null);
                    dashboardViewModel.setLed_onoff(null);
                    return;
                }


                for(int a = 0; a< arrayList.size(); a++){
                    DeviceAppointModel da = arrayList.get(a);
                    switch (da.getName()) {
                        case DeviceAppointNameModel.ComPumpName:
                            dashboardViewModel.setCompump_startt(da.getStartt());
                            dashboardViewModel.setCompump_stopt(da.getStopt());
                            dashboardViewModel.setCompump_onoff(da.getOnoff());
                            break;
                        case DeviceAppointNameModel.FFanName:
                            dashboardViewModel.setFfan_startt(da.getStartt());
                            dashboardViewModel.setFfan_stopt(da.getStopt());
                            dashboardViewModel.setFfan_onoff(da.getOnoff());
                            break;
                        case DeviceAppointNameModel.EFanName:
                            dashboardViewModel.setEfan_startt(da.getStartt());
                            dashboardViewModel.setEfan_stopt(da.getStopt());
                            dashboardViewModel.setEfan_onoff(da.getOnoff());
                            break;
                        case DeviceAppointNameModel.AirconName:
                            dashboardViewModel.setAircon_startt(da.getStartt());
                            dashboardViewModel.setAircon_stopt(da.getStopt());
                            dashboardViewModel.setAircon_onoff(da.getOnoff());
                            break;
                        case DeviceAppointNameModel.Co2CreatorName:
                            dashboardViewModel.setCo2creator_startt(da.getStartt());
                            dashboardViewModel.setCo2creator_stopt(da.getStopt());
                            dashboardViewModel.setCo2creator_onoff(da.getOnoff());
                            break;
                        case DeviceAppointNameModel.LedName:
                            dashboardViewModel.setLed_startt(da.getStartt());
                            dashboardViewModel.setLed_stopt(da.getStopt());
                            dashboardViewModel.setLed_onoff(da.getOnoff());
                            break;
                    }
                }

            }

            @Override
            public void onFailure(Call<Retrofit_DeviceAppointListModel> call, Throwable t) {
                Log.d(tag, String.valueOf(t));
            }
        });
    }

    private void initSpinnerAdapter(){
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,dashboardViewModel.getDevicenickList().getValue());
        spinnerAdapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        deviceselected.setAdapter(spinnerAdapter);


        deviceselected.setSelection(initDevicelistPosition,false);
        dashboardViewModel.setDeviceid(dashboardViewModel.getDeviceidList().getValue().get(initDevicelistPosition));

        deviceselected.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(tag,"onSpinner selected position = "+position + ", id = "+dashboardViewModel.getDeviceidList().getValue().get(position));
                String deviceid = dashboardViewModel.getDeviceidList().getValue().get(position);
                if (deviceid == null){
                    return;
                }
                if(!Objects.equals(dashboardViewModel.getDeviceid().getValue(), deviceid)){
                    dashboardViewModel.setDeviceid(deviceid);

                    getDeviceAppointList(deviceid);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(tag,"onNothingSelected");
            }
        });
    }


    public void onOnoffClickFunction(String category) {

        String device_id = dashboardViewModel.getDeviceid().getValue();

        boolean goal;
        
        switch (category){
            case DeviceAppointNameModel.ComPumpName:
                goal = Boolean.FALSE.equals(dashboardViewModel.getCompump_onoff().getValue());
                SendAppointComPump(device_id,dashboardViewModel.getCompump_startt().getValue(),dashboardViewModel.getCompump_stopt().getValue(),goal, success ->{
                    if(success){
                        dashboardViewModel.setCompump_onoff(goal);
                    }else{
                        notMassageFail();
                    }
                });


                break;
            case DeviceAppointNameModel.FFanName:
                goal = Boolean.FALSE.equals(dashboardViewModel.getFfan_onoff().getValue());
                SendAppointFFan(device_id,dashboardViewModel.getFfan_startt().getValue(),dashboardViewModel.getFfan_stopt().getValue(), goal, success ->{
                    if(success){
                        dashboardViewModel.setFfan_onoff(goal);
                    }else{
                        notMassageFail();
                    }
                });
                break;
            case DeviceAppointNameModel.EFanName:
                goal = Boolean.FALSE.equals(dashboardViewModel.getEfan_onoff().getValue());
                SendAppointEFan(device_id,dashboardViewModel.getEfan_startt().getValue(),dashboardViewModel.getEfan_stopt().getValue(), goal, success ->{
                    if(success){
                        dashboardViewModel.setEfan_onoff(goal);
                    }else{
                        notMassageFail();
                    }
                });
                break;
            case DeviceAppointNameModel.AirconName:
                goal = Boolean.FALSE.equals(dashboardViewModel.getAircon_onoff().getValue());
                SendAppointAircon(device_id,dashboardViewModel.getAircon_startt().getValue(),dashboardViewModel.getAircon_stopt().getValue(), goal, success ->{
                    if(success){
                        dashboardViewModel.setAircon_onoff(goal);
                    }else{
                        notMassageFail();
                    }
                });
                break;
            case DeviceAppointNameModel.Co2CreatorName:
                goal = Boolean.FALSE.equals(dashboardViewModel.getCo2creator_onoff().getValue());
                SendAppointCo2Creator(device_id,dashboardViewModel.getCo2creator_startt().getValue(),dashboardViewModel.getCo2creator_stopt().getValue(), goal, success ->{
                    if(success){
                        dashboardViewModel.setCo2creator_onoff(goal);
                    }else{
                        notMassageFail();
                    }
                });
                break;
            case DeviceAppointNameModel.LedName:
                goal = Boolean.FALSE.equals(dashboardViewModel.getLed_onoff().getValue());
                SendAppointLED(device_id,dashboardViewModel.getLed_startt().getValue(),dashboardViewModel.getLed_stopt().getValue(), goal, success ->{
                    if(success){
                        dashboardViewModel.setLed_onoff(goal);
                    }else{
                        notMassageFail();
                    }
                });
                break;
        }

    }

    public void TimeUpdateDialog(String device_id,String category,int start_hour,int start_min,int stop_hour, int stop_min){


        View updateDialogView = getLayoutInflater().inflate(R.layout.dialog_appoint_update,null);
        TimePicker tp_start_time = updateDialogView.findViewById(R.id.tp_dialog_appoint_start_time);
        TimePicker tp_stop_time = updateDialogView.findViewById(R.id.tp_dialog_appoint_stop_time);

        tp_start_time.setHour(start_hour);
        tp_start_time.setMinute(start_min);
        tp_stop_time.setHour(stop_hour);
        tp_stop_time.setMinute(stop_min);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(updateDialogView);
        builder.setTitle("시간 수정");
        builder.setPositiveButton("확인", (dialog, which) -> {
            
            String startTime = tp_start_time.getHour()+":"+tp_start_time.getMinute();
            String stopTime = tp_stop_time.getHour()+":"+tp_stop_time.getMinute();

            switch (category){
                case DeviceAppointNameModel.ComPumpName:
                    SendAppointComPump(device_id,startTime,stopTime,dashboardViewModel.getCompump_onoff().getValue(), success ->{
                        if(success){
                            dashboardViewModel.setCompump_startt(startTime);
                            dashboardViewModel.setCompump_stopt(stopTime);
                        }else{
                            notMassageFail();
                        }
                    });
                    break;
                case DeviceAppointNameModel.FFanName:
                    SendAppointFFan(device_id,startTime,stopTime,dashboardViewModel.getFfan_onoff().getValue(), success ->{
                        if(success){
                            dashboardViewModel.setFfan_startt(startTime);
                            dashboardViewModel.setFfan_stopt(stopTime);
                        }else{
                            notMassageFail();
                        }
                    });
                    break;
                case DeviceAppointNameModel.EFanName:

                    SendAppointEFan(device_id,startTime,stopTime,dashboardViewModel.getEfan_onoff().getValue(), success ->{
                        if(success){
                            dashboardViewModel.setEfan_startt(startTime);
                            dashboardViewModel.setEfan_stopt(stopTime);
                        }else{
                            notMassageFail();
                        }
                    });
                    break;
                case DeviceAppointNameModel.AirconName:

                    SendAppointAircon(device_id,startTime,stopTime,dashboardViewModel.getAircon_onoff().getValue(), success ->{
                        if(success){
                            dashboardViewModel.setAircon_startt(startTime);
                            dashboardViewModel.setAircon_stopt(stopTime);
                        }else{
                            notMassageFail();
                        }
                    });
                    break;
                case DeviceAppointNameModel.Co2CreatorName:

                    SendAppointCo2Creator(device_id,startTime,stopTime,dashboardViewModel.getCo2creator_onoff().getValue(), success ->{
                        if(success){

                            dashboardViewModel.setCo2creator_startt(startTime);
                            dashboardViewModel.setCo2creator_stopt(stopTime);
                        }else{
                            notMassageFail();
                        }
                    });
                    break;
                case DeviceAppointNameModel.LedName:

                    SendAppointLED(device_id,startTime,stopTime,dashboardViewModel.getLed_onoff().getValue(), success ->{
                        if(success){

                            dashboardViewModel.setLed_startt(startTime);
                            dashboardViewModel.setLed_stopt(stopTime);
                        }else{
                            notMassageFail();
                        }
                    });
                    break;
            }
            
            

        });
        builder.setNegativeButton("취소", (dialog, which) -> {

        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }

    private void SendAppointComPump(String deviceid, String startTime, String stopTime,boolean isCheckd, SendAppointComPumpResultCallback callback){
        Retrofit_SendAppointComPumpModel am = new Retrofit_SendAppointComPumpModel(deviceid,startTime,stopTime,isCheckd);

        retrofitService.ACMD_ComPump(am).enqueue(new Callback<Retrofit_ReceiveCompModel>() {
            @Override
            public void onResponse(Call<Retrofit_ReceiveCompModel> call, Response<Retrofit_ReceiveCompModel> response) {
                callback.onResult(response.body().getResult());
            }

            @Override
            public void onFailure(Call<Retrofit_ReceiveCompModel> call, Throwable t) {
                callback.onResult(false);
            }
        });

    }

    private void SendAppointFFan(String deviceid, String startTime, String stopTime,boolean isCheckd, SendAppointFFanResultCallback callback){
        Retrofit_SendAppointFFanModel am = new Retrofit_SendAppointFFanModel(deviceid,startTime,stopTime,isCheckd);

        retrofitService.ACMD_FFan(am).enqueue(new Callback<Retrofit_ReceiveFFanModel>() {
            @Override
            public void onResponse(Call<Retrofit_ReceiveFFanModel> call, Response<Retrofit_ReceiveFFanModel> response) {
                callback.onResult(response.body().getResult());
            }

            @Override
            public void onFailure(Call<Retrofit_ReceiveFFanModel> call, Throwable t) {
                callback.onResult(false);
            }
        });

    }

    private void SendAppointEFan(String deviceid, String startTime, String stopTime,boolean isCheckd, SendAppointEFanResultCallback callback){
        Retrofit_SendAppointEFanModel am = new Retrofit_SendAppointEFanModel(deviceid,startTime,stopTime,isCheckd);

        retrofitService.ACMD_EFan(am).enqueue(new Callback<Retrofit_ReceiveEFanModel>() {
            @Override
            public void onResponse(Call<Retrofit_ReceiveEFanModel> call, Response<Retrofit_ReceiveEFanModel> response) {
                callback.onResult(response.body().getResult());
            }

            @Override
            public void onFailure(Call<Retrofit_ReceiveEFanModel> call, Throwable t) {
                callback.onResult(false);
            }
        });
    }

    private void SendAppointAircon(String deviceid, String startTime, String stopTime,boolean isCheckd, SendAppointAirconResultCallback callback){
        Retrofit_SendAppointAirconModel am = new Retrofit_SendAppointAirconModel(deviceid,startTime,stopTime,isCheckd);

        retrofitService.ACMD_Aircon(am).enqueue(new Callback<Retrofit_ReceiveAirconModel>() {
            @Override
            public void onResponse(Call<Retrofit_ReceiveAirconModel> call, Response<Retrofit_ReceiveAirconModel> response) {
                callback.onResult(response.body().getResult());
            }

            @Override
            public void onFailure(Call<Retrofit_ReceiveAirconModel> call, Throwable t) {
                callback.onResult(false);
            }
        });

    }

    private void SendAppointCo2Creator(String deviceid, String startTime, String stopTime,boolean isCheckd, SendAppointCo2CreatorResultCallback callback){
        Retrofit_SendAppointCo2CreatorModel am = new Retrofit_SendAppointCo2CreatorModel(deviceid,startTime,stopTime,isCheckd);

        retrofitService.ACMD_Co2Creator(am).enqueue(new Callback<Retrofit_ReceiveCo2CreatorModel>() {
            @Override
            public void onResponse(Call<Retrofit_ReceiveCo2CreatorModel> call, Response<Retrofit_ReceiveCo2CreatorModel> response) {
                callback.onResult(response.body().getResult());
            }

            @Override
            public void onFailure(Call<Retrofit_ReceiveCo2CreatorModel> call, Throwable t) {
                callback.onResult(false);
            }
        });

    }

    private void SendAppointLED(String deviceid, String startTime, String stopTime,boolean isCheckd, SendAppointLEDResultCallback callback){
        Retrofit_SendAppointLEDModel am = new Retrofit_SendAppointLEDModel(deviceid,startTime,stopTime,isCheckd);

        retrofitService.ACMD_LED(am).enqueue(new Callback<Retrofit_ReceiveLEDModel>() {
            @Override
            public void onResponse(Call<Retrofit_ReceiveLEDModel> call, Response<Retrofit_ReceiveLEDModel> response) {
                callback.onResult(response.body().getResult());

            }

            @Override
            public void onFailure(Call<Retrofit_ReceiveLEDModel> call, Throwable t) {
                callback.onResult(false);
            }
        });
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
