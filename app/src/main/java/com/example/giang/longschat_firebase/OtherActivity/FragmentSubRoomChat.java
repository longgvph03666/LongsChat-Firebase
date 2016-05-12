package com.example.giang.longschat_firebase.OtherActivity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.giang.longschat_firebase.R;
import com.example.giang.longschat_firebase.Tabs.ChatRoomTab;

/**
 * Created by giang on 5/12/2016.
 */
public class FragmentSubRoomChat extends Fragment {
    ListView lv;
    View v;
    ImageView imageView;
    String [] title ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_sub_roomchat_layout, container, false);
        lv = (ListView) v.findViewById(R.id.lvSubRoom);
        imageView = (ImageView) v.findViewById(R.id.ivBackRoom);

        Bundle extras = getArguments();
        int position = extras.getInt("position");

        switch (position){
            case 0:
                title = new String[]{"Hà Nội", "TP.HCM"};

                break;
            case 1:
                title = new String[]{"Kết bạn bốn phương 1", "Kết bạn bốn phương 2", "Kết bạn bốn phương 3", "Kết bạn bốn phương 4", "Kết bạn bốn phương 5", "Công viên 1", "Công viên 2", "Thế giới teen 1", "Thế giới teen 2", "Thế giới teen 3"};
                break;
            case 2:
                title = new String[]{"Hoa học trò", "Sinh viên VN", "Tiếng anh", "Game học"};
                break;
            case 3:
                title = new String[]{"Tán phét cùng thần tượng", "Gieo quẻ trúng ô tô", "Tán gái nhận thưởng"};
                break;
            case 4:
                title = new String[]{"Hội người cao tuổi VN", "Hội trẻ trâu VN", "IS group", "Hội anh hùng bàn phím thế giới", "Hội làm tình ... Nguyện"};
                break;
            case 5:
                title = new String[]{"Boys", "Girls"};
                break;
        }

        ArrayAdapter adapter = new ArrayAdapter(getActivity().getApplicationContext(), R.layout.simple_list_item_1, title );
        lv.setAdapter(adapter);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatRoomTab.ft = getActivity().getFragmentManager().beginTransaction();

                FragmentChatName name = new FragmentChatName();

                ChatRoomTab.ft.replace(R.id.fragment_container, name);
                ChatRoomTab.ft.commit();
            }
        });

        return v;
    }
}
