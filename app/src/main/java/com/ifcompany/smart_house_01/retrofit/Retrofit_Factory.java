package com.ifcompany.smart_house_01.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_Factory {
//    private static final String BASE_URL = "http://192.168.0.7:8099/";
//    private static final String BASE_URL = "http://192.168.0.7:9004/";

    private static final String BASE_URL = "http://192.168.0.7:8309/";

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(new Retrofit_InterceptorRefreshToken(BASE_URL))
            .build();


    public static Retrofit_Interface create() {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

                .create(Retrofit_Interface.class);
    }
}
