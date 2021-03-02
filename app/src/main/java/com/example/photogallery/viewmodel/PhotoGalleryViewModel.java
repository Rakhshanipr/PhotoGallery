package com.example.photogallery.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.photogallery.repository.GalleryItemRepository;
import com.example.photogallery.services.model.GalleryItem;

import java.util.List;

public class PhotoGalleryViewModel extends ViewModel {

    GalleryItemRepository mItemRepository;

    List<GalleryItem> mItemList;

    LiveData<List<GalleryItem>> mLiveData;

    public PhotoGalleryViewModel() {
        mItemRepository=GalleryItemRepository.getInstance();
        mLiveData=mItemRepository.getGalleryItemListLiveData();
    }

    public LiveData<List<GalleryItem>> getLiveData(){
        return mLiveData;
    }
}
