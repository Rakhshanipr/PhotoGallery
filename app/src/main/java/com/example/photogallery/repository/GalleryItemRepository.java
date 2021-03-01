package com.example.photogallery.repository;

import com.example.photogallery.services.GetGalleryItemDeserialize;
import com.example.photogallery.services.model.GalleryItem;
import com.example.photogallery.services.model.network.FlickrResponse;
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

    Listners mListners;

    //endregion


    public Listners getListners() {
        return mListners;
    }

    public void setListners(Listners listners) {
        mListners = listners;
    }

    public GalleryItemRepository(){

        Type type=new TypeToken<List<GalleryItem>>(){}.getType();
        Object typeAdapter=new GetGalleryItemDeserialize();
        Retrofit retrofit= RetrofitInstance.getInstance(type,typeAdapter);
        mFlickrService=retrofit.create(IFlickrService.class);
    }

    public void getItemsAsync(){
        Call<List<GalleryItem>> call=mFlickrService.listItems(RetrofitInstance.QUERY_PARAMETERS);

        call.enqueue(new Callback<List<GalleryItem>>() {
            @Override
            public void onResponse(Call<List<GalleryItem>> call, Response<List<GalleryItem>> response) {
                mListners.onRetrofitResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<GalleryItem>> call, Throwable t) {

            }
        });
    }


  /*  public List<GalleryItem> getItems() {

        Call<List<GalleryItem>> call=mFlickrService.listItems(RetrofitInstance.QUERY_PARAMETERS);
        try {
            Response<List<GalleryItem>> response = call.execute();
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        *//*String uri = FlickrFetcher.generateUri();

        FlickrFetcher flickerFetcher = new FlickrFetcher();

        try {
            String jsonBodyString = flickerFetcher.getString(uri);
            JSONObject jsonObject = new JSONObject(jsonBodyString);
            mItems = parseJson(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mItems;*//*
        return null;
    }*/

//    private List<GalleryItem> parseJson(JSONObject jsonBodyObject) throws JSONException {
//
//        List<GalleryItem> list = new ArrayList<>();
//
//        JSONObject jsonPhotos = jsonBodyObject.getJSONObject("photos");
//        JSONArray jsonArrayPhoto = jsonPhotos.getJSONArray("photo");
//
//        for (int i = 0; i < jsonArrayPhoto.length(); i++) {
//            JSONObject jsonObject = jsonArrayPhoto.getJSONObject(i);
//
//            if (!jsonObject.has("url_s"))
//                continue;
//
//            String id = jsonObject.getString("id");
//            String title = jsonObject.getString("title");
//            String url = jsonObject.getString("url_s");
//
//            GalleryItem item = new GalleryItem(title, id, url);
//            list.add(item);
//        }
//        int i = 0;
//
//        return list;
//    }

    public interface Listners{
        void onRetrofitResponse(List<GalleryItem> items);
    }

}
