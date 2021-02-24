package com.example.photogallery.viewmodel;

import com.example.photogallery.services.model.GalleryItem;
import com.example.photogallery.services.network.FlickerFetcher;

import java.io.IOException;

public class GalleryItemViewModel {

    //region defind variable
    GalleryItem mGalleryItem;
    //endregion

    public GalleryItemViewModel() {
    }

    public GalleryItemViewModel(GalleryItem galleryItem) {
        mGalleryItem = galleryItem;
    }

    public void setGalleryItem(GalleryItem galleryItem) {
        mGalleryItem = galleryItem;
    }

    public static String retResult(String urlString) throws IOException {
        FlickerFetcher flickerFetcher = new FlickerFetcher();
        String result = flickerFetcher.getString(urlString);
        return result;
    }



    public String getTitle() {
        return mGalleryItem.getTitle();
    }

    public String getUrl() {
        return mGalleryItem.getUrl();
    }
}