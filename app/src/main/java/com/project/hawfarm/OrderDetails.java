package com.project.hawfarm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.project.hawfarm.adapter.OrderItemAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderDetails extends AppCompatActivity {

    private static final String TAG = "OrderDetails";

    TextView orderNo, orderDate, sellerName, sellerAddress, sellerPhone, paymentMethod,grandTotal;
    List<JSONObject> productList;
    OrderItemAdapter itemAdapter;
    JSONObject itemData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        try {
            itemData = new JSONObject(getIntent().getStringExtra("item"));
            Log.d(TAG, "onCreate: " + itemData);
            setDetails();
            setRecyclerView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setRecyclerView() throws Exception {
        RecyclerView recyclerView = findViewById(R.id.order_details_recycler);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        productList = new ArrayList<>();

        JSONArray productArray = itemData.getJSONArray("orderQuantity");
        for (int i = 0; i < productArray.length(); i++) {
            productList.add(productArray.getJSONObject(i));
        }

        itemAdapter = new OrderItemAdapter(OrderDetails.this, productList);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();
    }

    private void setDetails() throws Exception {

        orderNo = findViewById(R.id.order_no);
        orderDate = findViewById(R.id.order_date_field);
        sellerName = findViewById(R.id.seller_name_field);
        sellerAddress = findViewById(R.id.seller_add_field);
        sellerPhone = findViewById(R.id.seller_phone_field);
        paymentMethod = findViewById(R.id.payment_method_field);
        grandTotal = findViewById(R.id.grand_total_field);

        orderNo.setText(itemData.getString("orderId"));
        orderDate.setText(itemData.getString("orderDate"));
        sellerName.setText(itemData.getString("sellerName"));
        sellerAddress.setText(itemData.getString("sellerAddress"));
        sellerPhone.setText(itemData.getString("sellerNumber"));
        paymentMethod.setText(itemData.getString("paymentMethod"));
        grandTotal.setText(itemData.getString("grandTotal"));
    }
}
