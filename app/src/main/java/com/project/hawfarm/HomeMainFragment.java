package com.project.hawfarm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.hawfarm.adapter.HawkerAdapter;
import com.project.hawfarm.adapter.SliderAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeMainFragment extends Fragment {

    View mainView;

    HawkerAdapter mAdapter;
    List<JSONObject> currentStockList;

    ProgressBar listLoading;
    SwipeRefreshLayout homeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_home_main, container, false);


        ViewPager mViewPager = (ViewPager) mainView.findViewById(R.id.viewPage);
        SliderAdapter adapterView = new SliderAdapter(getContext());
        mViewPager.setAdapter(adapterView);

        RecyclerView recyclerView = (RecyclerView) mainView.findViewById(R.id.myRecyclerView);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        // specify an adapter
        currentStockList = new ArrayList<>();
        mAdapter = new HawkerAdapter(getContext(), currentStockList, this);
        recyclerView.setAdapter(mAdapter);

        listLoading = mainView.findViewById(R.id.recyclerview_progressbar);
        listLoading.setVisibility(View.VISIBLE);

        homeRefreshLayout = mainView.findViewById(R.id.home_refresh_layout);
        homeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchCurrentAllStock();
            }
        });
        fetchCurrentAllStock();

        return mainView;
    }

    private void fetchCurrentAllStock() {
        listLoading.setVisibility(View.VISIBLE);
        currentStockList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerData.ALL_STOCK_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("responseSuccess").equals("true")) {
                                JSONArray dataArray = new JSONArray(jsonObject.getJSONArray("data").toString());
                                Log.d("dataArray", dataArray.length() + "");
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject stockInfo = dataArray.getJSONObject(i);
                                    currentStockList.add(stockInfo);
                                    Log.d("stockInfo", i + ": " + stockInfo.toString());
                                }
                                listLoading.setVisibility(View.GONE);
                                homeRefreshLayout.setRefreshing(false);
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(),
                                        "Something is wrong ! Please, Try Again...", Toast.LENGTH_SHORT).show();
                                listLoading.setVisibility(View.GONE);
                                homeRefreshLayout.setRefreshing(false);
                            }
                            mAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),
                        "Something is wrong ! Please, Try Again...", Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
                listLoading.setVisibility(View.GONE);
                homeRefreshLayout.setRefreshing(false);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }
}