package com.ifcompany.smart_house_hydroponics.retrofit;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveRenewAccesstokenModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendRenewAccessModel;
import com.ifcompany.smart_house_hydroponics.token.TokenManager;
import com.ifcompany.smart_house_hydroponics.ui.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class Retrofit_InterceptorRefreshToken implements Interceptor {

    String tag = "Retrofit_InterceptorRefreshToken";
    String url;

    private Context mcontext;
    private TokenManager tokenManager;

    public Retrofit_InterceptorRefreshToken(String url, Context context){
        this.url = url;
        this.mcontext = context;
    }


//    @NonNull
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Request originalrequest = chain.request();
//
//        //토큰이 필요없는 루트는 토큰을 넣지 않음
//        String path = originalrequest.url().encodedPath();
//        if(shouldSkipAuth(path)){
//            Log.d(tag,"No access token available");
//            return chain.proceed(originalrequest);
//        }
//
//        String accessToken = null;
//
//        if(tokenManager == null){
//            tokenManager = TokenManager.getInstance(mcontext);
//        }
//
//        // 토큰이 만료되었으면 갱신 시도
//        if (tokenManager.isTokenExpired()) {
//            Log.d(tag, "Access token expired, attempting refresh...");
//
//            //오토 로그인 체크가 안되어있던 로그인일 경우. 제한시간이 지나면 로그인 페이지로
//            if(!tokenManager.getAutoLogin()){
//                Intent intent = new Intent(mcontext, LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                mcontext.startActivity(intent);
//                return chain.proceed(originalrequest);
//            }
//
//
//            retrofitService.RenewedAccessToken(new Retrofit_SendRenewAccessModel(refreshToken)).enqueue(new Callback<Retrofit_ReceiveRenewAccesstokenModel>() {
//                @Override
//                public void onResponse(Call<Retrofit_ReceiveRenewAccesstokenModel> call, retrofit2.Response<Retrofit_ReceiveRenewAccesstokenModel> response) {
//                    Retrofit_ReceiveRenewAccesstokenModel item = response.body();
//                    saveTokens(
//                            item.getToken_pair().getAccess_token(),
//                            item.getToken_pair().getRefresh_token(),
//                            item.getToken_pair().getExpires_in(),
//                            getUserEmail()
//                    );
//
//                    Log.d(TAG, "Tokens refreshed successfully");
//                    result[0] = true;
//                }
//
//                @Override
//                public void onFailure(Call<Retrofit_ReceiveRenewAccesstokenModel> call, Throwable t) {
//                    Log.e(TAG, "retrofitService refreshToken onFailure", t.getCause());
//                }
//            });
//
//            if (tokenManager.renewedTokens()) {
//                accessToken = tokenManager.getAccessToken();
//                Log.d(tag, "Token refreshed successfully");
//            } else {
//                Log.e(tag, "Token refresh failed in interceptor");
//                // 갱신 실패 시 그대로 진행 (Authenticator에서 처리)
//                return chain.proceed(originalrequest);
//            }
//        }
//
//        //헤더에 토큰 자동추가
//        Request authenticatedRequest = originalrequest.newBuilder()
//                .header("Authorization", "Bearer " + accessToken)
//                .build();
//
//        return chain.proceed(authenticatedRequest);
//    }


    @Override
    public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
        Request originalrequest = chain.request();
//        okhttp3.Response response = chain.proceed(chain.request());

        //토큰이 필요없는 루트는 토큰을 넣지 않음
        String path = originalrequest.url().encodedPath();

        if(shouldSkipAuth(path)){
            Log.d(tag,"No access token available");
            return chain.proceed(originalrequest);
        }
        
        if(tokenManager == null){
            tokenManager = TokenManager.getInstance(mcontext);
        }

        // 토큰이 만료 안되어 있으면  넘김
        if (!tokenManager.isTokenExpired()) {
            String accessToken = tokenManager.getAccessToken();
            Request authenticatedRequest = originalrequest.newBuilder().header("Authorization","Bearer "+accessToken).build();
            return chain.proceed(authenticatedRequest);
        }

        // 토큰이 만료되었으면 갱신 시도
        Log.d(tag, "Access token expired, attempting refresh...");

        //오토 로그인 체크가 안되어있던 로그인일 경우. 제한시간이 지나면 로그인 페이지로
        if(!tokenManager.getAutoLogin()){
            Intent intent = new Intent(mcontext, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mcontext.startActivity(intent);
            return chain.proceed(originalrequest);
        }

        Retrofit_SendRenewAccessModel m = new Retrofit_SendRenewAccessModel(tokenManager.getRefreshToken());
        String json = new Gson().toJson(m);

        Request renewedRequest = new Request.Builder().url(url+"app/jwt/refresh").post(RequestBody.create(json.toString(),MediaType.parse("application/json; charset=utf-8"))).build();

        okhttp3.Response renewedresponsed = new OkHttpClient().newCall(renewedRequest).execute();

        if(renewedresponsed.code() == 200){
            try {
                Log.d(tag," jwt token 1 ");
                JSONObject rt = new JSONObject(renewedresponsed.body().string());

                String email = rt.getString("email");
                boolean success = rt.getBoolean("success");
                String accesstoken = rt.getJSONObject("token_pair").getString("access_token");
                String refreshtoken = rt.getJSONObject("token_pair").getString("refresh_token");
                int expiresin = rt.getJSONObject("token_pair").getInt("expires_in");

                if(!success){
                    renewedresponsed.close();
                    return chain.proceed(originalrequest);
                }

                tokenManager.saveTokens(accesstoken, refreshtoken, expiresin, email);

                Log.d(tag," jwt token 1 "+accesstoken);

                Request retokenQuest = originalrequest.newBuilder().header("Authorization","Bearer "+accesstoken).build();
                renewedresponsed.close();
                return chain.proceed(retokenQuest);

            } catch (JSONException e) {
                e.printStackTrace();
                renewedresponsed.close();
                return chain.proceed(originalrequest);
            }
        }else{
            renewedresponsed.close();
            return chain.proceed(originalrequest);
        }
    }

    //토큰 필요없이 접근가능한 루트
    private boolean shouldSkipAuth(String path){
        return path.contains("/login") || path.contains("/refresh");
    }



//    @POST("login")
//    Call<Retrofit_ReceiveLoginModel> Login(@Body Retrofit_SendLoginModel post);
}
