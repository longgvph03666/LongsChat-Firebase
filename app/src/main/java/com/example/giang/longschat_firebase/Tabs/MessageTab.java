package com.example.giang.longschat_firebase.Tabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.giang.longschat_firebase.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageTab extends Fragment {


    public MessageTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_tab, container, false);
    }

}
