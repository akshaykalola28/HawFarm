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

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Context context;
    private List<JSONObject> itemsList;

    ItemAdapter(Context context, List<JSONObject> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cardview, viewGroup, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        JSONObject item = itemsList.get(i);

        try {
            itemViewHolder.itemName.setText(item.getString("veg_name"));
            itemViewHolder.itemType.setText(item.getString("stock_id")); //TODO: Change it later
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, itemType, price, description;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.item_price);
            description = itemView.findViewById(R.id.item_description);
        }

    }
}
