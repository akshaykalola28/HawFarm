package com.project.hawfarm;

import android.app.Activity;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


public class AddItemDialog extends DialogFragment {

    private static final String TAG = "AddItemDialog";
    View mainView;
    double weight, totalPrice;
    TextView itemNameField, priceField, descriptionField;
    ImageView itemImage;
    int priceInt;
    JSONObject itemData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.add_item_dialog, container, false);

        try {
            String itemDataString = getArguments().getString("itemData");
            itemData = new JSONObject(itemDataString);
            priceInt = itemData.getJSONArray("price").getJSONObject(0).getInt("price");
            setDetails();
        } catch (Exception e) {
            try {
                priceInt = itemData.getInt("basePrice");
                setDetails();
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }

        Button btn = mainView.findViewById(R.id.btnSubmit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAdded = false;
                try {
                    itemData.put("basePrice", priceInt);
                    itemData.put("weight", weight);
                    itemData.put("price", totalPrice);
                    //Add item from here
                    if (CartData.cartItemList.size() != 0) {
                        for (int i = 0; i < CartData.cartItemList.size(); i++) {
                            JSONObject cartItem = CartData.cartItemList.get(i);
                            Log.d("ID", cartItem.getString("stockId") + " | " + itemData.getString("stockId"));
                            if (cartItem.getString("stockId").trim().equals(itemData.getString("stockId").trim())) {
                                Log.d("S", "Done");
                                CartData.cartItemList.set(i, itemData);
                                Log.d("ITEMFORCART2", CartData.cartItemList.toString());
                                isAdded = true;
                                dismiss();
                                break;
                            }
                        }
                    }
                    if (!isAdded) {
                        CartData.cartItemList.add(itemData);
                        Log.d("ITEMFORCART", CartData.cartItemList.toString());
                        dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Spinner weightSpinner = mainView.findViewById(R.id.spinner1);
        weightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int price = priceInt;
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
        itemNameField = mainView.findViewById(R.id.product_name_dialog);
        priceField = mainView.findViewById(R.id.totalprice);
        descriptionField = mainView.findViewById(R.id.item_description_dialog);
        itemImage = mainView.findViewById(R.id.dialog_image);
        try {
            itemNameField.setText(itemData.getString("veg_name"));
            descriptionField.setText(itemData.getString("description"));
            Picasso.get().load(itemData.getString("stockImageURL")).into(itemImage);
            //textHawkerAddress.setText(hawkerData.getString("address"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }
}
