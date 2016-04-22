package com.example.giang.longschat_firebase.Firebase;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by giang on 6/4/2016.
 */
public class FirebaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
