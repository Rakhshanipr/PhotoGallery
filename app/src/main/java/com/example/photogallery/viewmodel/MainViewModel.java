package com.example.photogallery.viewmodel;

import com.example.photogallery.repository.GalleryItemRepository;
import com.example.photogallery.services.model.GalleryItem;

import java.util.List;

public class MainViewModel {

    GalleryItemRepository mItemRepository;

    List<GalleryItem> mItemList;
    public MainViewModel() {
        mItemRepository=GalleryItemRepository.getInstance();
    }


    public List<GalleryItem> setListners(){
        mItemRepository.setListners(new GalleryItemRepository.Listners() {
            @Override
            public void onRetrofitResponse(List<GalleryItem> items) {
                mItemList=items;
            }
        });
        return mItemList;
    }
}
