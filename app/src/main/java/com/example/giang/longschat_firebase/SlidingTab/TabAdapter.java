package com.example.giang.longschat_firebase.SlidingTab;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.giang.longschat_firebase.R;
import com.example.giang.longschat_firebase.Tabs.ChatRoomTab;
import com.example.giang.longschat_firebase.Tabs.FriendsTab;
import com.example.giang.longschat_firebase.Tabs.MessageTab;
import com.example.giang.longschat_firebase.Tabs.MoreTab;

/**
 * Created by giang on 5/4/2016.
 */
public class TabAdapter extends FragmentPagerAdapter{
    Context mContext;
    String [] titles = {"Message", "Friends", "ChatRoom", "More"};
    int [] icons = {R.drawable.ic_message_group_white, R.drawable.ic_people_white, R.drawable.ic_chatroom_white, R.drawable.ic_more_white};
    int heightIcon;

    public TabAdapter(FragmentManager fm, Context c) {
        super(fm);

        mContext = c;
        double scale = c.getResources().getDisplayMetrics().density;
        heightIcon = (int)(24*scale + 0.5f);
    }


    @Override
    public Fragment getItem(int position) {
        Fragment mFragment = null;
//        switch (position){
//            case 0:
//                mFragment = new MessageTab();
//                break;
//            case 1:
//                mFragment = new FriendsTab();
//                break;
//            case 2:
//                mFragment = new ChatRoomTab();
//                break;
//            case 3:
//                mFragment = new MoreTab();
//                break;
//        }
        if(position == 0){
            mFragment = new MessageTab();
        }else if(position == 1){
            mFragment = new FriendsTab();
        }else if(position == 2){
            mFragment = new ChatRoomTab();
        }else{
            mFragment = new MoreTab();
        }

        Bundle b = new Bundle();
        b.putInt("position", position);

        mFragment.setArguments(b);
        return mFragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        Drawable d = mContext.getResources().getDrawable(icons[position]);
        d.setBounds(0,0, heightIcon, heightIcon);

        ImageSpan is = new ImageSpan(d);
        SpannableString sp = new SpannableString(" ");
        sp.setSpan(is, 0, sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return (sp);
    }
}
