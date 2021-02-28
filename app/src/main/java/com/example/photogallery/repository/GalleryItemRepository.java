package com.example.photogallery.repository;

import com.example.photogallery.services.model.GalleryItem;
import com.example.photogallery.services.model.network.FlickrResponse;
import com.example.photogallery.services.model.network.PhotoItem;
import com.example.photogallery.services.network.FlickrFetcher;
import com.example.photogallery.services.network.IFlickrService;
import com.example.photogallery.services.network.RetrofitInstance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
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
    //endregion

    public GalleryItemRepository(){
        Retrofit retrofit= RetrofitInstance.getInstance();
        mFlickrService=retrofit.create(IFlickrService.class);
    }

    public List<PhotoItem> getItems() {

        Call<FlickrResponse> call=mFlickrService.listItems(RetrofitInstance.QUERY_PARAMETERS);
        try {
            Response<FlickrResponse> response = call.execute();
            return response.body().getPhotos().getPhoto();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*String uri = FlickrFetcher.generateUri();

        FlickrFetcher flickerFetcher = new FlickrFetcher();

        try {
            String jsonBodyString = flickerFetcher.getString(uri);
            JSONObject jsonObject = new JSONObject(jsonBodyString);
            mItems = parseJson(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mItems;*/
        return null;
    }

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

}
