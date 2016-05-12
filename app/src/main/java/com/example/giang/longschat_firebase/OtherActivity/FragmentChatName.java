package com.example.giang.longschat_firebase.OtherActivity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.giang.longschat_firebase.Adapter.ChatRoomAdapter;
import com.example.giang.longschat_firebase.Object.ChatRoomObject;
import com.example.giang.longschat_firebase.R;
import com.example.giang.longschat_firebase.Tabs.ChatRoomTab;

import java.util.ArrayList;

/**
 * Created by giang on 5/12/2016.
 */
public class FragmentChatName extends Fragment {
    View v;
    ListView lvChatRoom;
    ChatRoomAdapter adapter;
    ArrayList<ChatRoomObject> arrl;
    String [] main = {"Thành phố", "Tình yêu - Tình bạn", "Học tập" , "Giải trí" , "Hội nhóm", "Khác"};
    String [] sub = {"Theo thành phố, vùng miền ...", "Nơi giao lưu, kết bạn, ...", "Góc học tập mini của bạn", "Nơi thư giãn sau mỗi ngày làm việc mệt nhọc", "Tập hợp các nhóm, phần tử nguy hiểm =))", "Và nhiều hơn thế ..."};
    int [] icon = {R.drawable.ic_place, R.drawable.ic_love, R.drawable.ic_learn, R.drawable.ic_entertainment, R.drawable.ic_group, R.drawable.ic_other};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_chatroom_layout, container, false);
        lvChatRoom = (ListView) v.findViewById(R.id.lvChatRoom);

        arrl = new ArrayList<ChatRoomObject>();
        for (int i = 0; i < main.length; i++){
            arrl.add(new ChatRoomObject(main[i], sub[i], icon[i]));
        }
        adapter = new ChatRoomAdapter(getActivity(), R.layout.custom_row_chatroom, arrl);
        lvChatRoom.setAdapter(adapter);

        lvChatRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ChatRoomObject room = (ChatRoomObject) parent.getItemAtPosition(position);
                Bundle data = new Bundle();
                data.putInt("position", position);
                ChatRoomTab.ft = getActivity().getFragmentManager().beginTransaction();

                FragmentSubRoomChat sub = new FragmentSubRoomChat();
                sub.setArguments(data);

                ChatRoomTab.ft.replace(R.id.fragment_container, sub);
                ChatRoomTab.ft.commit();

            }
        });

        return v;
    }
}
