package com.example.photogallery.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.photogallery.repository.GalleryItemRepository;
import com.example.photogallery.services.model.GalleryItem;
import com.example.photogallery.services.network.FlickrFetcher;

import java.io.IOException;
import java.util.List;

public class GalleryItemViewModel extends ViewModel {

    //region defind variable
    GalleryItem mGalleryItem;

    GalleryItemRepository mItemRepository;
    //endregion

    public GalleryItemViewModel() {
        mItemRepository=GalleryItemRepository.getInstance();
    }



    public GalleryItemViewModel(GalleryItem galleryItem) {
        mGalleryItem = galleryItem;
    }

    public void setGalleryItem(GalleryItem galleryItem) {
        mGalleryItem = galleryItem;
    }

    public static String retResult(String urlString) throws IOException {
        FlickrFetcher flickerFetcher = new FlickrFetcher();
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