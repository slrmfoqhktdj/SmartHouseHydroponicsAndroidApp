package com.ifcompany.smart_house_01.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ifcompany.smart_house_01.R;
import com.ifcompany.smart_house_01.ui.dashboard.model.DeviceAppointModel;
import com.ifcompany.smart_house_01.ui.dashboard.model.DeviceAppointNameModel;


import java.util.ArrayList;

public class AppointAdapter extends RecyclerView.Adapter<AppointAdapter.ViewHolder>{
    // {0 ~ 30/31} 까지의 날짜 리스트
    private ArrayList<DeviceAppointModel> list = new ArrayList<>();

    private final LayoutInflater inflater;

    private String tag = "AppointAdapter";

    private boolean disabled = true;

    ItemClickListener mClickListener;

    public AppointAdapter(Context context,  ItemClickListener mClickListener) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mClickListener = mClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_appoint, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DeviceAppointModel device = list.get(position);
        String Name = "";
        switch (device.getName()){
            case DeviceAppointNameModel.ComPumpName:
                Name = "컴펌프";
                break;
            case DeviceAppointNameModel.FFanName:
                Name = "유동팬";
                break;
            case DeviceAppointNameModel.EFanName:
                Name = "배기팬";
                break;
            case DeviceAppointNameModel.AirconName:
                Name = "냉난방기";
                break;
            case DeviceAppointNameModel.Co2CreatorName:
                Name = "co2발생기";
                break;
            case DeviceAppointNameModel.LedName:
                Name = "Led";
                break;
        }

        holder.name.setText(Name);
        holder.startT.setText(device.getStartt());
        holder.stopT.setText(device.getStopt());
        holder.onoff.setChecked(device.getOnoff());
        holder.onoff.setEnabled(disabled);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItemList(ArrayList<DeviceAppointModel> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void setItemDisable(boolean disabled){
        this.disabled = disabled;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView startT;
        TextView stopT;
        Switch onoff;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.appoint_item_state_name);
            startT = itemView.findViewById(R.id.appoint_item_starttime);
            stopT = itemView.findViewById(R.id.appoint_item_stoptime);
            onoff = itemView.findViewById(R.id.appoint_item_onoff);
//            iv_item = itemView.findViewById(R.id.iv_date);


            itemView.setOnClickListener(v -> mClickListener.onItemClick(v,getAdapterPosition(),list.get(getAdapterPosition())));
            onoff.setOnCheckedChangeListener((buttonView, isChecked) -> mClickListener.onOnoffClick(buttonView,getAdapterPosition(),list.get(getAdapterPosition()),isChecked));
        }

//        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, this.getAdapterPosition(), list.get(this.getAdapterPosition()));
//        }
    }

    // 클릭리스너 인터페이스
    public interface ItemClickListener {
        void onItemClick(View view, int position, DeviceAppointModel item);
        void onOnoffClick(View view, int position, DeviceAppointModel item, boolean isChecked);
    }
}
