package com.ifcompany.smart_house_hydroponics.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ifcompany.smart_house_hydroponics.R;
import com.ifcompany.smart_house_hydroponics.ui.DeviceStateModel;
import com.ifcompany.smart_house_hydroponics.ui.home.model.DeviceSimpleListModel;


import java.util.ArrayList;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    // {0 ~ 30/31} 까지의 날짜 리스트
    private ArrayList<DeviceSimpleListModel> list;

    private Context context;
    private final LayoutInflater inflater;

    private String tag = "DeviceAdapter";

    ItemClickListener mClickListener;

    public DeviceAdapter(Context context,ArrayList<DeviceSimpleListModel> list, ItemClickListener mClickListener) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mClickListener = mClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_device, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DeviceSimpleListModel device = list.get(position);



        holder.device_name.setText(device.getNick());
        holder.temp.setText(String.valueOf(device.getTemp()));
        holder.humi.setText(String.valueOf(device.getHumi()));
        switch (device.getAuto()){
            case DeviceStateModel.pass:
                holder.state.setText("수동");
                holder.state.setTextColor(ContextCompat.getColor(context,R.color.device_state_pass));
                break;
            case DeviceStateModel.auto:
                holder.state.setText("자동");
                holder.state.setTextColor(ContextCompat.getColor(context,R.color.device_state_auto));
                break;
            default:
                holder.state.setText("연결끊김");
                holder.state.setTextColor(ContextCompat.getColor(context,R.color.device_state_disconnect));
                break;
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItemList(ArrayList<DeviceSimpleListModel> list){
        this.list = list;
        Log.d(tag,"list size = "+list.size());
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView device_name;
//        ImageView iv_item;
        TextView temp;
        TextView humi;
        TextView cctv;
        TextView state;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            device_name = itemView.findViewById(R.id.simple_item_device_name);
            temp = itemView.findViewById(R.id.simple_item_device_temp);
            humi = itemView.findViewById(R.id.simple_item_device_humi);
            cctv = itemView.findViewById(R.id.simple_item_device_cctv);
            state = itemView.findViewById(R.id.simple_item_device_state);


            itemView.setOnClickListener(v -> mClickListener.onItemClick(v,getAdapterPosition(),list.get(getAdapterPosition())));
            itemView.setOnLongClickListener(v ->  {mClickListener.onItemLongClick(v,getAdapterPosition(),list.get(getAdapterPosition())); return false;});
            cctv.setOnClickListener(v -> mClickListener.onCctvClick(v,getAdapterPosition(),list.get(getAdapterPosition())));
        }

//        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, this.getAdapterPosition(), list.get(this.getAdapterPosition()));
//        }
    }

    // 클릭리스너 인터페이스
    public interface ItemClickListener {
        void onItemClick(View view, int position, DeviceSimpleListModel item);
        void onItemLongClick(View view, int position, DeviceSimpleListModel item);
        void onCctvClick(View view, int position, DeviceSimpleListModel item);
    }






}
