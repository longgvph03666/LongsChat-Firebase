package com.example.giang.longschat_firebase.OtherActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.giang.longschat_firebase.Adapter.MessageChatRoomAdapter;
import com.example.giang.longschat_firebase.Firebase.FirebaseAdapter;
import com.example.giang.longschat_firebase.Object.MessageChatRoomObject;
import com.example.giang.longschat_firebase.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatRoomActivity extends AppCompatActivity {
   public static  ListView lvMessage;
    EditText etMessage;
    ImageView ivSend;
    public static MessageChatRoomAdapter adapter;
    public static ArrayList<MessageChatRoomObject> listData ;
    SharedPreferences mSharedPreferences;
    String oldTime;
    FirebaseAdapter mFirebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        setVies();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getIntent().getExtras().getString("room_name"));

//        ivSend.setEnabled(false); // Set disable view
//        ivSend.setImageResource(R.drawable.ic_send_button_disable);
        listData = new ArrayList<MessageChatRoomObject>();


        mSharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        mFirebaseAdapter = new FirebaseAdapter(this);
        mFirebaseAdapter.start();

        mFirebaseAdapter.getMessageChatRoom();
        adapter = new MessageChatRoomAdapter(getApplicationContext(), R.layout.custom_row_message_chatroom, listData);
        lvMessage.setAdapter(adapter);

        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat dateFormat = new SimpleDateFormat("HH:mm"); // yyyy/MM/dd HH:mm:ss
                Date date = new Date();
                String time = dateFormat.format(date);

                if(time.equals(oldTime)){
                    mFirebaseAdapter.putMessageChatRoom(mSharedPreferences.getString("user_name", "Not yet"), etMessage.getText().toString(), "" , Integer.parseInt(getIntent().getExtras().getString("room_id")));

                }else{
                    mFirebaseAdapter.putMessageChatRoom(mSharedPreferences.getString("user_name", "Not yet"), etMessage.getText().toString(), time , Integer.parseInt(getIntent().getExtras().getString("room_id")));

                }
                oldTime = time;

                etMessage.setText("");

                // Set button disable
//                ivSend.setEnabled(false);
//                ivSend.setImageResource(R.drawable.ic_send_button_disable);

            }
        });

//        etMessage.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if(etMessage.getText().toString().length() > 0){
//                    ivSend.setEnabled(true);
//                    ivSend.setImageResource(R.drawable.ic_send_button);
//                }else{
//                    ivSend.setEnabled(false);
//                    ivSend.setImageResource(R.drawable.ic_send_button_disable);
//                }
//
//                return false;
//
//            }
//        });
    }

    public void setVies(){
        lvMessage = (ListView) findViewById(R.id.lvMessageChatRoom);
        etMessage = (EditText) findViewById(R.id.chatroom_etMessage);
        ivSend = (ImageView) findViewById(R.id.chatroom_ivSendButton);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
