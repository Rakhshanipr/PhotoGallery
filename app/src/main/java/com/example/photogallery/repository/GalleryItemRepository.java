package com.example.photogallery.repository;

import com.example.photogallery.services.model.GalleryItem;
import com.example.photogallery.services.network.FlickerFetcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    //endregion

    public List<GalleryItem> getItems() {
        String uri = FlickerFetcher.generateUri();

        FlickerFetcher flickerFetcher = new FlickerFetcher();

        try {
            String jsonBodyString = flickerFetcher.getString(uri);
            JSONObject jsonObject = new JSONObject(jsonBodyString);
            mItems = parseJson(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mItems;
    }

    private List<GalleryItem> parseJson(JSONObject jsonBodyObject) throws JSONException {

        List<GalleryItem> list = new ArrayList<>();

        JSONObject jsonPhotos = jsonBodyObject.getJSONObject("photos");
        JSONArray jsonArrayPhoto = jsonPhotos.getJSONArray("photo");

        for (int i = 0; i < jsonArrayPhoto.length(); i++) {
            JSONObject jsonObject = jsonArrayPhoto.getJSONObject(i);

            if (!jsonObject.has("url_s"))
                continue;

            String id = jsonObject.getString("id");
            String title = jsonObject.getString("title");
            String url = jsonObject.getString("url_s");

            GalleryItem item = new GalleryItem(title, id, url);
            list.add(item);
        }
        int i = 0;

        return list;
    }

}
