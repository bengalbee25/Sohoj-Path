package com.sohoj_path;

import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.GravityCompat;


public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        DrawerLayout drawerLayout = ((MainActivity) getActivity()).drawerLayout;
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.END);
        }

        return view;
    }

}