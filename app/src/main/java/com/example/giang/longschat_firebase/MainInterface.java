package com.example.giang.longschat_firebase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.giang.longschat_firebase.Firebase.FirebaseAdapter;
import com.example.giang.longschat_firebase.Other.OnClearFromRecentService;
import com.example.giang.longschat_firebase.OtherActivity.LoginActivity;
import com.example.giang.longschat_firebase.SlidingTab.SlidingTabLayout;
import com.example.giang.longschat_firebase.SlidingTab.TabAdapter;

public class MainInterface extends AppCompatActivity {
    SlidingTabLayout mSlidingTabLayout;
    ViewPager mViewPager;
    FirebaseAdapter mFirebaseAdapter;
    public static Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_interface);
        getSupportActionBar().hide();
        mActivity = this;
        startService(new Intent(getApplicationContext(), OnClearFromRecentService.class));

        mViewPager = (ViewPager) findViewById(R.id.vp_tab);
        mViewPager.setAdapter(new TabAdapter(getSupportFragmentManager(), this));

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tab);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
        mSlidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.tv_tab);
        mSlidingTabLayout.setViewPager(mViewPager);

        mFirebaseAdapter = new FirebaseAdapter(getApplicationContext());
        mFirebaseAdapter.start();
        //mFirebaseAdapter.online();
        mFirebaseAdapter.getInfomation();
    }

    @Override
    protected void onDestroy() {
        mFirebaseAdapter.offline();
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        LoginActivity.mActivity.finish();
        finish();
    }
}
