package com.project.hawfarm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.hawfarm.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder> {

    private Context context;
    private List<JSONObject> items;

    public OrderItemAdapter(Context context, List<JSONObject> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_cardview, viewGroup, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder orderItemViewHolder, int i) {
        JSONObject item = items.get(i);

        try {
            orderItemViewHolder.vegNameField.setText(item.getString("veg_name"));
            orderItemViewHolder.quantity.setText(item.getString("quantity"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class OrderItemViewHolder extends RecyclerView.ViewHolder {

        TextView vegNameField,quantity;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            vegNameField = itemView.findViewById(R.id.order_veg_name);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
