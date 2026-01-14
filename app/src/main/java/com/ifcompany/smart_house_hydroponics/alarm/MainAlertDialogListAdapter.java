package com.ifcompany.smart_house_hydroponics.alarm;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ifcompany.smart_house_hydroponics.R;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveAlertListModel;

import java.util.List;

public class MainAlertDialogListAdapter extends RecyclerView.Adapter<MainAlertDialogListAdapter.ViewHolder> {

    private List<Retrofit_ReceiveAlertListModel.Retrofit_ReceiveAlertListModel_item> mlist;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Retrofit_ReceiveAlertListModel.Retrofit_ReceiveAlertListModel_item item);
    }

    public MainAlertDialogListAdapter(List<Retrofit_ReceiveAlertListModel.Retrofit_ReceiveAlertListModel_item> mlist, OnItemClickListener listener){
        this.mlist = mlist;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_alarm_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAlertDialogListAdapter.ViewHolder holder, int position) {
        Retrofit_ReceiveAlertListModel.Retrofit_ReceiveAlertListModel_item item = mlist.get(position);
//        if(!item.getRead()) holder.bgLl.setForeground(ContextCompat.getColor(context,R.color.teal_700));
        if(!item.getRead()) holder.bgLl.setBackgroundColor(Color.LTGRAY);
        holder.deviceidText.setText(item.getId());
        holder.titleText.setText(item.getTitle());
        holder.contentText.setText(item.getContent());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return mlist==null?0:mlist.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        TextView contentText;
        TextView deviceidText;

        LinearLayout bgLl;

        ViewHolder(View view) {
            super(view);
            bgLl = view.findViewById(R.id.dialog_alarm_list_bg);
            deviceidText = view.findViewById(R.id.dialog_alarm_list_deviceid);
            titleText = view.findViewById(R.id.dialog_alarm_list_title);
            contentText = view.findViewById(R.id.dialog_alarm_list_content);
        }
    }
}
