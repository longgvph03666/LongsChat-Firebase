package com.example.giang.longschat_firebase.OtherActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giang.longschat_firebase.Firebase.FirebaseAdapter;
import com.example.giang.longschat_firebase.MainInterface;
import com.example.giang.longschat_firebase.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    TextView tvSignUp, tvForget;
    public static EditText etEmail, etPass, etEmailCreate, etPassCreate, etPassConfirm, etResendEmail;
    ScrollView scrollView;
    Button btLogin, btSign, btReset, btResend;
    Snackbar mSnackbar;
    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
    FirebaseAdapter mFirebaseAdapter;
    public static Dialog mDialog;
    public static Dialog mDialog2;
    public static SharedPreferences mSharedPreferences;
    public static Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        direct(); // Điều hướng khi đã đăng nhập
        setViews();
        setEvent();
        mActivity = this;

        mFirebaseAdapter = new FirebaseAdapter(this);
        mFirebaseAdapter.start();
    }

    public void setViews(){
        tvForget = (TextView) findViewById(R.id.lg_tvForget);
        tvSignUp = (TextView) findViewById(R.id.lg_tvSignUp);
        etEmail = (EditText) findViewById(R.id.lg_etEmail);
        etPass = (EditText) findViewById(R.id.lg_etPass);
        scrollView = (ScrollView) findViewById(R.id.lg_scrollview);
        btLogin = (Button) findViewById(R.id.lg_btLogin);

        tvForget.setPaintFlags(tvForget.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG); // Gạch chân text
        tvSignUp.setPaintFlags(tvSignUp.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        etPass.setTypeface(Typeface.DEFAULT); // Set font chữ về mặc định

    }

    public void setEvent(){

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String pass = etPass.getText().toString();

                if (!email.equals("") && !pass.equals("")){
                    if (isValidEmailAddress(email)){
                        mDialog = new Dialog(LoginActivity.this);
                        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // No title
                        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT)); // No backfround
                        mDialog.setContentView(R.layout.custom_lg_login_dialog);
                        mDialog.setCancelable(false); // Disable cancel
                        mDialog.show();
                        mFirebaseAdapter.login(email, pass);
                    }else{
                        mSnackbar = Snackbar.make(v, "Định dạng email sai !", Snackbar.LENGTH_LONG);
                        mSnackbar.setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mSnackbar.dismiss();
                            }
                        });
                        mSnackbar.show();
                        etEmail.requestFocus();
                    }
                }else{
                    mSnackbar = Snackbar.make(v, "Không được để trống !", Snackbar.LENGTH_LONG);
                    mSnackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSnackbar.dismiss();
                        }
                    });
                    mSnackbar.show();
                    etEmail.requestFocus();
                }
            }
        });


        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = new Dialog(LoginActivity.this);
                mDialog.setContentView(R.layout.custom_lg_signup_dialog);
                mDialog.setTitle("Đăng ký");
                mDialog.show();

                etEmailCreate = (EditText) mDialog.findViewById(R.id.etEmail_dialog_sign);
                etPassCreate = (EditText) mDialog.findViewById(R.id.etPass_dialog_sign);
                etPassConfirm = (EditText) mDialog.findViewById(R.id.etPassConfirm_dialog_sign);
                btSign = (Button) mDialog.findViewById(R.id.btSign_dialog_sign);
                btReset = (Button) mDialog.findViewById(R.id.btReset_dialog_sign);
                etEmailCreate.setText(etEmail.getText().toString());

                btSign.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!etEmailCreate.getText().toString().equals("") && !etPassCreate.getText().toString().equals("") && !etPassConfirm.getText().toString().equals("")){
                            if(isValidEmailAddress(etEmailCreate.getText().toString())){
                                if (etPassCreate.getText().toString().equals(etPassConfirm.getText().toString())){
                                    mDialog2 = new Dialog(LoginActivity.this);
                                    mDialog2.requestWindowFeature(Window.FEATURE_NO_TITLE); // No title
                                    mDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT)); // No backfround
                                    mDialog2.setContentView(R.layout.custom_lg_login_dialog);
                                    mDialog2.setCancelable(false); // Disable cancel
                                    mDialog2.show();
                                    mFirebaseAdapter.signUp(etEmailCreate.getText().toString(), etPassCreate.getText().toString());
                                    etEmail.setText(etEmailCreate.getText().toString());
                                    mDialog.dismiss();
                                }else{
                                    mSnackbar = Snackbar.make(v, "Mật khẩu không trùng nhau !", Snackbar.LENGTH_LONG);
                                    mSnackbar.setAction("OK", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mSnackbar.dismiss();
                                        }
                                    });
                                    mSnackbar.show();
                                    etEmailCreate.requestFocus();
                                }
                            }else{
                                mSnackbar = Snackbar.make(v, "Định dạng email sai !", Snackbar.LENGTH_LONG);
                                mSnackbar.setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mSnackbar.dismiss();
                                    }
                                });
                                mSnackbar.show();
                                etEmailCreate.requestFocus();
                            }
                        }else{
                            mSnackbar = Snackbar.make(v, "Không được để trống !", Snackbar.LENGTH_LONG);
                            mSnackbar.setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mSnackbar.dismiss();
                                }
                            });
                            mSnackbar.show();
                            etEmailCreate.requestFocus();
                        }
                    }
                });

                btReset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etEmailCreate.setText("");
                        etPassCreate.setText("");
                        etPassConfirm.setText("");
                    }
                });

            }
        });

        tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = new Dialog(LoginActivity.this);
                mDialog.setContentView(R.layout.custom_lg_resend_dialog);
                mDialog.setTitle("Lấy lại mật khẩu");
                mDialog.show();

                etResendEmail = (EditText) mDialog.findViewById(R.id.etResendEmail_resend);
                btResend = (Button) mDialog.findViewById(R.id.btResend_resend);
                etResendEmail.setText(etEmail.getText().toString());

                btResend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!etResendEmail.getText().toString().equals("")){
                            if (isValidEmailAddress(etResendEmail.getText().toString())){
                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
                                mBuilder.setTitle("Bạn có chắc ?");
                                mBuilder.setMessage("Chúng tôi sẽ gửi mật khẩu mới đến cho bạn bằng chính email này. Vậy hãy chắc chắn rằng đây là email của bạn. ");
                                mBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                mBuilder.setPositiveButton("Tất nhiên", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mDialog2 = new Dialog(LoginActivity.this);
                                        mDialog2.requestWindowFeature(Window.FEATURE_NO_TITLE); // No title
                                        mDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT)); // No backfround
                                        mDialog2.setContentView(R.layout.custom_lg_login_dialog);
                                        mDialog2.setCancelable(false); // Disable cancel
                                        mDialog2.show();
                                        etEmail.setText(etResendEmail.getText().toString());
                                        mFirebaseAdapter.resendPass(etResendEmail.getText().toString());
                                        mDialog.dismiss();

                                    }
                                });

                                mBuilder.create().show();
                            }else{
                                Toast.makeText(LoginActivity.this, "Định dạng email sai !", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(LoginActivity.this, "Không được để trống !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }



    public static boolean isValidEmailAddress(String emailAddress) {
        String emailRegEx;
        Pattern pattern;
        // Regex for a valid email address
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    public void direct(){
        mSharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        if(!mSharedPreferences.getString("user_email", "").equals("")){
            Intent it = new Intent(LoginActivity.this, MainInterface.class);
            startActivity(it);
        }
    }
}
