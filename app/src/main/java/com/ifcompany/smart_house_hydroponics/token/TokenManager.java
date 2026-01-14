package com.ifcompany.smart_house_hydroponics.token;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.ifcompany.smart_house_hydroponics.db.DataStoreManager;
//import com.ifcompany.smart_house_hydroponics.db.PreferenceManager;
import com.ifcompany.smart_house_hydroponics.retrofit.Retrofit_Factory;
import com.ifcompany.smart_house_hydroponics.retrofit.Retrofit_Interface;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveRenewAccesstokenModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendRenewAccessModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 토큰 관리 싱글톤 클래스
 * - Access Token, Refresh Token 암호화 저장
 * - 토큰 만료 확인 및 자동 갱신
 * - 로그인 상태 관리
 */
public class TokenManager {
    private static final String TAG = "TokenManager";

    // SharedPreferences 키 상수
    private static final String PREFS_NAME = "token_prefs";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_TOKEN_EXPIRY = "token_expiry";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_AUTO_LOGIN = "auto_login";

    // 싱글톤 인스턴스
    private static TokenManager instance;
    private Context context;
//    private PreferenceManager pm ;
    private DataStoreManager ds;
    private Retrofit_Interface retrofitService;

    /**
     * Private 생성자 - 싱글톤 패턴
     * EncryptedSharedPreferences로 토큰을 안전하게 저장
     */
    private TokenManager(Context context) {
        this.context = context;
//        pm = PreferenceManager.getInstance(context);
        ds = DataStoreManager.getInstance(context);
        retrofitService = Retrofit_Factory.getInstance(context).create();
    }

    /**
     * 싱글톤 인스턴스 반환
     */
    public static synchronized TokenManager getInstance(Context context) {
        if (instance == null) {
            instance = new TokenManager(context);
        }
        return instance;
    }

//    /**
//     * 로그인 성공 시 토큰 저장
//     *
//     * @param accessToken  API 요청용 토큰 (짧은 수명)
//     * @param refreshToken 토큰 갱신용 (긴 수명)
//     * @param expiresIn    만료 시간 (초 단위)
//     * @param userEmail    사용자 이메일
//     */

    public interface TokenSaveCallback {
        void onTokenSaved();
        void onTokenSaveFailed();
    }


    public void saveTokens(String accessToken, String refreshToken, long expiresIn, String userEmail, TokenSaveCallback callback) {
        // 만료 시간 계산 (현재 시간 + expiresIn초)
        try{
            long expiryTime = System.currentTimeMillis() + (expiresIn * 1000);


//            pm.setString(KEY_ACCESS_TOKEN, accessToken);
//            pm.setString(KEY_REFRESH_TOKEN,refreshToken);
//            pm.setLong(KEY_TOKEN_EXPIRY, expiryTime);
//            pm.setString(KEY_USER_EMAIL, userEmail);

            ds.setEncryptStringSync(KEY_ACCESS_TOKEN,accessToken);
            ds.setEncryptStringSync(KEY_REFRESH_TOKEN,refreshToken);
            ds.setLongSync(KEY_TOKEN_EXPIRY, expiryTime);
            ds.setStringSync(KEY_USER_EMAIL, userEmail);

            Log.d(TAG, "Tokens saved - Expires at: " + expiryTime);

            long currentTime = System.currentTimeMillis();

            Log.d(TAG, "Is currentTime = "+ currentTime);

            if(callback == null){
                return;
            }

            if(getAccessToken() != null && getRefreshToken() != null){
                callback.onTokenSaved();
            }else{
                callback.onTokenSaveFailed();
            }

        }catch (Exception e){
            e.printStackTrace();
            if(callback !=null){
                callback.onTokenSaveFailed();
            }
        }

    }

    // 기존 메서드 유지 (하위 호환성)
    public void saveTokens(String accessToken, String refreshToken, long expiresIn, String userEmail) {
        saveTokens(accessToken, refreshToken, expiresIn, userEmail, null);
    }

//    public void setAutoLogin(boolean autoLogin){pm.setBool(KEY_AUTO_LOGIN,autoLogin);}
    public void setAutoLogin(boolean autoLogin){ds.setBoolSync(KEY_AUTO_LOGIN,autoLogin);}

    /**
     * Access Token 조회
     * @return Access Token 또는 null
     */
//    public void setAccessToken(String accessToken){pm.setString(KEY_ACCESS_TOKEN,accessToken);}
//    public String getAccessToken() {
//        return pm.getString(KEY_ACCESS_TOKEN,null);
//    }
    public void setAccessToken(String accessToken){ds.setEncryptStringSync(KEY_ACCESS_TOKEN,accessToken);}
    public String getAccessToken() {return ds.getDecryptStringSync(KEY_ACCESS_TOKEN,null);}

    /**
     * Refresh Token 조회
     * @return Refresh Token 또는 null
     */
    public String getRefreshToken() {
        return ds.getDecryptStringSync(KEY_REFRESH_TOKEN, null);
    }

    /**
     * 사용자 이메일 조회
     * @return 사용자 이메일 또는 null
     */
    public String getUserEmail() {
        return ds.getStringSync(KEY_USER_EMAIL, null);
    }

    //자동 로그인
    public Boolean getAutoLogin(){return ds.getBoolSync(KEY_AUTO_LOGIN,false);}
    /**
     * 토큰 만료 확인
     * 만료 1분 전을 만료로 간주 (여유 시간 확보)
     *
     * @return true: 만료됨, false: 유효함
     */
    public boolean isTokenExpired() {
//        long expiryTime = pm.getLong(KEY_TOKEN_EXPIRY, 0);
        long expiryTime = ds.getLongSync(KEY_TOKEN_EXPIRY, 0);
        long currentTime = System.currentTimeMillis();

        // 만료 1분(60000ms) 전을 만료로 간주
        boolean expired = currentTime > (expiryTime - 60000);

        if (expired) {
            Log.d(TAG, "Token expired or expiring soon");
        }

        return expired;
    }

    /**
     * 로그인 상태 확인
     * Refresh Token이 있으면 로그인 상태로 간주
     *
     * @return true: 로그인됨, false: 로그아웃됨
     */
    public boolean isLoggedIn() {
        return getRefreshToken() != null;
    }

    /**
     * 로그아웃 - 모든 토큰 삭제
     */

    public void Logout(){
//        pm.setString(KEY_ACCESS_TOKEN, null);
//        pm.setString(KEY_REFRESH_TOKEN,null);
//        pm.setLong(KEY_TOKEN_EXPIRY, 0);
//        pm.setString(KEY_USER_EMAIL, null);
//        pm.setBool(KEY_AUTO_LOGIN,false);

        ds.setEncryptStringSync(KEY_ACCESS_TOKEN, null);
        ds.setEncryptStringSync(KEY_REFRESH_TOKEN, null);
        ds.setLongSync(KEY_TOKEN_EXPIRY, 0);
        ds.setStringSync(KEY_USER_EMAIL,  null);
        ds.setBoolSync(KEY_AUTO_LOGIN,false);
    }

    /**
     * 토큰 갱신 (동기 호출)
     * Refresh Token으로 새 Access Token 발급
     *
     * @return true: 갱신 성공, false: 갱신 실패
     */
//    public boolean renewedTokens() {
//        String refreshToken = getRefreshToken();
////        boolean autoLogin = getAutoLogin();
//
//        final boolean[] result = {false};
//
//        if (refreshToken == null) {
//            Log.w(TAG, "No refresh token available");
//            return false;
//        }
//
//        try {
//            Log.d(TAG, "Attempting to refresh tokens...");
//
//
//            retrofitService.RenewedAccessToken(new Retrofit_SendRenewAccessModel(refreshToken)).enqueue(new Callback<Retrofit_ReceiveRenewAccesstokenModel>() {
//                @Override
//                public void onResponse(Call<Retrofit_ReceiveRenewAccesstokenModel> call, Response<Retrofit_ReceiveRenewAccesstokenModel> response) {
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
//
//        } catch (Exception e) {
//            Log.e(TAG, "Unexpected error during token refresh", e);
//        }
//
//        return result[0];
//    }


//    @Override
//    public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
//        Request originalrequest = chain.request();
//        okhttp3.Response response = chain.proceed(chain.request());
//
//        if(response.code() == 401){
//            Log.d(tag," jwt token 만료 ");
//
//
//            Retrofit_SendRenewAccessModel m = new Retrofit_SendRenewAccessModel(getRefreshToken());
//            String json = new Gson().toJson(m);
//
//            Request refreshRequest = new Request.Builder().url(url+"login").post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json.toString())).build();
//
//            okhttp3.Response responsed = new OkHttpClient().newCall(refreshRequest).execute();
//
//            String refreshtoken ;
//            if(responsed.code() == 200){
//                try {
//                    Log.d(tag," jwt token 1 ");
//                    JSONObject rt = new JSONObject(responsed.body().string());
//                    refreshtoken = rt.getJSONObject("item").getString("name");
//                    Log.d(tag," jwt token 1 "+refreshtoken);
//                    PreferenceManager.PreferenceManager().setString(PreferenceModel.jwt_token,refreshtoken);
//                    Request retokenQuest = originalrequest.newBuilder().header("Authorization","Bearer "+refreshtoken).build();
//                    response.close();
//                    response = chain.proceed(retokenQuest);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//
//                }
//            }
//
//        }
//
//        return response;
//    }

    /**
     * 토큰 정보 로깅 (디버그용)
     */
    public void logTokenInfo() {
        Log.d(TAG, "=== Token Info ===");
        Log.d(TAG, "Logged in: " + isLoggedIn());
        Log.d(TAG, "Token expired: " + isTokenExpired());
        Log.d(TAG, "User email: " + getUserEmail());
        Log.d(TAG, "Has access token: " + (getAccessToken() != null));
        Log.d(TAG, "Has refresh token: " + (getRefreshToken() != null));
    }
}