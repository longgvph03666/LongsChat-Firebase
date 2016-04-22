package com.example.giang.longschat_firebase.Object;

/**
 * Created by giang on 11/4/2016.
 */
public class MoreObjectItem {
    private String title;
    private int icon;

    public MoreObjectItem(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
