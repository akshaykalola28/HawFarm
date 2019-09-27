package com.project.hawfarm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.hawfarm.adapter.OrderHistoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryFragment extends Fragment {

    private static final String TAG = "AllOrderFragment";

    View mainView;
    CardView cardView;

    RecyclerView OrderHistoryRecycler;

    List<JSONObject> orderHistoryList;
    OrderHistoryAdapter mAdapter;

    JSONObject userDataJson;
    String userEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_order_history, container, false);

        userDataJson = ((HomeActivity) getActivity()).getUser();

        try {
            userEmail = userDataJson.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        initUI();
        fetchOrderList();
        return mainView;
    }
    private void initUI() {
        OrderHistoryRecycler = mainView.findViewById(R.id.order_history_recycler);
        OrderHistoryRecycler.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        OrderHistoryRecycler.setLayoutManager(manager);

        orderHistoryList = new ArrayList<>();
        mAdapter = new OrderHistoryAdapter(getActivity(), this, orderHistoryList);
        OrderHistoryRecycler.setAdapter(mAdapter);
    }

    private void fetchOrderList() {
        Log.d(TAG, "fetchOrderHistoryList: ");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ServerData.LIST_ORDER_URL + userEmail, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: ");
                        try {
                            if (response.getBoolean("responseSuccess")) {
                                JSONArray responseArray = response.getJSONArray("data");
                                for (int i = 0; i < responseArray.length(); i++) {
                                    JSONObject order = responseArray.getJSONObject(i);
                                    orderHistoryList.add(order);
                                }
                            } else {
                                Toast.makeText(getActivity(),
                                        "Order Not Available.", Toast.LENGTH_SHORT).show();
                            }
                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: ");
                        Toast.makeText(getActivity(),
                                "Something is wrong ! Please, Try Again...", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }


}
