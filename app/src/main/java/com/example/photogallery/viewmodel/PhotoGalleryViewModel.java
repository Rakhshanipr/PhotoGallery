package com.example.photogallery.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.photogallery.data.repository.GalleryItemRepository;
import com.example.photogallery.data.model.GalleryItem;
import com.example.photogallery.utilities.QueryPreferences;

import java.util.List;

public class PhotoGalleryViewModel extends AndroidViewModel {

    GalleryItemRepository mItemRepository;

    LiveData<List<GalleryItem>> mLiveData;

    LiveData<List<GalleryItem>> mLiveDataSearch;

    public PhotoGalleryViewModel(@NonNull Application application) {
        super(application);

        mItemRepository=GalleryItemRepository.getInstance();
        mLiveData=mItemRepository.getLiveDataPopular();
        mLiveDataSearch=mItemRepository.getLiveDataSearch();
    }


    public void fetchSearchLiveData(String query){
        mItemRepository.fetchSearchGalleryItemListLiveData(query);
    }

    public LiveData<List<GalleryItem>> getLiveDataPopular(){
        return mLiveData;
    }

    public LiveData<List<GalleryItem>> getLiveDataSearch(){
        return mLiveDataSearch;
    }

    public void saveQueryInPref(String query){
        QueryPreferences.setSearchQuery(getApplication(),query);
    }

    public void fetchItems(){
        String query=QueryPreferences.getSearchQuery(getApplication());
        if (query==null)
            mItemRepository.fetchPopularGalleryItemListLiveData();
        else
            mItemRepository.fetchSearchGalleryItemListLiveData(query);
    }

}
