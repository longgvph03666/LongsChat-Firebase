package com.example.giang.longschat_firebase.Firebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.giang.longschat_firebase.MainInterface;
import com.example.giang.longschat_firebase.OtherActivity.LoginActivity;
import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by giang on 6/4/2016.
 */
public class FirebaseAdapter {
    Context mContext;
    final static String PATCH = "https://longs-chat.firebaseio.com/";
    public static boolean isLogin;
    Firebase mFirebase;
    SharedPreferences mSharedPreferences;

    public FirebaseAdapter(Context context) {
        this.mContext = context;
    }

    public void start(){
        mFirebase = new Firebase(PATCH);
    }

    public void login(String email, String pass ){
        mFirebase.authWithPassword(email, pass, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                //System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                isLogin = true;
                Toast.makeText(mContext, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(mContext, MainInterface.class);
                mContext.startActivity(it);
                SharedPreferences.Editor editor = LoginActivity.mSharedPreferences.edit();
                editor.putString("email", LoginActivity.etEmail.getText().toString());
                editor.putString("id", authData.getUid());
                editor.commit();
                getInfomation();
                LoginActivity.mDialog.dismiss();
            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                isLogin = false;
                Toast.makeText(mContext, "Tài khoản hoặc mật khẩu không chính xác !", Toast.LENGTH_SHORT).show();
                LoginActivity.mDialog.dismiss();
            }
        });
    }

    public void signUp( String email, String pass){
        mFirebase.createUser(email, pass, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                //System.out.println("Successfully created user account with uid: " + result.get("uid"));
                Toast.makeText(mContext, "Tạo tài khoản thành công !", Toast.LENGTH_SHORT).show();
                LoginActivity.mDialog2.dismiss();
            }
            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(mContext, "Không thể tạo tài khoản !", Toast.LENGTH_SHORT).show();
                LoginActivity.mDialog2.dismiss();
            }
        });
    }

    public void resendPass(String email){
        mFirebase.resetPassword(email , new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                Toast.makeText(mContext, "Hoàn thành !", Toast.LENGTH_SHORT).show();
                LoginActivity.mDialog2.dismiss();
                LoginActivity.mDialog.dismiss();
            }
            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(mContext, "Thất bại !", Toast.LENGTH_SHORT).show();
                LoginActivity.mDialog2.dismiss();
            }
        });
    }

    public void getInfomation(){
        mSharedPreferences = mContext.getSharedPreferences("user", Context.MODE_PRIVATE);

        mFirebase.child("user").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, String> value = (Map<String, String>) dataSnapshot.getValue();
                // dataSnapshot.getKey()
               if(dataSnapshot.getKey().equals(mSharedPreferences.getString("id", ""))){
                   SharedPreferences.Editor editor = LoginActivity.mSharedPreferences.edit();
                   editor.putString("user_name", value.get("name"));
                   editor.putString("user_age", String.valueOf(value.get("age")));
                   editor.putString("user_address", value.get("address"));
                   editor.putString("user_sex", String.valueOf(value.get("sex")));
                   editor.putString("user_phonenumber", value.get("phoneNumber"));
                   editor.commit();
               }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void putInfomation(String mName, String mAge, String mAddress, String mPhone, String mSex){
        mSharedPreferences = mContext.getSharedPreferences("user", Context.MODE_PRIVATE);

        Map<String, String> user = new HashMap<String, String>();
        user.put("name", mName);
        user.put("age", mAge);
        user.put("address", mAddress);
        user.put("phoneNumber", mPhone);
        user.put("sex", mSex);

        mFirebase.child("user").child(mSharedPreferences.getString("id", "")).setValue(user);
    }




}
