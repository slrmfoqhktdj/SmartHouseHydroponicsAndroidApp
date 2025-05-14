package com.ifcompany.smart_house_01.ui.intro;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.ifcompany.smart_house_01.MainActivity;
import com.ifcompany.smart_house_01.R;
import com.ifcompany.smart_house_01.databinding.ActivitySplashBinding;
import com.ifcompany.smart_house_01.db.PreferenceManager;
import com.ifcompany.smart_house_01.db.PreferenceModel;
import com.ifcompany.smart_house_01.retrofit.Retrofit_Factory;
import com.ifcompany.smart_house_01.retrofit.Retrofit_Interface;
import com.ifcompany.smart_house_01.retrofit.model.receive.Retrofit_ReceiveLoginModel;
import com.ifcompany.smart_house_01.retrofit.model.send.Retrofit_SendLoginModel;
import com.ifcompany.smart_house_01.ui.login.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashActivity extends AppCompatActivity {


    //영상길이
    long video_time = 2000;

    String tag = "SplashActivity";
    private PreferenceManager pm ;
    private ActivitySplashBinding binding;

    private final Retrofit_Interface retrofitService = Retrofit_Factory.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(tag, "onCreate");

        pm = PreferenceManager.getInstance(this);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        new Handler().postDelayed(() -> {
            Log.d(tag, "start handler");

            nextActivity();

        },video_time);


    }

    @Override
    public void onDestroy(){
        super.onDestroy();


    }

    // 연결



    private void nextActivity(){
        String email = pm.getString(PreferenceModel.isEmail,null);
        String pw = pm.getString(PreferenceModel.isPw,null);
        Log.d(tag, "saved email = "+ email);
        Log.d(tag, "saved pw = " + pw);

        if(email == null || pw == null || email.equals("") || pw.equals("")){
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            Login(email, pw);
        }

    }


    private void Login(String email, String pw){
        Retrofit_SendLoginModel rl = new Retrofit_SendLoginModel(email,pw);
        Log.d(tag,"login handle");
        retrofitService.Login(rl).enqueue(new Callback<Retrofit_ReceiveLoginModel>() {
            @Override
            public void onResponse(Call<Retrofit_ReceiveLoginModel> call, Response<Retrofit_ReceiveLoginModel> response) {

                pm.setString(PreferenceModel.jwt_token,response.body().getItem().getName());

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onFailure(Call<Retrofit_ReceiveLoginModel> call, Throwable t) {
                Log.d(tag, String.valueOf(t));

                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }





}
