package com.example.giang.longschat_firebase.OtherActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.giang.longschat_firebase.Firebase.FirebaseAdapter;
import com.example.giang.longschat_firebase.R;

public class InfomationActivity extends AppCompatActivity {
    FirebaseAdapter mFirebaseAdapter;
    SharedPreferences mSharedPreferences;
    EditText etName, etAge, etAddress, etPhoneNumber;
    RadioButton rdMale, rdFemale;
    Button btUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
        setTitle("Cập nhật thông tin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        setViews();
        mFirebaseAdapter = new FirebaseAdapter(this);
        mFirebaseAdapter.start();


        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gender;
                if(rdMale.isChecked()){
                    gender = "true";
                }else{
                    gender = "false";
                }
                mFirebaseAdapter.putInfomation(etName.getText().toString(), etAge.getText().toString(), etAddress.getText().toString(), etPhoneNumber.getText().toString(), gender);
                SharedPreferences.Editor editor = LoginActivity.mSharedPreferences.edit();
                editor.putString("user_name", etName.getText().toString());
                editor.putString("user_age", etAge.getText().toString());
                editor.putString("user_address", etAddress.getText().toString());
                editor.putString("user_sex", gender);
                editor.putString("user_phonenumber", etPhoneNumber.getText().toString());
                editor.commit();
            }
        });
    }

    public void setViews(){
        etName = (EditText) findViewById(R.id.etName);
        etAge = (EditText) findViewById(R.id.etAge);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        rdMale = (RadioButton) findViewById(R.id.rb_male);
        rdFemale = (RadioButton) findViewById(R.id.rb_female);
        btUpdate = (Button) findViewById(R.id.btUpdateInfo);

        etName.setText(mSharedPreferences.getString("user_name", ""));
        etAge.setText(mSharedPreferences.getString("user_age", ""));
        etAddress.setText(mSharedPreferences.getString("user_address", ""));
        etPhoneNumber.setText(mSharedPreferences.getString("user_phonenumber", ""));
        if(mSharedPreferences.getString("user_sex", "").equals("true")){
            rdMale.setChecked(true);
        }else{
            rdFemale.setChecked(true);
        }
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
