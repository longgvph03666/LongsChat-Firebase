package com.example.giang.longschat_firebase.Tabs;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.giang.longschat_firebase.Adapter.MoreTabAdapter;
import com.example.giang.longschat_firebase.Object.MoreObjectItem;
import com.example.giang.longschat_firebase.OtherActivity.AccountManager;
import com.example.giang.longschat_firebase.OtherActivity.InfomationActivity;
import com.example.giang.longschat_firebase.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreTab extends Fragment {
    String [] titleItem = {"Thông báo & Âm thanh", "Quản lý tài khoản", "Trợ giúp", "Thông tin ứng dụng"};
    int [] icon = {R.drawable.ic_sound_black, R.drawable.ic_profile_black, R.drawable.ic_help_black, R.drawable.icon_info_app_black};
    ListView lvItems;
    View v;
    LinearLayout rowUser;
    ArrayList<MoreObjectItem> arrl;
    TextView tvName, tvEmail;
    SharedPreferences mSharedPreferences;
    public MoreTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_more_tab, container, false);
        mSharedPreferences = getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
        setViews();


        arrl = new ArrayList<MoreObjectItem>();
        for (int i = 0; i < titleItem.length; i++){
            arrl.add(new MoreObjectItem(titleItem[i], icon[i]));
        }

        MoreTabAdapter adapter = new MoreTabAdapter(getActivity(), R.layout.custom_layout_moretab_item, arrl);
        lvItems.setAdapter(adapter);

        rowUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), InfomationActivity.class);
                startActivity(it);
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:

                        break;
                    case 1:
                    {
                        Intent it = new Intent(getActivity(), AccountManager.class);
                        startActivity(it);
                    }
                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                }
            }
        });



        return v;

    }

    public void setViews(){
        lvItems = (ListView) v.findViewById(R.id.more_listItem);
        rowUser = (LinearLayout) v.findViewById(R.id.row_user);
        tvName = (TextView) v.findViewById(R.id.mt_tvUserName);
        tvEmail = (TextView) v.findViewById(R.id.mt_tvUserEmail);

        tvName.setText(mSharedPreferences.getString("user_name", ""));
        tvEmail.setText(mSharedPreferences.getString("email", ""));
    }

    @Override
    public void onResume() {
        tvName.setText(mSharedPreferences.getString("user_name", ""));
        tvEmail.setText(mSharedPreferences.getString("email", ""));
        super.onResume();
    }
}
