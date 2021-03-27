package com.example.photogallery.data.model;

public class GalleryItem {

    //region defind parameter
    String mTitle;
    String mId;
    String mUrl;
    //endregion

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