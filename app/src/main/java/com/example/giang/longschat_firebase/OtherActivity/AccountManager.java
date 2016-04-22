package com.example.giang.longschat_firebase.OtherActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.giang.longschat_firebase.Adapter.MoreTabAdapter;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Quản lý tài khoản");
        mActivity = this;
        lv = (ListView) findViewById(R.id.am_lv);

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

                        break;
                    case 1:

                        break;
                    case 2:

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
                                SharedPreferences mSharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                                mSharedPreferences.edit().clear().commit();

                                Intent it = new Intent(AccountManager.this, LoginActivity.class);
                                it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(it);


                                LoginActivity.mActivity.finish();
                                MainInterface.mActivity.finish();
                                finish();
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
}
