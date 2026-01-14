package com.ifcompany.smart_house_hydroponics.ui.intro;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.ifcompany.smart_house_hydroponics.MainActivity;
import com.ifcompany.smart_house_hydroponics.R;
import com.ifcompany.smart_house_hydroponics.databinding.ActivitySplashBinding;
import com.ifcompany.smart_house_hydroponics.token.TokenManager;
import com.ifcompany.smart_house_hydroponics.ui.login.LoginActivity;


public class SplashActivity extends AppCompatActivity {


    //영상길이
    long video_time = 2000;

    String tag = "SplashActivity";
    private ActivitySplashBinding binding;

//    private Retrofit_Interface retrofitService;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(tag, "onCreate");

//        retrofitService = Retrofit_Factory.create(getApplicationContext());
        tokenManager = TokenManager.getInstance(getApplicationContext());
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
        Intent intent;

        if(!tokenManager.getAutoLogin()){
            tokenManager.Logout();
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        }else{
//            Login(email, pw);
            intent = new Intent(SplashActivity.this, MainActivity.class);
        }

        startActivity(intent);
        finish();

    }


//    private void Login(String email, String pw){
//        Retrofit_SendLoginModel rl = new Retrofit_SendLoginModel(email,pw);
//        Log.d(tag,"login handle");
//        retrofitService.Login(rl).enqueue(new Callback<Retrofit_ReceiveLoginModel>() {
//            @Override
//            public void onResponse(Call<Retrofit_ReceiveLoginModel> call, Response<Retrofit_ReceiveLoginModel> response) {
//
//                Retrofit_ReceiveLoginModel.Retrofit_ReceiveLoginModel_item m = response.body().getItem();
//
//                if(m.getSuccess()){
//                    tokenManager.saveTokens(m.getToken_pair().getAccess_token(),m.getToken_pair().getRefresh_token(),m.getToken_pair().getExpires_in(),m.getEmail());
//
//                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }else{
//                    Toast.makeText(getApplicationContext(),"로그인 요청에 실패했습니다. 잠시 후 다시 시도해주세요.",Toast.LENGTH_LONG).show();
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<Retrofit_ReceiveLoginModel> call, Throwable t) {
//                Log.d(tag, String.valueOf(t));
//
//                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//    }





}
