package com.ifcompany.smart_house_hydroponics.ui.settings;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ifcompany.smart_house_hydroponics.R;
import com.ifcompany.smart_house_hydroponics.databinding.FragmentSettingsBinding;
//import com.ifcompany.smart_house_hydroponics.db.PreferenceManager;
//import com.ifcompany.smart_house_hydroponics.db.PreferenceModel;
import com.ifcompany.smart_house_hydroponics.db.DataStoreManager;
import com.ifcompany.smart_house_hydroponics.retrofit.Retrofit_Factory;
import com.ifcompany.smart_house_hydroponics.retrofit.Retrofit_Interface;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveLogOutModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveRebootHardwareModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveRebootSoftwareModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendLogOutModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendRebootHardwareModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendRebootSoftwareModel;
import com.ifcompany.smart_house_hydroponics.token.TokenManager;
import com.ifcompany.smart_house_hydroponics.ui.login.LoginActivity;
import com.onesignal.OneSignal;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private String tag = "SettingsFragment";
    private Retrofit_Interface retrofitService;
//    private PreferenceManager pm ;
    private DataStoreManager ds;
    private TokenManager tokenManager;
    private String selected_device_id = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

//        pm = PreferenceManager.getInstance(getActivity());
        ds = DataStoreManager.getInstance(getActivity());
        retrofitService = Retrofit_Factory.getInstance(getActivity()).create();
        tokenManager = TokenManager.getInstance(getActivity());

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

                    //서버 토큰 정보 삭제
                    retrofitService.Logout(new Retrofit_SendLogOutModel(tokenManager.getUserEmail(), tokenManager.getRefreshToken())).enqueue(new Callback<Retrofit_ReceiveLogOutModel>() {
                        @Override
                        public void onResponse(Call<Retrofit_ReceiveLogOutModel> call, Response<Retrofit_ReceiveLogOutModel> response) {
                            Log.d(tag,response.body().getSuccess().toString());
                        }

                        @Override
                        public void onFailure(Call<Retrofit_ReceiveLogOutModel> call, Throwable t) {
                            Log.d(tag,t.getMessage().toString());
                        }
                    });
                    
                    //서버와의 통신과는 상관없이 로그아웃 진행
                    OneSignal.logout();

                    tokenManager.Logout();


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

    private void sendCMDSoftwareReboot(String deviceid){
        retrofitService.CMD_Reboot_Software(new Retrofit_SendRebootSoftwareModel(deviceid)).enqueue(new Callback<Retrofit_ReceiveRebootSoftwareModel>() {
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

    private void sendCMDHardwareReboot(String deviceid){
        retrofitService.CMD_Reboot_Hardware(new Retrofit_SendRebootHardwareModel(deviceid)).enqueue(new Callback<Retrofit_ReceiveRebootHardwareModel>() {
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

//        ArrayList<String> deviceidlist = new ArrayList<>(pm.getDeviceList(PreferenceModel.device_list));
        ds.getDeviceList(new DataStoreManager.GetDeviceListCallback() {
            @Override
            public void onSuccess(ArrayList<String> devices) {
                selected_device_id = devices.get(0);
                ArrayList<String> devicenicklist = (ArrayList<String>)devices.clone();
                devicenicklist.replaceAll(key -> ds.getStringSync(key, key));

                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,devicenicklist);
                spinnerAdapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);

                sn_device_list.setAdapter(spinnerAdapter);
                sn_device_list.setSelection(0,false);

                sn_device_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.d(tag,"onSpinner selected position = "+position + ", id = "+devices.get(position));

                        selected_device_id = devices.get(position);

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


                    if(selected_device_id == null){
                        return;
                    }

                    if(rg_reboot.getCheckedRadioButtonId() == R.id.rb_detail_reboot_software){
                        sendCMDSoftwareReboot(selected_device_id);
                    }else{
                        sendCMDHardwareReboot(selected_device_id);
                    }
                });
                builder.setNegativeButton("취소", (dialog, which) -> {

                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

            @Override
            public void onError(Throwable t) {
                Log.d(tag,t.toString());
            }
        });
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}