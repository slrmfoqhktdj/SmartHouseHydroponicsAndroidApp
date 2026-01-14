package com.ifcompany.smart_house_hydroponics.retrofit;

import com.ifcompany.smart_house_hydroponics.retrofit.model.Retrofit_DeviceAppointListModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.Retrofit_DeviceDetailModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.Retrofit_DeviceSimpleListModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveAirconModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveAlertListModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveCo2CreatorModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveCompModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveEFanModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveFFanModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveLEDModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveLogOutModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveLoginModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveOSIDModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceivePumpModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveRebootHardwareModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveRebootSoftwareModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveRenewAccesstokenModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendAirconModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendAppointAirconModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendAppointCo2CreatorModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendAppointComPumpModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendAppointEFanModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendAppointFFanModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendAppointLEDModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendCo2CreatorModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendCompModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendEFanModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendFFanModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendLEDModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendLogOutModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendLoginModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendOSIDModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendPumpModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendRebootHardwareModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendRebootSoftwareModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendRenewAccessModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Retrofit_Interface {

    //로그인 및 토큰 발급
    @POST("app/login")
    Call<Retrofit_ReceiveLoginModel> Login(@Body Retrofit_SendLoginModel post);
    @POST("app/jwt/refresh")
    Call<Retrofit_ReceiveRenewAccesstokenModel> RenewedAccessToken(@Body Retrofit_SendRenewAccessModel refresh_token);

    //@Header("Authorization") String token
    @POST("/logout")
    Call<Retrofit_ReceiveLogOutModel> Logout(@Body Retrofit_SendLogOutModel post);

    //oneSignal id 제출
    @POST("app/rpt/osid")
    Call<Retrofit_ReceiveOSIDModel> RPT_OSID(@Body Retrofit_SendOSIDModel post);
    @GET("app/alarmlist")
    Call<Retrofit_ReceiveAlertListModel> GetAlertList(@Query("user") String user);
    //등록된 기기 리스트 및 간단 정보
    @GET("app/simple/list")
    Call<Retrofit_DeviceSimpleListModel> GetDeviceSimpleList(@Query("user") String user);
    //원하는 기기의 상세 정보
    @GET("app/detail")
    Call<Retrofit_DeviceDetailModel> GetDeviceInfo(@Query("id") String id);
    //comp onoff 명령어
    @POST("app/cmd/comp")
    Call<Retrofit_ReceiveCompModel> CMD_Comp(@Body Retrofit_SendCompModel post);
    //comp onoff 명령어
    @POST("app/cmd/pump")
    Call<Retrofit_ReceivePumpModel> CMD_Pump(@Body Retrofit_SendPumpModel post);
    //FFan onoff 명령어
    @POST("app/cmd/ffan")
    Call<Retrofit_ReceiveFFanModel> CMD_FFan(@Body Retrofit_SendFFanModel post);
    //EFan onoff 명령어
    @POST("app/cmd/efan")
    Call<Retrofit_ReceiveEFanModel> CMD_EFan(@Body Retrofit_SendEFanModel post);
    //Aircon onoff 명령어
    @POST("app/cmd/aircon")
    Call<Retrofit_ReceiveAirconModel> CMD_Aircon(@Body Retrofit_SendAirconModel post);
    //Co2Creator onoff 명령어
    @POST("app/cmd/co2creator")
    Call<Retrofit_ReceiveCo2CreatorModel> CMD_Co2Creator(@Body Retrofit_SendCo2CreatorModel post);
    //LED onoff 명령어
    @POST("app/cmd/led")
    Call<Retrofit_ReceiveLEDModel> CMD_LED(@Body Retrofit_SendLEDModel post);
    @POST("command/rbsw")
    Call<Retrofit_ReceiveRebootSoftwareModel> CMD_Reboot_Software(@Body Retrofit_SendRebootSoftwareModel post);
    @POST("command/rbhw")
    Call<Retrofit_ReceiveRebootHardwareModel> CMD_Reboot_Hardware(@Body Retrofit_SendRebootHardwareModel post);

    //등록된 기기 예약리스트 및 정보
    @GET("app/appoint/list")
    Call<Retrofit_DeviceAppointListModel> GetDeviceAppointList(@Query("deviceid") String deviceid);
    //예약 comp onoff 명령어
    @POST("app/cmd/appoint/compump")
    Call<Retrofit_ReceiveCompModel> ACMD_ComPump(@Body Retrofit_SendAppointComPumpModel post);
    //예약 FFan onoff 명령어
    @POST("app/cmd/appoint/ffan")
    Call<Retrofit_ReceiveFFanModel> ACMD_FFan(@Body Retrofit_SendAppointFFanModel post);
    //예약 EFan onoff 명령어
    @POST("app/cmd/appoint/efan")
    Call<Retrofit_ReceiveEFanModel> ACMD_EFan(@Body Retrofit_SendAppointEFanModel post);
    //예약 Aircon onoff 명령어
    @POST("app/cmd/appoint/aircon")
    Call<Retrofit_ReceiveAirconModel> ACMD_Aircon(@Body Retrofit_SendAppointAirconModel post);
    //예약 Co2Creator onoff 명령어
    @POST("app/cmd/appoint/co2creator")
    Call<Retrofit_ReceiveCo2CreatorModel> ACMD_Co2Creator(@Body Retrofit_SendAppointCo2CreatorModel post);
    //예약 LED onoff 명령어
    @POST("app/cmd/appoint/led")
    Call<Retrofit_ReceiveLEDModel> ACMD_LED(@Body Retrofit_SendAppointLEDModel post);

//    @GET("api/{device_id}")
//    @Headers({"Content-Type: application/json"})
//    Call<Retrofit_DeviceModel> GetDeviceInfo(
//            @Path("device_id") String device_id
//    );


}

