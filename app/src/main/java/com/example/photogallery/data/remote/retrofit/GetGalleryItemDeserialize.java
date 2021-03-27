package com.example.photogallery.data.remote.retrofit;

import com.example.photogallery.data.model.GalleryItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetGalleryItemDeserialize implements JsonDeserializer<List<GalleryItem>> {

    @Override
    public List<GalleryItem> deserialize(JsonElement json
            , Type typeOfT
            , JsonDeserializationContext context) throws JsonParseException {

        JsonObject body=json.getAsJsonObject();


        List<GalleryItem> list = new ArrayList<>();

        JsonObject jsonPhotos = body.getAsJsonObject("photos");
        JsonArray jsonArrayPhoto = jsonPhotos.getAsJsonArray("photo");

        for (int i = 0; i < jsonArrayPhoto.size(); i++) {
            JsonObject jsonObject = jsonArrayPhoto.get(i).getAsJsonObject();

            if (!jsonObject.has("url_s"))
                continue;

            String id = jsonObject.get("id").getAsString();
            String title = jsonObject.get("title").getAsString();
            String url = jsonObject.get("url_s").getAsString();

            GalleryItem item = new GalleryItem(title, id, url);
            list.add(item);
        }
        return list;
    }
}
