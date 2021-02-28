package com.example.photogallery.services.network;

import com.example.photogallery.services.model.GalleryItem;
import com.example.photogallery.services.model.network.FlickrResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface IFlickrService {

    @GET(".")
    Call<FlickrResponse> listItems(@QueryMap Map<String,String> parameters);

}