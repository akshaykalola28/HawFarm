package com.project.hawfarm;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeMainFragment extends Fragment {

    View mainView;

    HawkerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_home_main, container, false);

        ViewPager mViewPager = (ViewPager) mainView.findViewById(R.id.viewPage);
        Slider adapterView = new Slider(getActivity().getApplicationContext());
        mViewPager.setAdapter(adapterView);

        RecyclerView recyclerView = (RecyclerView) mainView.findViewById(R.id.myRecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new HawkerAdapter();
        recyclerView.setAdapter(mAdapter);

        return mainView;

    }
}

