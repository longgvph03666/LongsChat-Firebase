package com.example.giang.longschat_firebase.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.giang.longschat_firebase.Object.ChatRoomObject;
import com.example.giang.longschat_firebase.R;

import java.util.List;

/**
 * Created by giang on 5/11/2016.
 */
public class ChatRoomAdapter extends ArrayAdapter<ChatRoomObject> {
    View mView;
    public ChatRoomAdapter(Context context, int resource, List<ChatRoomObject> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        mView = convertView;
        if(mView == null){
            LayoutInflater inflater;
            inflater = LayoutInflater.from(getContext());
            mView = inflater.inflate(R.layout.custom_row_chatroom, null);
        }

        ChatRoomObject object = getItem(position);
        if(object != null){
            TextView mainText = (TextView) mView.findViewById(R.id.custom_tvMainTitle);
            TextView subText = (TextView) mView.findViewById(R.id.custom_tvSubTitle);
            ImageView logo = (ImageView) mView.findViewById(R.id.custom_ivIcon_room);

            mainText.setText(object.getMaintitle());
            subText.setText(object.getSubTitle());
            logo.setImageResource(object.getIconResource());

        }



        return mView;
    }
}
