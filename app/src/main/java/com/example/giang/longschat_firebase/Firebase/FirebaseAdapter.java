package com.example.giang.longschat_firebase.Firebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.giang.longschat_firebase.MainInterface;
import com.example.giang.longschat_firebase.Object.MessageChatRoomObject;
import com.example.giang.longschat_firebase.OtherActivity.AccountManager;
import com.example.giang.longschat_firebase.OtherActivity.ChangeEmail;
import com.example.giang.longschat_firebase.OtherActivity.ChangePassword;
import com.example.giang.longschat_firebase.OtherActivity.ChatRoomActivity;
import com.example.giang.longschat_firebase.OtherActivity.InfomationActivity;
import com.example.giang.longschat_firebase.OtherActivity.LoginActivity;
import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by giang on 6/4/2016.
 */
public class FirebaseAdapter {
    Context mContext;
    public final static String PATCH = "https://longs-chat.firebaseio.com/";
    public static boolean isLogin;
    Firebase mFirebase;
    public static SharedPreferences mSharedPreferences;
    SharedPreferences.Editor editor = LoginActivity.mSharedPreferences.edit();;

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
                it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                mContext.startActivity(it);
                editor.putString("user_email", LoginActivity.etEmail.getText().toString());
                editor.putString("user_id", authData.getUid());
                editor.putString("user_password", LoginActivity.etPass.getText().toString());
                editor.commit();
                getInfomation();
                LoginActivity.mDialog.dismiss();
                LoginActivity.mActivity.finish();
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
               if(dataSnapshot.getKey().equals(mSharedPreferences.getString("user_id", ""))){
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

        mFirebase.child("user").child(mSharedPreferences.getString("user_id", "")).setValue(user);
        Toast.makeText(mContext, "Đã cập nhật thông tin !", Toast.LENGTH_SHORT).show();
        InfomationActivity.mActivity.finish();
    }

    public void putMessageChatRoom(String mName, String mMessage, String mTime, int mPosition){
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = dateFormat.format(new Date());

        Map<String, String> msg = new HashMap<String, String>();
        msg.put("name", mName);
        msg.put("message", mMessage);
        msg.put("time", mTime);

        switch (mPosition){
            // location
            case 11:
                mFirebase.child("chat").child("room").child("location").child("HN").child(time).setValue(msg);
                break;
            case 12:
                mFirebase.child("chat").child("room").child("location").child("TP-HCM").setValue(msg);
                break;
            // friends
            case 21:
                mFirebase.child("chat").child("room").child("friends").child("KetBanBonPhuong1").setValue(msg);
                break;
            case 22:
                mFirebase.child("chat").child("room").child("friends").child("KetBanBonPhuong2").setValue(msg);
                break;
            case 23:
                mFirebase.child("chat").child("room").child("friends").child("KetBanBonPhuong3").setValue(msg);
                break;
            case 24:
                mFirebase.child("chat").child("room").child("friends").child("KetBanBonPhuong4").setValue(msg);
                break;
            case 25:
                mFirebase.child("chat").child("room").child("friends").child("KetBanBonPhuong5").setValue(msg);
                break;
            case 26:
                mFirebase.child("chat").child("room").child("friends").child("CongVien1").setValue(msg);
                break;
            case 27:
                mFirebase.child("chat").child("room").child("friends").child("CongVien2").setValue(msg);
                break;
            case 28:
                mFirebase.child("chat").child("room").child("friends").child("TheGioiTeen1").setValue(msg);
                break;
            case 29:
                mFirebase.child("chat").child("room").child("friends").child("TheGioiTeen2").setValue(msg);
                break;
            case 210:
                mFirebase.child("chat").child("room").child("friends").child("TheGioiTeen3").setValue(msg);
                break;
            // learn
            case 31:
                mFirebase.child("chat").child("room").child("learn").child("HoaHocTro").setValue(msg);
                break;
            case 32:
                mFirebase.child("chat").child("room").child("learn").child("SinhVienVN").setValue(msg);
                break;
            case 33:
                mFirebase.child("chat").child("room").child("learn").child("TiengAnh").setValue(msg);
                break;
            case 34:
                mFirebase.child("chat").child("room").child("learn").child("GameHoc").setValue(msg);
                break;
            // Entertainment
            case 41:
                mFirebase.child("chat").child("room").child("entertainment").child("TanPhetCungThanTuong").setValue(msg);
                break;
            case 42:
                mFirebase.child("chat").child("room").child("entertainment").child("GieoQueTrungOTo").setValue(msg);
                break;
            case 43:
                mFirebase.child("chat").child("room").child("entertainment").child("TanGaiNhanThuong").setValue(msg);
                break;
            // Groups
            case 51:
                mFirebase.child("chat").child("room").child("groups").child("HoiNguoiCaoTuoiVN").setValue(msg);
                break;
            case 52:
                mFirebase.child("chat").child("room").child("groups").child("HoiTreTrauVN").setValue(msg);
                break;
            case 53:
                mFirebase.child("chat").child("room").child("groups").child("ISGroup").setValue(msg);
                break;
            case 54:
                mFirebase.child("chat").child("room").child("groups").child("HoiAnhHungBanPhimTheGioi").setValue(msg);
                break;
            case 55:
                mFirebase.child("chat").child("room").child("groups").child("HoiLamTinhNguyen").setValue(msg);
                break;
            // Other
            case 61:
                mFirebase.child("chat").child("room").child("other").child("Boys").setValue(msg);
                break;
            case 62:
                mFirebase.child("chat").child("room").child("other").child("Girls").setValue(msg);
                break;
        }

    }

    public void getMessageChatRoom(){
        mFirebase.child("chat").child("room").child("location").child("HN").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, String> value = (Map<String, String>) dataSnapshot.getValue();
                Log.d("Get", "@");
                ChatRoomActivity.listData.add(new MessageChatRoomObject(value.get("name"), value.get("message"), value.get("time")));
                ChatRoomActivity.adapter.notifyDataSetChanged();
                //ChatRoomActivity.lvMessage.setSelection(ChatRoomActivity.listData.size());
                ChatRoomActivity.lvMessage.smoothScrollToPosition(ChatRoomActivity.listData.size());
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

    public void changeEmail(String mOldEmail, String mPassword, final String mNewEmail){
        mFirebase.changeEmail(mOldEmail, mPassword, mNewEmail, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                Toast.makeText(mContext, "Thay đổi thành công !", Toast.LENGTH_SHORT).show();
                editor.putString("user_email", mNewEmail);
                editor.commit();
                ChangeEmail.mActivity.finish();
            }
            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(mContext, "Thất bại !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void changePassword(String email, String oldPass, final String newPass){
        mFirebase.changePassword(email, oldPass, newPass, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                Toast.makeText(mContext, "Thay đổi thành công !", Toast.LENGTH_SHORT).show();
                editor.putString("user_password", newPass);
                editor.commit();
                ChangePassword.mActivity.finish();
            }
            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(mContext, "Thất bại !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteUser(String email, String password){
        mFirebase.removeUser(email, password, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                Toast.makeText(mContext, "Đã xóa !!!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(mContext, "Thất bại !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void online(){
        mSharedPreferences = mContext.getSharedPreferences("user", Context.MODE_PRIVATE);
        mFirebase.child("online").child(mSharedPreferences.getString("user_id", "")).setValue("true");
    }


    public void offline(){
        mSharedPreferences = mContext.getSharedPreferences("user", Context.MODE_PRIVATE);
        mFirebase.child("online").child(mSharedPreferences.getString("user_id", "")).setValue("false");
    }

    public void offlineForLogout(){
        mSharedPreferences = mContext.getSharedPreferences("user", Context.MODE_PRIVATE);
        mFirebase.child("online").child(AccountManager.user_id).setValue("false");
    }

}
