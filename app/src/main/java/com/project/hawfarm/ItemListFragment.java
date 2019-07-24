package com.project.hawfarm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.hawfarm.adapter.ItemAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemListFragment extends Fragment {

    View mainView;
    TextView textHawkerName, textHawkerAddress;

    JSONObject hawkerData;
    JSONArray productArray;
    List<JSONObject> productList;

    ItemAdapter itemAdapter;
    List<JSONObject> cartItemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_item_list, container, false);

        try {
            String hawkerDataString = getArguments().getString("hawkerData");

            hawkerData = new JSONObject(hawkerDataString);
            productArray = hawkerData.getJSONArray("product");

            Log.i("PRINT", productArray.toString());
            setDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }

        cartItemList = new ArrayList<>();
        setRecyclerView();

        mainView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    Log.d("KEY","PRESSED");
                }
                return false;
            }
        });

        return mainView;
    }

    private void setRecyclerView() {
        RecyclerView recyclerView = mainView.findViewById(R.id.item_recycleview);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        productList = new ArrayList<>();
        itemAdapter = new ItemAdapter(getActivity().getApplicationContext(), productList, this);
        recyclerView.setAdapter(itemAdapter);

        for (int i = 0; i < productArray.length(); i++) {
            try {
                productList.add(productArray.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            itemAdapter.notifyDataSetChanged();
        }
    }

    private void setDetails() {
        textHawkerName = mainView.findViewById(R.id.text_hawker_name);
        textHawkerAddress = mainView.findViewById(R.id.text_address);

        try {
            textHawkerName.setText(hawkerData.getString("name"));
            //textHawkerAddress.setText(hawkerData.getString("address"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addItemList(JSONObject itemData) {
        cartItemList.add(itemData);
        Log.d("CARTITEMLIST", cartItemList.toString());
    }

    public boolean onBackPressed() {
        return false;
    }
}
