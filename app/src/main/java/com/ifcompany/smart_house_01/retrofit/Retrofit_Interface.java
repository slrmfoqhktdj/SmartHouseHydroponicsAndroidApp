package com.ifcompany.smart_house_01.retrofit;

import com.ifcompany.smart_house_01.retrofit.model.Retrofit_DeviceAppointListModel;
import com.ifcompany.smart_house_01.retrofit.model.Retrofit_DeviceDetailModel;
import com.ifcompany.smart_house_01.retrofit.model.Retrofit_DeviceSimpleListModel;
import com.ifcompany.smart_house_01.retrofit.model.receive.Retrofit_ReceiveAirconModel;
import com.ifcompany.smart_house_01.retrofit.model.receive.Retrofit_ReceiveCo2CreatorModel;
import com.ifcompany.smart_house_01.retrofit.model.receive.Retrofit_ReceiveCompModel;
import com.ifcompany.smart_house_01.retrofit.model.receive.Retrofit_ReceiveEFanModel;
import com.ifcompany.smart_house_01.retrofit.model.receive.Retrofit_ReceiveFFanModel;
import com.ifcompany.smart_house_01.retrofit.model.receive.Retrofit_ReceiveLEDModel;
import com.ifcompany.smart_house_01.retrofit.model.receive.Retrofit_ReceiveLoginModel;
import com.ifcompany.smart_house_01.retrofit.model.receive.Retrofit_ReceiveOSIDModel;
import com.ifcompany.smart_house_01.retrofit.model.receive.Retrofit_ReceivePumpModel;
import com.ifcompany.smart_house_01.retrofit.model.receive.Retrofit_ReceiveRebootHardwareModel;
import com.ifcompany.smart_house_01.retrofit.model.receive.Retrofit_ReceiveRebootSoftwareModel;
import com.ifcompany.smart_house_01.retrofit.model.send.Retrofit_SendAirconModel;
import com.ifcompany.smart_house_01.retrofit.model.send.Retrofit_SendAppointAirconModel;
import com.ifcompany.smart_house_01.retrofit.model.send.Retrofit_SendAppointCo2CreatorModel;
import com.ifcompany.smart_house_01.retrofit.model.send.Retrofit_SendAppointComPumpModel;
import com.ifcompany.smart_house_01.retrofit.model.send.Retrofit_SendAppointEFanModel;
import com.ifcompany.smart_house_01.retrofit.model.send.Retrofit_SendAppointFFanModel;
import com.ifcompany.smart_house_01.retrofit.model.send.Retrofit_SendAppointLEDModel;
import com.ifcompany.smart_house_01.retrofit.model.send.Retrofit_SendCo2CreatorModel;
import com.ifcompany.smart_house_01.retrofit.model.send.Retrofit_SendCompModel;
import com.ifcompany.smart_house_01.retrofit.model.send.Retrofit_SendEFanModel;
import com.ifcompany.smart_house_01.retrofit.model.send.Retrofit_SendFFanModel;
import com.ifcompany.smart_house_01.retrofit.model.send.Retrofit_SendLEDModel;
import com.ifcompany.smart_house_01.retrofit.model.send.Retrofit_SendLoginModel;
import com.ifcompany.smart_house_01.retrofit.model.send.Retrofit_SendOSIDModel;
import com.ifcompany.smart_house_01.retrofit.model.send.Retrofit_SendPumpModel;
import com.ifcompany.smart_house_01.retrofit.model.send.Retrofit_SendRebootHardwareModel;
import com.ifcompany.smart_house_01.retrofit.model.send.Retrofit_SendRebootSoftwareModel;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Retrofit_Interface {


//    @FormUrlEncoded
    @GET("{path}")
//    Call<ResponseBody> downApk(@Path("path") String path,@FieldMap HashMap<String,String> param);
    Call<ResponseBody> downApk(@Path("path") String path);

    //앱 업데이트 확인
    @FormUrlEncoded
    @POST("file/apk/consequnce")
    Call<ResponseBody> updateComplete(
            @FieldMap HashMap<String,String> param
    );

    //결제내역 보내기
    @FormUrlEncoded
    @POST("pay/history")
    Call<ResponseBody> sendPayHistory(
            @FieldMap HashMap<String,String> param
    );

    //충전 시작시간 보내기
    @FormUrlEncoded
    @POST("charging/start")
    Call<ResponseBody> sendCStart(
            @FieldMap HashMap<String,String> param
    );

    //충전 종료시간 보내기
    @FormUrlEncoded
    @POST("charging/end")
    Call<ResponseBody> sendCEnd(
            @FieldMap HashMap<String,String> param
    );



    //로그인 및 토큰 발급
    @POST("app/login")
    Call<Retrofit_ReceiveLoginModel> Login(@Body Retrofit_SendLoginModel post);
    //oneSignal id 제출
    @POST("app/rpt/osid")
    Call<Retrofit_ReceiveOSIDModel> RPT_OSID(@Header("Authorization") String token, @Body Retrofit_SendOSIDModel post);
    //등록된 기기 리스트 및 간단 정보
    @GET("app/simple/list")
    Call<Retrofit_DeviceSimpleListModel> GetDeviceSimpleList(@Header("Authorization") String token, @Query("user") String user);
    //원하는 기기의 상세 정보
    @GET("app/detail")
    Call<Retrofit_DeviceDetailModel> GetDeviceInfo(@Header("Authorization") String token, @Query("id") String id);
    //comp onoff 명령어
    @POST("app/cmd/comp")
    Call<Retrofit_ReceiveCompModel> CMD_Comp(@Header("Authorization") String token, @Body Retrofit_SendCompModel post);
    //comp onoff 명령어
    @POST("app/cmd/pump")
    Call<Retrofit_ReceivePumpModel> CMD_Pump(@Header("Authorization") String token, @Body Retrofit_SendPumpModel post);
    //FFan onoff 명령어
    @POST("app/cmd/ffan")
    Call<Retrofit_ReceiveFFanModel> CMD_FFan(@Header("Authorization") String token, @Body Retrofit_SendFFanModel post);
    //EFan onoff 명령어
    @POST("app/cmd/efan")
    Call<Retrofit_ReceiveEFanModel> CMD_EFan(@Header("Authorization") String token, @Body Retrofit_SendEFanModel post);
    //Aircon onoff 명령어
    @POST("app/cmd/aircon")
    Call<Retrofit_ReceiveAirconModel> CMD_Aircon(@Header("Authorization") String token, @Body Retrofit_SendAirconModel post);
    //Co2Creator onoff 명령어
    @POST("app/cmd/co2creator")
    Call<Retrofit_ReceiveCo2CreatorModel> CMD_Co2Creator(@Header("Authorization") String token, @Body Retrofit_SendCo2CreatorModel post);
    //LED onoff 명령어
    @POST("app/cmd/led")
    Call<Retrofit_ReceiveLEDModel> CMD_LED(@Header("Authorization") String token, @Body Retrofit_SendLEDModel post);
    @POST("command/rbsw")
    Call<Retrofit_ReceiveRebootSoftwareModel> CMD_Reboot_Software(@Header("Authorization") String token, @Body Retrofit_SendRebootSoftwareModel post);
    @POST("command/rbhw")
    Call<Retrofit_ReceiveRebootHardwareModel> CMD_Reboot_Hardware(@Header("Authorization") String token, @Body Retrofit_SendRebootHardwareModel post);

    //등록된 기기 예약리스트 및 정보
    @GET("app/appoint/list")
    Call<Retrofit_DeviceAppointListModel> GetDeviceAppointList(@Header("Authorization") String token, @Query("deviceid") String deviceid);
    //예약 comp onoff 명령어
    @POST("app/cmd/appoint/compump")
    Call<Retrofit_ReceiveCompModel> ACMD_ComPump(@Header("Authorization") String token, @Body Retrofit_SendAppointComPumpModel post);
    //예약 FFan onoff 명령어
    @POST("app/cmd/appoint/ffan")
    Call<Retrofit_ReceiveFFanModel> ACMD_FFan(@Header("Authorization") String token, @Body Retrofit_SendAppointFFanModel post);
    //예약 EFan onoff 명령어
    @POST("app/cmd/appoint/efan")
    Call<Retrofit_ReceiveEFanModel> ACMD_EFan(@Header("Authorization") String token, @Body Retrofit_SendAppointEFanModel post);
    //예약 Aircon onoff 명령어
    @POST("app/cmd/appoint/aircon")
    Call<Retrofit_ReceiveAirconModel> ACMD_Aircon(@Header("Authorization") String token, @Body Retrofit_SendAppointAirconModel post);
    //예약 Co2Creator onoff 명령어
    @POST("app/cmd/appoint/co2creator")
    Call<Retrofit_ReceiveCo2CreatorModel> ACMD_Co2Creator(@Header("Authorization") String token, @Body Retrofit_SendAppointCo2CreatorModel post);
    //예약 LED onoff 명령어
    @POST("app/cmd/appoint/led")
    Call<Retrofit_ReceiveLEDModel> ACMD_LED(@Header("Authorization") String token, @Body Retrofit_SendAppointLEDModel post);

//    @GET("api/{device_id}")
//    @Headers({"Content-Type: application/json"})
//    Call<Retrofit_DeviceModel> GetDeviceInfo(
//            @Path("device_id") String device_id
//    );


}

