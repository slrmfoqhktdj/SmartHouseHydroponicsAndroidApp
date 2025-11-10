package com.ifcompany.smart_house_01.ui.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ifcompany.smart_house_01.R;
import com.ifcompany.smart_house_01.databinding.FragmentSettingsBinding;
import com.ifcompany.smart_house_01.db.PreferenceManager;
import com.ifcompany.smart_house_01.db.PreferenceModel;
import com.ifcompany.smart_house_01.retrofit.Retrofit_Factory;
import com.ifcompany.smart_house_01.retrofit.Retrofit_Interface;
import com.ifcompany.smart_house_01.retrofit.model.receive.Retrofit_ReceiveRebootHardwareModel;
import com.ifcompany.smart_house_01.retrofit.model.receive.Retrofit_ReceiveRebootSoftwareModel;
import com.ifcompany.smart_house_01.retrofit.model.send.Retrofit_SendRebootHardwareModel;
import com.ifcompany.smart_house_01.retrofit.model.send.Retrofit_SendRebootSoftwareModel;
import com.ifcompany.smart_house_01.ui.dashboard.model.DeviceAppointNameModel;
import com.ifcompany.smart_house_01.ui.login.LoginActivity;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private String tag = "SettingsFragment";
    private final Retrofit_Interface retrofitService = Retrofit_Factory.create();
    private PreferenceManager pm ;
    private String selected_device_id = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        pm = PreferenceManager.getInstance(getActivity());

        Log.e(tag,"OnCreateView");
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        ConstraintLayout logout = binding.settingsLogout;
        ConstraintLayout reboot = binding.settingsReboot;

        logout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("로그아웃");
            builder.setMessage("로그아웃을 하시겠습니까?");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    OneSignal.logout();

                    pm.setString(PreferenceModel.isEmail,null);
                    pm.setString(PreferenceModel.isPw,null);
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);

                    getActivity().finish();

                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        reboot.setOnClickListener(v -> {
            DeviceRebootDialog();
        });

        return root;
    }

    private void sendCMDSoftwareReboot(String token,String deviceid){
        retrofitService.CMD_Reboot_Software(token,new Retrofit_SendRebootSoftwareModel(deviceid)).enqueue(new Callback<Retrofit_ReceiveRebootSoftwareModel>() {
            @Override
            public void onResponse(Call<Retrofit_ReceiveRebootSoftwareModel> call, Response<Retrofit_ReceiveRebootSoftwareModel> response) {
                try{
                    Log.d(tag,"sendCMDRebootSoftware : "+response.body().getId()+","+response.body().getResult());
                    if(deviceid.equals(response.body().getId())){
                        Toast.makeText(getActivity(),"소프트웨어 재부팅명령을 성공적으로 발송했습니다.",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Retrofit_ReceiveRebootSoftwareModel> call, Throwable t) {
                Log.d(tag, String.valueOf(t));
            }
        });
    }

    private void sendCMDHardwareReboot(String token,String deviceid){
        retrofitService.CMD_Reboot_Hardware(token,new Retrofit_SendRebootHardwareModel(deviceid)).enqueue(new Callback<Retrofit_ReceiveRebootHardwareModel>() {
            @Override
            public void onResponse(Call<Retrofit_ReceiveRebootHardwareModel> call, Response<Retrofit_ReceiveRebootHardwareModel> response) {
                try{
                    Log.d(tag,"sendCMDRebootHardware : "+response.body().getId()+","+response.body().getResult());
                    if(deviceid.equals(response.body().getId())){
                        Toast.makeText(getActivity(),"하드웨어 재부팅명령을 성공적으로 발송했습니다.",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Retrofit_ReceiveRebootHardwareModel> call, Throwable t) {
                Log.d(tag, String.valueOf(t));
            }
        });
    }

    public void DeviceRebootDialog(){

        View rebootDialogView = getLayoutInflater().inflate(R.layout.dialog_detail_reboot,null);
        Spinner sn_device_list = rebootDialogView.findViewById(R.id.reboot_device_spinner);
        RadioGroup rg_reboot = rebootDialogView.findViewById(R.id.rg_detail_reboot);

        ArrayList<String> deviceidlist = new ArrayList<>(pm.getDeviceList(PreferenceModel.device_list));
        selected_device_id = deviceidlist.get(0);

        ArrayList<String> devicenicklist = (ArrayList<String>)deviceidlist.clone();
        devicenicklist.replaceAll(key -> pm.getString(key, key));

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,devicenicklist);
        spinnerAdapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);

        sn_device_list.setAdapter(spinnerAdapter);
        sn_device_list.setSelection(0,false);

        sn_device_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(tag,"onSpinner selected position = "+position + ", id = "+deviceidlist.get(position));

                selected_device_id = deviceidlist.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(tag,"onNothingSelected");
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rebootDialogView);
        builder.setTitle("재부팅");
        builder.setMessage("선택된 유형으로 재부팅을 실행합니다.");
        builder.setPositiveButton("확인", (dialog, which) -> {

            String jwt_t = pm.getString(PreferenceModel.jwt_token,null);

            if(jwt_t == null){
                return;
            }

            if(selected_device_id == null){
                return;
            }

            if(rg_reboot.getCheckedRadioButtonId() == R.id.rb_detail_reboot_software){
                sendCMDSoftwareReboot(pm.getString(PreferenceModel.jwt_token,null),selected_device_id);
            }else{
                sendCMDHardwareReboot(pm.getString(PreferenceModel.jwt_token,null),selected_device_id);
            }
        });
        builder.setNegativeButton("취소", (dialog, which) -> {

        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}