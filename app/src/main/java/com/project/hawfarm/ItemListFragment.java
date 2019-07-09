package com.project.hawfarm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

public class ItemListFragment extends Fragment {

    View mainView;
    TextView textHawkerName, textHawkerAddress;

    JSONObject hawkerData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_item_list, container, false);

        try {
            String hawkerDataString = getArguments().getString("hawkerData");

            hawkerData = new JSONObject(hawkerDataString);

            setDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return mainView;
    }

    private void setDetails() {
        textHawkerName = mainView.findViewById(R.id.text_hawker_name);
        textHawkerAddress = mainView.findViewById(R.id.text_address);

        try {
            textHawkerName.setText(hawkerData.getString("name"));
            textHawkerAddress.setText(hawkerData.getString("address"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
