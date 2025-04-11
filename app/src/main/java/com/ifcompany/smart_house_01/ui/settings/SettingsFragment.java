package com.ifcompany.smart_house_01.ui.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ifcompany.smart_house_01.databinding.FragmentSettingsBinding;
import com.ifcompany.smart_house_01.db.PreferenceManager;
import com.ifcompany.smart_house_01.db.PreferenceModel;
import com.ifcompany.smart_house_01.ui.login.LoginActivity;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private String tag = "SettingsFragment";
    private PreferenceManager pm ;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        pm = PreferenceManager.getInstance(getActivity());

        Log.e(tag,"OnCreateView");
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        ConstraintLayout logout = binding.settingsLogout;

        logout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("로그아웃");
            builder.setMessage("로그아웃을 하시겠습니까?");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    pm.setString(PreferenceModel.isEmail,null);
                    pm.setString(PreferenceModel.isPw,null);
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);

                    getActivity().finish();

                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}