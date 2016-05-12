package com.example.giang.longschat_firebase.Object;

/**
 * Created by giang on 5/11/2016.
 */
public class ChatRoomObject {
    private String maintitle, subTitle;
    private int iconResource;

    public ChatRoomObject(String maintitle, String subTitle, int iconResource) {
        this.maintitle = maintitle;
        this.subTitle = subTitle;
        this.iconResource = iconResource;
    }

    public String getMaintitle() {
        return maintitle;
    }

    public void setMaintitle(String maintitle) {
        this.maintitle = maintitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }
}
