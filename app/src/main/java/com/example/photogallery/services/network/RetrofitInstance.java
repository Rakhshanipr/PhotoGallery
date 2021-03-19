package com.example.photogallery.services.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    //region defind static method and variable
    public static final String BASE_PATH = "https://www.flickr.com/services/rest/";
    public static final String METHOD_RECENT = "flickr.photos.getRecent";
    public static final String API_KEY = "a4ac80c364bd9e6517ec7d825b48c1f7";
    public static final String METHOD_POPULAR = "flickr.photos.getPopular";
    private static final String USER_ID = "34427466731@N01";

    public static Map<String, String> QUERY_PARAMETERS = new HashMap<String, String>() {{
        put("method", METHOD_POPULAR);
        put("api_key", API_KEY);
        put("format", "json");
        put("extras", "url_s");
        put("user_id", USER_ID);
        put("nojsoncallback", "1");
    }};

    public static Retrofit getInstance(Type type, Object typeAdapter) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_PATH)
                .addConverterFactory(createGsonConverter(type, typeAdapter))
                .build();

        return retrofit;
    }

    public static Converter.Factory createGsonConverter(Type type, Object typeAdapter) {
        GsonBuilder builder = new GsonBuilder()
                .registerTypeAdapter(type, typeAdapter);

        Gson gson = builder.create();
        return GsonConverterFactory.create(gson);
    }

    //endregion
}
