package com.example.giang.longschat_firebase.OtherActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.giang.longschat_firebase.Adapter.MoreTabAdapter;
import com.example.giang.longschat_firebase.Firebase.FirebaseAdapter;
import com.example.giang.longschat_firebase.MainInterface;
import com.example.giang.longschat_firebase.Object.MoreObjectItem;
import com.example.giang.longschat_firebase.R;

import java.util.ArrayList;

public class AccountManager extends AppCompatActivity {
    String [] titles = {"Thay đổi Email", "Thay đổi mật khẩu", "Xóa tài khoản", "Đăng xuất"};
    int [] icons = {R.drawable.ic_email_black, R.drawable.ic_password_black, R.drawable.ic_user_del_black, R.drawable.ic_log_out_black};
    ArrayList<MoreObjectItem> arrl;
    ListView lv;
    public static Activity mActivity;
    public static Dialog mDialog;
    Button btContinue;
    EditText etPassword;
    SharedPreferences mSharedPreferences;
    FirebaseAdapter mFirebaseAdapter;
    public static AlertDialog.Builder mBuilder;
    public static String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Quản lý tài khoản");
        mActivity = this;
        lv = (ListView) findViewById(R.id.am_lv);

        mSharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        mFirebaseAdapter = new FirebaseAdapter(this);
        mFirebaseAdapter.start();

        // get user_id
        user_id = mSharedPreferences.getString("user_id", "");

        arrl = new ArrayList<MoreObjectItem>();
        for (int i = 0; i < titles.length; i++){
            arrl.add(new MoreObjectItem(titles[i], icons[i]));
        }

        MoreTabAdapter adapter = new MoreTabAdapter(getApplicationContext(), R.layout.custom_layout_moretab_item, arrl);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                    {
                        Intent it = new Intent(AccountManager.this, ChangeEmail.class);
                        startActivity(it);
                    }
                        break;
                    case 1:
                    {
                        Intent it = new Intent(AccountManager.this, ChangePassword.class);
                        startActivity(it);
                    }
                        break;
                    case 2:
                        mDialog = new Dialog(AccountManager.this);
                        mDialog.setTitle("Xóa tài khoản");
                        mDialog.setContentView(R.layout.custom_delete_user);
                        mDialog.show();

                        etPassword =(EditText) mDialog.findViewById(R.id.etPassword_dialog_delete_user);
                        btContinue = (Button) mDialog.findViewById(R.id.btContinue_dialog_delete_user);

                        btContinue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!etPassword.getText().toString().equals("")){
                                    if(etPassword.getText().toString().equals(mSharedPreferences.getString("user_password", ""))){
                                        mBuilder = new AlertDialog.Builder(AccountManager.this);
                                        mBuilder.setTitle("Xóa ?");
                                        mBuilder.setMessage("Bạn có chắc muốn xóa tài khoản " + mSharedPreferences.getString("user_email", "") + " ?");
                                        mBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                mDialog.dismiss();
                                            }
                                        });

                                        mBuilder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                mFirebaseAdapter.deleteUser(mSharedPreferences.getString("user_email", ""), mSharedPreferences.getString("user_password", ""));
                                                dialog.dismiss();
                                                mDialog.dismiss();
                                                logout();
                                            }
                                        });

                                        mBuilder.create().show();
                                    }else{
                                        Toast.makeText(AccountManager.this, "Mật khẩu không đúng !", Toast.LENGTH_SHORT).show();
                                        etPassword.requestFocus();
                                    }
                                }else{
                                    Toast.makeText(AccountManager.this, "Không được để trống !", Toast.LENGTH_SHORT).show();
                                    etPassword.requestFocus();
                                }
                            }
                        });

                        break;
                    case 3:
                        AlertDialog.Builder builder = new AlertDialog.Builder(AccountManager.this);
                        builder.setTitle("Đăng xuất");
                        builder.setMessage("Bạn sẽ không thể nhận tin nhắn từ bạn bè của bạn. Bạn có chắc ?");
                        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder.setPositiveButton("Chắc chắn", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logout();
                            }
                        });

                        builder.create().show();
                        break;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout(){
        mFirebaseAdapter.offlineForLogout();
        SharedPreferences mSharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        mSharedPreferences.edit().clear().commit();

        Intent it = new Intent(AccountManager.this, LoginActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(it);


        LoginActivity.mActivity.finish();
        MainInterface.mActivity.finish();
        finish();
    }
}
