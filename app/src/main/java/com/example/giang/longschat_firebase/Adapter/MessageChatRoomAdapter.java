package com.example.giang.longschat_firebase.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.giang.longschat_firebase.Object.MessageChatRoomObject;
import com.example.giang.longschat_firebase.R;

import java.util.List;

/**
 * Created by giang on 5/13/2016.
 */
public class MessageChatRoomAdapter extends ArrayAdapter<MessageChatRoomObject>{
    View mView;

    public MessageChatRoomAdapter(Context context, int resource, List<MessageChatRoomObject> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mView = convertView;
        if(mView == null){
            LayoutInflater inflater;
            inflater = LayoutInflater.from(getContext());
            mView = inflater.inflate(R.layout.custom_row_message_chatroom, null);
        }

        MessageChatRoomObject object = getItem(position);
        if(object != null){
            TextView name = (TextView) mView.findViewById(R.id.message_tvName);
            TextView message = (TextView) mView.findViewById(R.id.message_tvMessage);
            TextView time = (TextView) mView.findViewById(R.id.message_tvTime);

            name.setText(object.getName());
            message.setText(object.getMessage());
            time.setText(object.getTime());
            SharedPreferences mSharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
//            if(object.getName().toString().equals(mSharedPreferences.getString("user_name", "Not yet"))){
//                name.setTextColor(getContext().getResources().getColor(R.color.red));
//            }

        }



        return mView;
    }
}
