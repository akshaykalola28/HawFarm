package com.project.hawfarm;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.zip.Inflater;

public class ItemListFragment extends Fragment {

    View mainView;
    ListView listView;
    String[] vegname = {"A", "B", "C"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_item_list, container, false);

        listView = mainView.findViewById(R.id.simpleListView);
        VegAdapter vegAdapter = new VegAdapter(getActivity().getApplicationContext(), vegname);
        listView.setAdapter(vegAdapter);

        return mainView;
    }
}

class VegAdapter extends ArrayAdapter {
    String[] a1;

    public VegAdapter(Context context, String[] b1) {
        super(context, R.layout.add_item, R.id.itemName, b1);
        this.a1 = b1;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.add_item, parent, false);
        TextView itemName = (TextView) row.findViewById(R.id.itemName);
        itemName.setText(a1[position]);

        return row;
    }
}
