package com.project.hawfarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.project.hawfarm.adapter.ItemAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemListFragment extends Fragment {

    private static final String TAG = "ItemListFragment";
    View mainView;
    TextView textHawkerName, textHawkerAddress;
    FloatingActionButton addItemToCartFAB;

    JSONObject hawkerData;
    JSONArray productArray;
    List<JSONObject> productList;

    ItemAdapter itemAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_item_list, container, false);

        try {
            String hawkerDataString = getArguments().getString("hawkerData");

            hawkerData = new JSONObject(hawkerDataString);
            productArray = hawkerData.getJSONArray("product");

            //Store Only hawker data without
            JSONObject hawkerDataLocal = hawkerData;
            hawkerDataLocal.remove("product");
            CartData.hawkerDataInCart = hawkerDataLocal;
            Log.d(TAG, "onCreateView: " + CartData.hawkerDataInCart);

            Log.i("PRINT", productArray.toString());
            setDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addItemToCartFAB = mainView.findViewById(R.id.add_item_to_cart);
        addItemToCartFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CartData.cartItemList.isEmpty()) {
                    Intent intent = new Intent(getContext(), CartViewActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Cart is Empty.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setRecyclerView();

        return mainView;
    }

    private void setRecyclerView() {
        RecyclerView recyclerView = mainView.findViewById(R.id.item_recycleview);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        productList = new ArrayList<>();
        itemAdapter = new ItemAdapter(getContext(), productList, this);
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

        CartData.cartItemList.clear();
        try {
            textHawkerName.setText(hawkerData.getString("name")); //TODO: Change to name
            textHawkerAddress.setText(hawkerData.getJSONArray("address").getJSONObject(0).getString("address") + ", " +
                    hawkerData.getJSONArray("address").getJSONObject(0).getString("pincode"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
