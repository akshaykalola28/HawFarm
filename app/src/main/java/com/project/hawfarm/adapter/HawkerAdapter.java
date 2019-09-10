package com.project.hawfarm.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.hawfarm.ItemListFragment;
import com.project.hawfarm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class HawkerAdapter extends RecyclerView.Adapter<HawkerAdapter.HawkerViewHolder> {

    private Context context;
    private List<JSONObject> currentStockList;
    private Fragment fragment;

    public HawkerAdapter(Context context, List<JSONObject> currentStockList, Fragment fragment) {
        this.context = context;
        this.currentStockList = currentStockList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public HawkerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.seller_cardview, viewGroup, false);

        return new HawkerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HawkerViewHolder hawkerViewHolder, int i) {
        JSONObject currentStock = currentStockList.get(i);
        hawkerViewHolder.setData(currentStock, i);
        String allVegName = "";
        try {
            JSONArray productArray = currentStock.getJSONArray("product");

            for (int n = 0; n < productArray.length(); n++) {
                JSONObject productJson = productArray.getJSONObject(n);
                if (n == 0) {
                    allVegName = productJson.getString("veg_name");
                } else {
                    allVegName = productJson.getString("veg_name") + ", " + allVegName;
                }
            }
            hawkerViewHolder.textVegName.setText(allVegName);
            hawkerViewHolder.textSellerName.setText(currentStock.getString("email")); //TODO: Change to name
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return currentStockList.size();
    }

    public class HawkerViewHolder extends RecyclerView.ViewHolder {

        JSONObject currentItem = null;
        int pos = 0;

        TextView textSellerName, textVegName, textDescription;
        ImageView sellerImageView;

        public HawkerViewHolder(@NonNull View itemView) {
            super(itemView);

            textSellerName = itemView.findViewById(R.id.text_seller_name);
            textVegName = itemView.findViewById(R.id.text_veg_name);
            textDescription = itemView.findViewById(R.id.text_desc);
            sellerImageView = itemView.findViewById(R.id.seller_imageview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, currentItem.toString(), Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putString("hawkerData", currentItem.toString());
                    ItemListFragment itemListFragment = new ItemListFragment();
                    itemListFragment.setArguments(bundle);

                    fragment.getFragmentManager().beginTransaction().replace(R.id.home_fragment,
                            itemListFragment).addToBackStack(null).commit();
                }
            });
        }

        void setData(JSONObject currentItem, int pos) {
            this.currentItem = currentItem;
            this.pos = pos;
        }
    }
}
