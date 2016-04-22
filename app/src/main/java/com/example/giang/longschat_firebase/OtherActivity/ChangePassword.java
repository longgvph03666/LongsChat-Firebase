package com.example.giang.longschat_firebase.OtherActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giang.longschat_firebase.Firebase.FirebaseAdapter;
import com.example.giang.longschat_firebase.R;

import java.util.Random;

public class ChangePassword extends AppCompatActivity {
    TextView tvCode;
    EditText etOldPass, etNewPass, etConfirmNewPass, etCode;
    Button btChange;
    SharedPreferences mSharedPreferences;
    public static Activity mActivity;
    FirebaseAdapter mFirebaseAdapter;
    Random mRandom;
    int number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mActivity = this;
        setTitle("Thay đổi mật khẩu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setViews();

        mSharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        mFirebaseAdapter = new FirebaseAdapter(this);
        mFirebaseAdapter.start();

        mRandom = new Random();
        number = mRandom.nextInt(10000);
        tvCode.setText(number + "");

        btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
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


    public void setViews(){
        etOldPass = (EditText) findViewById(R.id.cp_etOldPassword);
        etNewPass = (EditText) findViewById(R.id.cp_etNewPassword);
        etConfirmNewPass = (EditText) findViewById(R.id.cp_etConfirmNewPassword);
        etCode = (EditText) findViewById(R.id.cp_etSecurityCode);
        tvCode = (TextView) findViewById(R.id.cp_tvCode);
        btChange = (Button) findViewById(R.id.cp_btChange);

        tvCode.setPaintFlags(tvCode.getPaintFlags() |   Paint.STRIKE_THRU_TEXT_FLAG); // Set underline
    }

    public void change(){
        if(!etOldPass.getText().toString().equals("") && !etNewPass.getText().toString().equals("") && !etConfirmNewPass.getText().toString().equals("")){
            if(etOldPass.getText().toString().equals(mSharedPreferences.getString("user_password", ""))){
                if(etNewPass.getText().toString().equals(etConfirmNewPass.getText().toString())){
                    if(etCode.getText().toString().equals(String.valueOf(number))){
                        mFirebaseAdapter.changePassword(mSharedPreferences.getString("user_email", ""), etOldPass.getText().toString(), etNewPass.getText().toString());
                    }else{
                        Toast.makeText(ChangePassword.this, "Mã bảo mật không đúng !", Toast.LENGTH_SHORT).show();
                        etCode.requestFocus();
                    }
                }else{
                    Toast.makeText(ChangePassword.this, "Nhập lại mật khẩu không đúng !", Toast.LENGTH_SHORT).show();
                    etConfirmNewPass.requestFocus();
                }
            }else{
                Toast.makeText(ChangePassword.this, "Mật khẩu cũ không đúng !", Toast.LENGTH_SHORT).show();
                etOldPass.requestFocus();
            }
        }else{
            Toast.makeText(ChangePassword.this, "Không được để trống !", Toast.LENGTH_SHORT).show();
            etOldPass.requestFocus();
        }
    }


}
