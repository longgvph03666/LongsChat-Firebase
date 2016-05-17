package com.example.giang.longschat_firebase.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.giang.longschat_firebase.Object.MessageChatRoomObject;
import com.example.giang.longschat_firebase.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giang on 5/12/2016.
 */
public class RecyclerChatRoomAdapter extends
        RecyclerView.Adapter<RecyclerChatRoomAdapter.RecyclerViewHolder> {
    Context mContext;
    private List<MessageChatRoomObject> listData = new ArrayList<MessageChatRoomObject>();


    public RecyclerChatRoomAdapter(Context c, List<MessageChatRoomObject> listData) {
        this.listData = listData;
        this.mContext = c;
    }

    public void updateList(List<MessageChatRoomObject> data) {
        listData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                 int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.custom_row_message_chatroom, viewGroup, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {
        String name = listData.get(position).getName();
        viewHolder.tvName.setText(listData.get(position).getName());
        viewHolder.tvMessage.setText(listData.get(position).getMessage());
        viewHolder.tvTime.setText(listData.get(position).getTime());

        SharedPreferences mSharedPreferences = mContext.getSharedPreferences("user", Context.MODE_PRIVATE);
        if(name.equals(mSharedPreferences.getString("user_name", "Not yet"))){
            //viewHolder.tvName.setTextColor(mContext.getResources().getColor(R.color.red));
            //Log.d("Pair", mSharedPreferences.getString("user_name", "Not yet") + " VS " + name);
        }
    }

    public void addItem(int position, MessageChatRoomObject data) {
        listData.add(position, data);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        listData.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * ViewHolder for item view of list
     * */

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        public TextView tvName, tvMessage, tvTime;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.message_tvName);
            tvMessage = (TextView) itemView.findViewById(R.id.message_tvMessage);
            tvTime = (TextView) itemView.findViewById(R.id.message_tvTime);

        }

        // Event click
        @Override
        public void onClick(View v) {
            Log.d("Clicked", "Clicked !");
        }
    }

}

