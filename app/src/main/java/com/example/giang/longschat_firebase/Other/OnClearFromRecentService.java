package com.example.giang.longschat_firebase.Other;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.giang.longschat_firebase.Firebase.FirebaseAdapter;

/**
 * Created by giang on 22/4/2016.
 */
public class OnClearFromRecentService extends Service {
    FirebaseAdapter mFirebaseAdapter;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mFirebaseAdapter = new FirebaseAdapter(this);
        mFirebaseAdapter.start();
        mFirebaseAdapter.online();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.offline();
    }

    public void onTaskRemoved(Intent rootIntent) {
        mFirebaseAdapter.offline();
        //Code here
        stopSelf();
    }
}