package com.project.hawfarm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AddItemDialog extends DialogFragment {

    View mainView;
    double weight, totalPrice;
    TextView textHawkerItemName, priceField;
    String priceString;
    JSONObject itemData;

    List<JSONObject> cartItemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.add_item_dialog, container, false);

        try {
            String itemDataString = getArguments().getString("itemData");
            itemData = new JSONObject(itemDataString);
            priceString = itemData.getString("price");
            setDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }

        cartItemList = new ArrayList<>();

        Button btn = mainView.findViewById(R.id.btnSubmit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemListFragment itemListFragment = (ItemListFragment) getTargetFragment();
                itemListFragment.addItemList(itemData);
                dismiss();
            }
        });

        Spinner weightSpinner = mainView.findViewById(R.id.spinner1);
        weightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int price = Integer.parseInt(priceString);
                if (position == 0) {
                    weight = 250;
                    totalPrice = price * 1;
                } else if (position == 1) {
                    weight = 500;
                    totalPrice = price * 2;
                } else if (position == 2) {
                    weight = 1000;
                    totalPrice = price * 4;
                }
                setTotalPrice(totalPrice);
                Log.d("DEMO", weight + "");
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return mainView;
    }

    private void setTotalPrice(double totalPrice) {
        priceField.setText(totalPrice + "");
    }

    private void setDetails() {
        //textHawkerName = mainView.findViewById(R.id.product_name_dialog);
        textHawkerItemName = mainView.findViewById(R.id.seller_name_dialog);
        priceField = mainView.findViewById(R.id.totalprice);

        try {
            textHawkerItemName.setText(itemData.getString("veg_name"));
            //textHawkerAddress.setText(hawkerData.getString("address"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
