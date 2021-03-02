package com.example.photogallery.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.photogallery.services.GetGalleryItemDeserialize;
import com.example.photogallery.services.model.GalleryItem;
import com.example.photogallery.services.network.FlickrFetcher;
import com.example.photogallery.services.network.IFlickrService;
import com.example.photogallery.services.network.RetrofitInstance;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
    MutableLiveData<List<GalleryItem>> mLiveData=new MutableLiveData<>();
    //endregion


    public GalleryItemRepository(){

        Type type=new TypeToken<List<GalleryItem>>(){}.getType();
        Object typeAdapter=new GetGalleryItemDeserialize();
        Retrofit retrofit= RetrofitInstance.getInstance(type,typeAdapter);
        mFlickrService=retrofit.create(IFlickrService.class);
    }

    public MutableLiveData<List<GalleryItem>> getGalleryItemListLiveData(){
        Call<List<GalleryItem>> call=mFlickrService.listItems(RetrofitInstance.QUERY_PARAMETERS);

        call.enqueue(new Callback<List<GalleryItem>>() {
            @Override
            public void onResponse(Call<List<GalleryItem>> call, Response<List<GalleryItem>> response) {
                mLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<GalleryItem>> call, Throwable t) {

            }
        });

        return mLiveData;
    }
}