package com.example.giang.longschat_firebase.Object;

/**
 * Created by giang on 5/12/2016.
 */
public class MessageChatRoomObject {
    private String name, message, time;

    public MessageChatRoomObject(String name, String message, String time) {
        this.name = name;
        this.message = message;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
