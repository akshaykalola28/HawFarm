package com.project.hawfarm;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.project.hawfarm.adapter.ItemAdapter;

@SuppressLint("SetTextI18n")
public class CartViewActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    ItemAdapter itemAdapter;
    TextView hawkerNameField, hawkerAddressField, totalItemField, itemPriceField, itemPriceField2,
            chargePriceField, grandTotalField, paymentField;
    int itemPrice, chargePrice = 10, grandTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);

        try {
            setDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setRecyclerView();
    }

    private void setDetails() throws Exception {
        hawkerNameField = findViewById(R.id.cart_text_Name);
        hawkerAddressField = findViewById(R.id.cart_text_address);
        totalItemField = findViewById(R.id.cart_text_itemtotal);
        itemPriceField = findViewById(R.id.cart_item_price);
        itemPriceField2 = findViewById(R.id.cart_item_price2);
        chargePriceField = findViewById(R.id.cart_charges_price);
        grandTotalField = findViewById(R.id.cart_grand_total);
        paymentField = findViewById(R.id.cart_item_payment);

        hawkerNameField.setText(CartData.hawkerDataInCart.getString("name"));
        hawkerAddressField.setText(CartData.hawkerDataInCart.getJSONArray("address").getJSONObject(0).getString("address") + ", " +
                CartData.hawkerDataInCart.getJSONArray("address").getJSONObject(0).getString("pincode"));
        totalItemField.setText("Total Item: " + CartData.cartItemList.size());
        setItemPrice();

    }

    private void setItemPrice() throws Exception {
        int price = 0;
        for (int i = 0; i < CartData.cartItemList.size(); i++) {
            price += CartData.cartItemList.get(i).getInt("price");
        }
        itemPrice = price;
        itemPriceField.setText("₹ " + itemPrice);
        itemPriceField2.setText("₹ " + itemPrice);

        //Set Charge Price
        chargePriceField.setText("₹ " + chargePrice);

        //Set Grand Total
        grandTotal = itemPrice + chargePrice;
        grandTotalField.setText("₹ " + grandTotal);
    }

    private void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_cart);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        itemAdapter = new ItemAdapter(CartViewActivity.this, CartData.cartItemList);
        recyclerView.setAdapter(itemAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        itemAdapter.notifyDataSetChanged();
        try {
            setItemPrice();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void payment_option(View view) {
        Intent intent = new Intent(CartViewActivity.this, PaymentOptionActivity.class);
        startActivity(intent);
    }

}
