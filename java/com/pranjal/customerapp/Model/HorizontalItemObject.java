package com.pranjal.customerapp.Model;

/**
 * Created by royalpranjal on 1/28/2017.
 */

public class HorizontalItemObject {
    private String name;
    private String url;
    private int photo;

    public HorizontalItemObject(String name, String url, int photo) {
        this.name = name;
        this.photo = photo;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
