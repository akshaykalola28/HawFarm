package com.project.hawfarm;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class HawkerAdapter extends RecyclerView.Adapter<HawkerAdapter.HawkerViewHolder > {


    @NonNull
    @Override
    public HawkerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull HawkerViewHolder hawkerViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class HawkerViewHolder extends RecyclerView.ViewHolder{

        public HawkerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
