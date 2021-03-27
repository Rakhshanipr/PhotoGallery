package com.example.photogallery.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.photogallery.data.remote.retrofit.GetGalleryItemDeserialize;
import com.example.photogallery.data.model.GalleryItem;
import com.example.photogallery.data.remote.retrofit.IFlickrService;
import com.example.photogallery.data.remote.retrofit.RetrofitInstance;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GalleryItemRepository {

    //region defind static method and variable
    private static GalleryItemRepository sInstance;

    public static GalleryItemRepository getInstance() {
        if (sInstance == null)
            sInstance = new GalleryItemRepository();

        return sInstance;
    }
    //endregion

    //region deinf variable
    private List<GalleryItem> mItems = new ArrayList<>();

    private IFlickrService mFlickrService;

    MutableLiveData<List<GalleryItem>> mLiveDataPopular =new MutableLiveData<>();

    MutableLiveData<List<GalleryItem>> mLiveDataSearch=new MutableLiveData<>();
    //endregion



    public GalleryItemRepository(){
        Type type=new TypeToken<List<GalleryItem>>(){}.getType();
        Object typeAdapter=new GetGalleryItemDeserialize();
        Retrofit retrofit= RetrofitInstance.getInstance(type,typeAdapter);
        mFlickrService=retrofit.create(IFlickrService.class);
    }

    public MutableLiveData<List<GalleryItem>> getLiveDataPopular() {
        return mLiveDataPopular;
    }

    public MutableLiveData<List<GalleryItem>> getLiveDataSearch() {
        return mLiveDataSearch;
    }

    public void fetchPopularGalleryItemListLiveData(){
        Call<List<GalleryItem>> call=
                mFlickrService.listItems(RetrofitInstance.getPopular_query());

        call.enqueue(new Callback<List<GalleryItem>>() {
            @Override
            public void onResponse(Call<List<GalleryItem>> call, Response<List<GalleryItem>> response) {
                mLiveDataPopular.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<GalleryItem>> call, Throwable t) {

            }
        });
    }

    public void fetchSearchGalleryItemListLiveData(String query){
        Call<List<GalleryItem>> call=
                mFlickrService.listItems(RetrofitInstance.getSerach_query(query));

        call.enqueue(new Callback<List<GalleryItem>>() {
            @Override
            public void onResponse(Call<List<GalleryItem>> call, Response<List<GalleryItem>> response) {
                mLiveDataSearch.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<GalleryItem>> call, Throwable t) {

            }
        });
    }
}