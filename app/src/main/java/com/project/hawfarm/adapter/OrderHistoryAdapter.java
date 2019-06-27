package com.project.hawfarm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.hawfarm.R;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {

    Context context;

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.order_histry_cardview, viewGroup, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder orderHistoryViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView orderNo, productName, quntity, sellerName, deliveryAddress, status;

        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            orderNo = itemView.findViewById(R.id.order_no);
            productName = itemView.findViewById(R.id.product_name);
            quntity = itemView.findViewById(R.id.Quntity);
            sellerName = itemView.findViewById(R.id.seller_name);
            deliveryAddress = itemView.findViewById(R.id.delivery_address);
            status = itemView.findViewById(R.id.status);

        }
    }
}
