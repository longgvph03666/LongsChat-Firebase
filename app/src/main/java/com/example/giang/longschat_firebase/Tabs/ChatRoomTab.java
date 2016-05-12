package com.example.giang.longschat_firebase.Tabs;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.giang.longschat_firebase.OtherActivity.FragmentChatName;
import com.example.giang.longschat_firebase.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatRoomTab extends Fragment {
    View mView;
    public static FragmentTransaction ft; // Add fragment in layout


    public ChatRoomTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_chat_room_tab, container, false);
        ft = getActivity().getFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, new FragmentChatName());
        ft.commit();

        return mView;
    }

}
