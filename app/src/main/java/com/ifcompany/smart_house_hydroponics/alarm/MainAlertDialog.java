package com.ifcompany.smart_house_hydroponics.alarm;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ifcompany.smart_house_hydroponics.R;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveAlertListModel;

import java.util.List;

public class MainAlertDialog {
    public void AlarmDialog(List<Retrofit_ReceiveAlertListModel.Retrofit_ReceiveAlertListModel_item> array, Context context){

        if(array == null){
            Toast.makeText(context,"알림이 없습니다.",Toast.LENGTH_LONG).show();
            return;
        }

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();

        display.getRealSize(size);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        Dialog dialog = new Dialog(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_alarm,null);
        dialog.setContentView(dialogView);

        lp.copyFrom(dialog.getWindow().getAttributes());
        int width = size.x;
        int height = size.y;
        lp.width = width*80/100;
//        lp.height= height*50/100;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.setCanceledOnTouchOutside(true); // 외부 touch 시 Dialog 종료
        dialog.getWindow().setAttributes(lp);





        RecyclerView recyclerView = dialog.findViewById(R.id.dialog_alarm_RecyclerView);

//        recyclerView.setLayoutParams(new LinearLayoutCompat.LayoutParams(
//                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
//                LinearLayoutCompat.LayoutParams.MATCH_PARENT
//        ));


        DividerItemDecoration did = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(did);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        MainAlertDialogListAdapter adapter = new MainAlertDialogListAdapter(array, new MainAlertDialogListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Retrofit_ReceiveAlertListModel.Retrofit_ReceiveAlertListModel_item item) {
                Toast.makeText(context,item.getId(),Toast.LENGTH_LONG).show();
            }
        });

        recyclerView.setAdapter(adapter);
        dialog.show();


    }
}
