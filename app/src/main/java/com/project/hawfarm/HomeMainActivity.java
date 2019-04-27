package com.project.hawfarm;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeMainActivity extends Fragment {

    View mainView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_home_main, container, false);

        ViewPager mViewPager = (ViewPager) mainView.findViewById(R.id.viewPage);
        ImageAdapter adapterView = new ImageAdapter(getActivity().getApplicationContext());
        mViewPager.setAdapter(adapterView);

        return mainView;

    }
}

