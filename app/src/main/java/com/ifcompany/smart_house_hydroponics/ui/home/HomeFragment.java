package com.ifcompany.smart_house_hydroponics.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ifcompany.smart_house_hydroponics.R;
import com.ifcompany.smart_house_hydroponics.databinding.FragmentHomeBinding;
//import com.ifcompany.smart_house_hydroponics.db.PreferenceManager;
//import com.ifcompany.smart_house_hydroponics.db.PreferenceModel;
import com.ifcompany.smart_house_hydroponics.db.DataStoreManager;
import com.ifcompany.smart_house_hydroponics.intent.KeyModel;
import com.ifcompany.smart_house_hydroponics.retrofit.Retrofit_Factory;
import com.ifcompany.smart_house_hydroponics.retrofit.Retrofit_Interface;
import com.ifcompany.smart_house_hydroponics.retrofit.model.Retrofit_DeviceSimpleListModel;
import com.ifcompany.smart_house_hydroponics.token.TokenManager;
import com.ifcompany.smart_house_hydroponics.ui.home.detail.DeviceDetailActivity;
import com.ifcompany.smart_house_hydroponics.ui.home.model.DeviceSimpleListModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private String tag = "HomeFragment";
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private DeviceAdapter deviceAdapter;
//    private PreferenceManager pm ;
    private DataStoreManager ds;
    private Retrofit_Interface retrofitService;
    private TokenManager tokenManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(tag,"OnCreateView");

//        pm = PreferenceManager.getInstance(getActivity());
        ds = DataStoreManager.getInstance(getActivity());
        retrofitService = Retrofit_Factory.getInstance(getActivity()).create();
        tokenManager = TokenManager.getInstance(getActivity());

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.homeRecyclerview;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);



        initRecyclerAdapter(new ArrayList<>());


        if(homeViewModel.getItemlist().getValue().size()<=0){
            getDeviceSimpleList(tokenManager.getUserEmail());
        }



        binding.fragmentHomeRefreshLayout.setOnRefreshListener(() -> {
            getDeviceSimpleList(tokenManager.getUserEmail());
            binding.fragmentHomeRefreshLayout.setRefreshing(false);
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        homeViewModel.getItemlist().observe(getViewLifecycleOwner(), DeviceSimpeModelItems -> {
            Log.d(tag,"change list");
            Log.d(tag,"homeViewModel.getItemlist().observe = "+DeviceSimpeModelItems.size());
            deviceAdapter.setItemList(DeviceSimpeModelItems);
        });
    }

    private void initRecyclerAdapter(ArrayList<DeviceSimpleListModel> list){
        deviceAdapter = new DeviceAdapter(requireContext(), list, new DeviceAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position, DeviceSimpleListModel item) {
                onItemClickFunction(view,position,item);
            }

            @Override
            public void onItemLongClick(View view, int position, DeviceSimpleListModel item) {
                onItemLongClickFunction(view,position,item);
            }

            @Override
            public void onCctvClick(View view, int position, DeviceSimpleListModel item) {
                onCctvClickFunction(view,position,item);
            }
        });

        recyclerView.setAdapter(deviceAdapter);
    }

    private void getDeviceSimpleList(String user){
        retrofitService.GetDeviceSimpleList(user).enqueue(new Callback<Retrofit_DeviceSimpleListModel>() {
            @Override
            public void onResponse(Call<Retrofit_DeviceSimpleListModel> call, Response<Retrofit_DeviceSimpleListModel> response) {
                try{
                    ArrayList<String> assignDeviceList = new ArrayList<>();

                    List<Retrofit_DeviceSimpleListModel.Retrofit_DeviceSimpeModel_item> list = response.body().getItem();
                    ArrayList<Retrofit_DeviceSimpleListModel.Retrofit_DeviceSimpeModel_item> arrayList = new ArrayList<>(list);

                    ArrayList<DeviceSimpleListModel> itemlist = new ArrayList<>();
                    for(int z = 0; z < arrayList.size(); z ++){
                        DeviceSimpleListModel m = new DeviceSimpleListModel();
                        Retrofit_DeviceSimpleListModel.Retrofit_DeviceSimpeModel_item n = arrayList.get(z);
                        Log.d(tag,n.getId()+" : "+n.getTemp()+" "+n.getHumi()+" "+n.getCctv()+" "+n.getAuto());
                        m.setId(n.getId());
                        m.setTemp(n.getTemp());
                        m.setHumi(n.getHumi());
                        m.setCctv(n.getCctv());
                        m.setAuto(n.getAuto());
                        assignDeviceList.add(n.getId());

//                        String Nicked = pm.getString(n.getId(),null);

//                        ds.getString(n.getId(), null, new DataStoreManager.GetStringCallback() {
//                            @Override
//                            public void onSuccess(String value) {
//                                if(value != null){
//                                    m.setNick(value);
//                                }else{
//                                    m.setNick(n.getId());
//                                }
//
//                            }
//
//                            @Override
//                            public void onError(Throwable t) {
//                                Log.d(tag,t.toString());
//                            }
//                        });

                        m.setNick(ds.getStringSync(n.getId(),n.getId()));
                        itemlist.add(m);

//                        if(Nicked != null){
//                            m.setNick(Nicked);
//                        }else{
//                            m.setNick(n.getId());
//                        }
//
//                        itemlist.add(m);
                    }

                    Log.d(tag,"itemlist size = "+itemlist.size());
                    homeViewModel.setItemlist(itemlist);

//                    pm.setDeviceList(PreferenceModel.device_list,assignDeviceList);
                    ds.setDeviceListSync(assignDeviceList);

                }catch (Exception e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(@NonNull Call<Retrofit_DeviceSimpleListModel> call, @NonNull Throwable t) {
                Log.d(tag, String.valueOf(t));
                Toast.makeText(getActivity(),"기기 리스트를 불러오는데 실패했습니다. 잠시 후 다시 시도해주세요.",Toast.LENGTH_LONG).show();
            }
        });
    }


    public void onItemClickFunction(View view, int position, DeviceSimpleListModel item) {
        Log.d(tag,"item click "+ position + " "+item.getId());
//        getDeviceInfo(pm.getString(PreferenceModel.jwt_token,null),item.getId());

//        if(item.getAuto()== DeviceStateModel.disconnect){
//            Toast.makeText(getActivity(),"기기와 연결이 불안정합니다.",Toast.LENGTH_LONG).show();
//            return;
//        }

        Intent deviceDetailIntent = new Intent(getActivity(), DeviceDetailActivity.class);
        deviceDetailIntent.putExtra(KeyModel.DeviceId,item.getId());
        deviceDetailIntent.putExtra(KeyModel.Nick,item.getNick());
        startActivity(deviceDetailIntent);
    }

    public void onItemLongClickFunction(View view, int position, DeviceSimpleListModel items) {
        Log.d(tag,"item click "+ position + " "+items.getId()+" "+ items.getNick());
//        getDeviceInfo(pm.getString(PreferenceModel.jwt_token,null),item.getId());

        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.inflate(R.menu.popup_device_item_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.detail_rename){
                    DeviceNameDialog(items.getNick(),position);
                    return true;
                }else{
                    return true;
                }
            }
        });
        popupMenu.show();
    }

    public void onCctvClickFunction(View view, int position, DeviceSimpleListModel item) {
        Log.d(tag,"cctv click "+ position + " "+item.getId()+" - "+item.getCctv());
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(item.getCctv());
        websiteIntent.setData(uri);
        startActivity(websiteIntent);
    }



    public void DeviceNameDialog(String origin_name,int position){


        View updateDialogView = getLayoutInflater().inflate(R.layout.dialog_name_update,null);
        EditText name_et = updateDialogView.findViewById(R.id.et_dialog_name);


        name_et.setHint(origin_name);


        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(updateDialogView);
        builder.setTitle("이름 수정");
        builder.setPositiveButton("확인", (dialog, which) -> {

            String newNick = name_et.getText().toString();

            homeViewModel.getItemlist().getValue().get(position).setNick(newNick);
            ArrayList<DeviceSimpleListModel> arrayList = homeViewModel.getItemlist().getValue();

//            pm.setString(arrayList.get(position).getId(),newNick);
            ds.setStringSync(arrayList.get(position).getId(),newNick);

            homeViewModel.setItemlist(arrayList);
        });
        builder.setNegativeButton("취소", (dialog, which) -> {

        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}