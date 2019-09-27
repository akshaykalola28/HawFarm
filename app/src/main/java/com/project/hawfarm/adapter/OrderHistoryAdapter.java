package com.project.hawfarm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.hawfarm.OrderDetails;
import com.project.hawfarm.R;

import org.json.JSONObject;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {

    private static final String TAG = "OrderHistoryAdapter";

    private Context context;
    private Fragment fragment;
    private List<JSONObject> items;

    public OrderHistoryAdapter(Context context, Fragment fragment, List<JSONObject> items) {
        this.context = context;
        this.fragment = fragment;
        this.items = items;
    }

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_order_history, viewGroup, false);
        return new OrderHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder orderHistoryViewHolder, int i) {

        final JSONObject item = items.get(i);
        Log.d(TAG, "onBindViewHolder: " + item.toString());

        try {
            orderHistoryViewHolder.orderIdField.setText(item.getString("orderId"));
            orderHistoryViewHolder.orderDateField.setText(item.getString("orderDate"));
            orderHistoryViewHolder.sellerNameField.setText(item.getString("sellerName"));
            orderHistoryViewHolder.statusField.setText(item.getString("status"));

            orderHistoryViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, OrderDetails.class);
                    intent.putExtra("item", String.valueOf(item));
                    context.startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView orderIdField, orderDateField, sellerNameField, statusField;

        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            orderIdField = itemView.findViewById(R.id.order_id_field);
            orderDateField = itemView.findViewById(R.id.order_date_field);
            sellerNameField = itemView.findViewById(R.id.seller_name_field);
            statusField = itemView.findViewById(R.id.status_field);

        }
    }
}
