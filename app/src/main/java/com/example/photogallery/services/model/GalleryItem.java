package com.example.photogallery.services.model;

public class GalleryItem {

    String mTitle;
    String mId;
    String mUrl;

    public GalleryItem() {
    }

    public GalleryItem(String title, String id, String url) {
        mTitle = title;
        mId = id;
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
