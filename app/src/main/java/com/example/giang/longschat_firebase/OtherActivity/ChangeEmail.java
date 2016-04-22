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

public class ChangeEmail extends AppCompatActivity {
    TextView tvCode;
    EditText etNewEmail, etPassword, etSecurityCode;
    Button btChange;
    Random mRandom;
    SharedPreferences mSharedPreferences;
    int number;
    FirebaseAdapter mFirebaseAdapter;
    public static Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        setTitle("Thay đổi Email");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setViews();
        mActivity =  this;
        mFirebaseAdapter = new FirebaseAdapter(this);
        mFirebaseAdapter.start();

        mSharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
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

    public void setViews(){
        tvCode = (TextView) findViewById(R.id.ce_tvCode);
        etNewEmail = (EditText) findViewById(R.id.ce_etNewEmail);
        etPassword = (EditText) findViewById(R.id.ce_etPassword);
        etSecurityCode = (EditText) findViewById(R.id.ce_etSecurityCode);
        btChange = (Button) findViewById(R.id.ce_btChange);

        tvCode.setPaintFlags(tvCode.getPaintFlags() |   Paint.STRIKE_THRU_TEXT_FLAG); // Set underline
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void change(){
        if(!etNewEmail.getText().toString().equals("")&&!etPassword.getText().toString().equals("")&&!etSecurityCode.getText().toString().equals("")){
            if(LoginActivity.isValidEmailAddress(etNewEmail.getText().toString())){
                if(etPassword.getText().toString().equals(mSharedPreferences.getString("user_password", ""))){
                    if(etSecurityCode.getText().toString().equals(String.valueOf(number))){
                        mFirebaseAdapter.changeEmail(mSharedPreferences.getString("user_email", ""), etPassword.getText().toString(), etNewEmail.getText().toString());
                    }else{
                        Toast.makeText(ChangeEmail.this, "Mã bảo mật không đúng !", Toast.LENGTH_SHORT).show();
                        etSecurityCode.requestFocus();
                    }
                }else{
                    Toast.makeText(ChangeEmail.this, "Mật khẩu không đúng !", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                }
            }else{
                Toast.makeText(ChangeEmail.this, "Định dạng Email sai !", Toast.LENGTH_SHORT).show();
                etNewEmail.requestFocus();
            }
        }else{
            Toast.makeText(ChangeEmail.this, "Không được để trống !", Toast.LENGTH_SHORT).show();
            etNewEmail.requestFocus();
        }
    }
}


