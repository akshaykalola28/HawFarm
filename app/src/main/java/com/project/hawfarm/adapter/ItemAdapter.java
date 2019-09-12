package com.project.hawfarm.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.project.hawfarm.AddItemDialog;
import com.project.hawfarm.R;

import org.json.JSONObject;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Context context;
    private List<JSONObject> itemsList;
    private Fragment fragment;

    public ItemAdapter(Context context, List<JSONObject> itemsList, Fragment fragment) {
        this.context = context;
        this.itemsList = itemsList;
        this.fragment = fragment;
    }

    public ItemAdapter(Context context, List<JSONObject> itemsList) {
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
        setAnimation(itemViewHolder);
        final JSONObject item = itemsList.get(i);

        try {
            itemViewHolder.itemName.setText(item.getString("veg_name"));
            itemViewHolder.itemDescription.setText(item.getString("description"));

            int price;
            if (fragment == null) {
                //Call when cart view created.
                itemViewHolder.itemAddButton.setText(item.getString("weight"));
                price = item.getInt("price");
            } else {
                //Call when ItemList Created.
                price = item.getJSONArray("price").getJSONObject(0).getInt("price");
            }
            itemViewHolder.priceField.setText("₹ " + price);
        } catch (Exception e) {
            e.printStackTrace();
        }

        itemViewHolder.itemAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("itemData", item.toString());

                if (fragment != null) {
                    FragmentTransaction fragmentTransaction = fragment.getFragmentManager().beginTransaction().addToBackStack(null);
                    DialogFragment dialog = new AddItemDialog();
                    dialog.setArguments(bundle);
                    dialog.show(fragmentTransaction, "addItemDialog");
                } else {
                    AppCompatActivity activity = (AppCompatActivity) context;
                    FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction().addToBackStack(null);
                    DialogFragment dialog = new AddItemDialog();
                    dialog.setArguments(bundle);
                    dialog.show(fragmentTransaction, "addItemDialog");
                }
            }
        });
    }

    private void setAnimation(ItemViewHolder itemViewHolder) {
        Animation slideRelativeLayout1 = AnimationUtils.loadAnimation(context, R.anim.item_list_ani_card_view);
        itemViewHolder.itemCardView.setAnimation(slideRelativeLayout1);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, priceField, itemDescription;
        Button itemAddButton;

        CardView itemCardView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemCardView = itemView.findViewById(R.id.item_card_view);
            itemName = itemView.findViewById(R.id.item_name);
            priceField = itemView.findViewById(R.id.item_price);
            itemDescription = itemView.findViewById(R.id.item_description);
            itemAddButton = itemView.findViewById(R.id.item_add_button);
        }
    }
}
