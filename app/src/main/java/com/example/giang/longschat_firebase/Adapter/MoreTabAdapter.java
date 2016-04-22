package com.example.giang.longschat_firebase.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.giang.longschat_firebase.Object.MoreObjectItem;
import com.example.giang.longschat_firebase.R;

import java.util.List;

/**
 * Created by giang on 11/4/2016.
 */
public class MoreTabAdapter extends ArrayAdapter<MoreObjectItem>{
    public MoreTabAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public MoreTabAdapter(Context context, int resource, List<MoreObjectItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_layout_moretab_item, null);
        }

        MoreObjectItem object = getItem(position);

        if (object != null) {
            // Anh xa + Gan gia tri
            ImageView ivIcon = (ImageView) v.findViewById(R.id.custom_ivIcon);
            TextView tvTitle = (TextView) v.findViewById(R.id.custom_tvTitle);

            ivIcon.setImageResource(object.getIcon());
            tvTitle.setText(object.getTitle());

        }

        return v;
    }
}
