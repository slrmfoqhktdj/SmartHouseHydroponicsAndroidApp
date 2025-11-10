package com.ifcompany.smart_house_01;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.FirebaseApp;
import com.ifcompany.smart_house_01.databinding.ActivityMainBinding;
import com.ifcompany.smart_house_01.db.PreferenceManager;
import com.ifcompany.smart_house_01.db.PreferenceModel;
import com.ifcompany.smart_house_01.retrofit.Retrofit_Factory;
import com.ifcompany.smart_house_01.retrofit.Retrofit_Interface;
import com.ifcompany.smart_house_01.retrofit.model.receive.Retrofit_ReceiveAlertListModel;
import com.ifcompany.smart_house_01.retrofit.model.receive.Retrofit_ReceiveOSIDModel;
import com.ifcompany.smart_house_01.retrofit.model.send.Retrofit_SendOSIDModel;
import com.ifcompany.smart_house_01.alarm.MainAlertDialog;
import com.onesignal.Continue;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;

import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private String tag = "MainActivity";
    private ActivityMainBinding binding;

    private PreferenceManager pm ;
    private final Retrofit_Interface retrofitService = Retrofit_Factory.create();
    // 권한 요청을 구분하기 위한 상수 선언
    private static final int REQUEST_NOTIFICATION_PERMISSION = 1001;  // 고유한 숫자로 설정

    private List<Retrofit_ReceiveAlertListModel.Retrofit_ReceiveAlertListModel_item> alist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setFullScreen();
        super.onCreate(savedInstanceState);
        Log.e(tag,"OnCreate");
//        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);
        pm = PreferenceManager.getInstance(this);
        InitFragments();
//        FCMAdmin();
//        FCMInit();
//        FCMSubscribe("device01");
        OneSiganlInit();
//        OneSignalHandler();
        OneSignal.getNotifications().addForegroundLifecycleListener(iNotificationWillDisplayEvent -> {
            Log.e(tag,"addForegroundLifecycleListener");
        });
        ReceiveAlarmList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    //알람 아이콘 클릭시
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int curId = item.getItemId();
        if (curId == R.id.menu_main_notification) {
            Toast.makeText(this, "notifi", Toast.LENGTH_LONG).show();
            MainAlertDialog mainAlertDialog = new MainAlertDialog();
            mainAlertDialog.AlarmDialog(alist,this);

        }

        return super.onOptionsItemSelected(item);
    }

//    public void setFullScreen(){
//        int uiOption = getWindow().getDecorView().getSystemUiVisibility();
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
//            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
//            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//        getWindow().getDecorView().setSystemUiVisibility(uiOption);
//    }



    private void InitFragments(){
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_settings)
                .build();

        NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(MainActivity.this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

//    private void FCMAdmin(){
//        // Android 알림 권한 요청
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//
//            if(ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//
//                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.POST_NOTIFICATIONS }, REQUEST_NOTIFICATION_PERMISSION);
//            }
//        }
//
//    }

//    private void FCMSubscribe(String deviceid){
//        FirebaseMessaging.getInstance().subscribeToTopic(deviceid).addOnCompleteListener(new OnCompleteListener<Void>() {
//
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                if(task.isSuccessful()) {
//                    Log.d("FCM", "주제("+deviceid+")을 성공적으로 구독");
//                } else {
//                    Log.d("FCM", "주제("+deviceid+") 구독에 실패하였습니다.");
//                }
//            }
//        });
//    }

//    private void FCMInit(){
//        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
//            @Override
//            public void onComplete(@NonNull Task<String> task) {
//                if(!task.isSuccessful()){
//                    Log.d(tag, "FCM 토큰 가져오기 실패", task.getException());
//                    return;
//                }
//
//                String token = task.getResult();
//                pm.setString(PreferenceModel.fcm_token, token);
//                Log.d(tag, "FCM 토큰 : "+ token);
//                Log.d(tag, "FCM 토큰 길이 : "+ token.length());
//            }
//        });
//    }

    private void OneSiganlInit(){
        OneSignal.getDebug().setLogLevel(LogLevel.ERROR);
        OneSignal.initWithContext(this,"f9c08257-c09f-4c0a-9f07-9bc038f1c390");
        OneSignal.getNotifications().requestPermission(false, Continue.none());
//        Log.d(tag,"OneSignal external id : "+OneSignal.getUser().getExternalId());
//        Log.d(tag,"OneSignal onsignal id : "+OneSignal.getUser().getOnesignalId());
        Log.d(tag,"OneSignal pushsubscription id : "+OneSignal.getUser().getPushSubscription().getId());
//        Log.d(tag,"OneSignal pushsubscription token : "+OneSignal.getUser().getPushSubscription().getToken());

//        OneSignal.getNotifications().addClickListener(iNotificationClickEvent -> {
//
//        });
//
//        OneSignal.getNotifications().addForegroundLifecycleListener(iNotificationWillDisplayEvent -> {
//
//        });



        //oneSignal 앱 id 보고
        retrofitService.RPT_OSID(pm.getString(PreferenceModel.jwt_token,null),new Retrofit_SendOSIDModel(pm.getString(PreferenceModel.isEmail,null),OneSignal.getUser().getPushSubscription().getId())).enqueue(new Callback<Retrofit_ReceiveOSIDModel>() {
            @Override
            public void onResponse(Call<Retrofit_ReceiveOSIDModel> call, Response<Retrofit_ReceiveOSIDModel> response) {
                try{
                    Log.d(tag,"RPT_OSID : "+response.body().getOs_id()+","+response.body().getResult());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Retrofit_ReceiveOSIDModel> call, Throwable t) {
                Log.d(tag, String.valueOf(t));
            }
        });

    }

    private void OneSignalHandler(){

    }

    private void ReceiveAlarmList(){
        retrofitService.GetAlertList(pm.getString(PreferenceModel.jwt_token,null),pm.getString(PreferenceModel.isEmail,null)).enqueue(new Callback<Retrofit_ReceiveAlertListModel>() {
            @Override
            public void onResponse(Call<Retrofit_ReceiveAlertListModel> call, Response<Retrofit_ReceiveAlertListModel> response) {
                try{
                    Log.d(tag,"ReceiveAlarmList : "+response.body().getUser()+","+response.body().getItem());
//                    alist = response.body().getItem();
                    alist = response.body().getItem();


                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Retrofit_ReceiveAlertListModel> call, Throwable t) {
                Log.d(tag, String.valueOf(t));
            }
        });
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.e(tag,"onDestroy");
    }

    // 권한 요청 결과 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_NOTIFICATION_PERMISSION) {

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "알림 권한이 허용 되었습니다.", Toast.LENGTH_SHORT).show();
            }

            else {
                Toast.makeText(this, "알림 권한이 거부 되었습니다.\n알림을 받을 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}