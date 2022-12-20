package com.app.jurnalkelas.ui.logout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.app.jurnalkelas.LoginActivity;
import com.app.jurnalkelas.R;
import com.app.jurnalkelas.ui.home.HomeFragment;
import com.app.jurnalkelas.util.SharedPrefManager;

import java.util.Objects;

public class LogoutFragment extends Fragment {

    Context mContext;
    SharedPrefManager sharedPrefManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_logout, container, false);

        sharedPrefManager = new SharedPrefManager(mContext);

        new AlertDialog.Builder(mContext)
                .setTitle("Keluar applikasi")
                .setMessage("Apakah anda yakin ingin keluar ?")
                .setPositiveButton("KELUAR", (dialog, which) -> {

                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);

                }).setNegativeButton("BATAL", (dialog, which) -> {

            HomeFragment mf = new HomeFragment();
            FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, mf, HomeFragment.class.getSimpleName())
                    .addToBackStack(null);
            ft.commit();

        }).show();


        return root;
    }
}