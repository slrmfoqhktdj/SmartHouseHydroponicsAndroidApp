package com.ifcompany.smart_house_01.retrofit;

import android.util.Log;

import androidx.annotation.RequiresExtension;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ifcompany.smart_house_01.db.PreferenceManager;
import com.ifcompany.smart_house_01.db.PreferenceModel;
import com.ifcompany.smart_house_01.retrofit.model.receive.Retrofit_ReceiveLoginModel;
import com.ifcompany.smart_house_01.retrofit.model.send.Retrofit_SendLoginModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class Retrofit_InterceptorRefreshToken implements Interceptor {

    String tag = "Retrofit_InterceptorRefreshToken";
    String url;

    public Retrofit_InterceptorRefreshToken(String url){
        this.url = url;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalrequest = chain.request();
        Response response = chain.proceed(chain.request());

        if(response.code() == 401){
            Log.d(tag," jwt token 만료 ");

            String email = PreferenceManager.PreferenceManager().getString(PreferenceModel.isEmail,null);
            String pw = PreferenceManager.PreferenceManager().getString(PreferenceModel.isPw,null);

            Retrofit_SendLoginModel m = new Retrofit_SendLoginModel(email,pw);
            String json = new Gson().toJson(m);

            Request refreshRequest = new Request.Builder().url(url+"login").post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json.toString())).build();

            Response responsed = new OkHttpClient().newCall(refreshRequest).execute();

            String refreshtoken ;
            if(responsed.code() == 200){
                try {
                    Log.d(tag," jwt token 1 ");
                    JSONObject rt = new JSONObject(responsed.body().string());
                    refreshtoken = rt.getJSONObject("item").getString("name");
                    Log.d(tag," jwt token 1 "+refreshtoken);
                    PreferenceManager.PreferenceManager().setString(PreferenceModel.jwt_token,refreshtoken);
                    Request retokenQuest = originalrequest.newBuilder().header("Authorization","Bearer "+refreshtoken).build();
                    response.close();
                    response = chain.proceed(retokenQuest);

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }



            //            new OkHttpClient().newCall(refreshRequest).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//
//                }
//                @Override
//                public void onResponse(Call call, Response responsed) throws IOException {
//                    Log.d(tag,"refresh state = "+ responsed.code());
//                    String refreshtoken ;
//                    if(responsed.code() == 200){
//                        try {
//                            JSONObject rt = new JSONObject(responsed.body().string());
//                            Log.d(tag,"refresh token = "+ rt.getJSONObject("item").getString("name"));
//                            refreshtoken = rt.getJSONObject("item").getString("name");
//
//                            Request retokenQuest = originalrequest.newBuilder().header("Authorization","Bearer "+refreshtoken).build();
//                            return chain.proceed(retokenQuest);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//
//                        }
//                    }
//                }
//            });

        }

        return response;
    }




//    @POST("login")
//    Call<Retrofit_ReceiveLoginModel> Login(@Body Retrofit_SendLoginModel post);
}
