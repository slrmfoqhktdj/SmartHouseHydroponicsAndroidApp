package com.ifcompany.smart_house_hydroponics.retrofit;

import android.content.Context;

import com.ifcompany.smart_house_hydroponics.token.TokenManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_Factory {
//    private static final String BASE_URL = "http://192.168.0.7:8099/";
//    private static final String BASE_URL = "http://192.168.0.7:9004/";

//    private static final String BASE_URL = "http://192.168.0.7:8309/";
    private static final String BASE_URL = "https://shouse.hydrop.api.gracetech.co.kr/";
    private static Retrofit_Factory instance;
    private Retrofit retrofit;
    private static Context mcontext = null;

    public static synchronized Retrofit_Factory getInstance(Context context) {
        if (instance == null) {
            instance = new Retrofit_Factory(context);
        }
        return instance;
    }

    private Retrofit_Factory(Context context){

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(new Retrofit_InterceptorRefreshToken(BASE_URL,mcontext))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public <T> Retrofit_Interface create(){
        return retrofit.create(Retrofit_Interface.class);
    }

//    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
//            .connectTimeout(10, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
//            .writeTimeout(15, TimeUnit.SECONDS)
//            .addInterceptor(new Retrofit_InterceptorRefreshToken(BASE_URL,mcontext))
//            .build();


//    public static Retrofit_Interface create(Context context) {
//
//        mcontext = context;
//
//        return new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//
//                .create(Retrofit_Interface.class);
//    }
}
