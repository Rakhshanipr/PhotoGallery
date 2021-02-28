package com.example.photogallery.services.model.network;

import com.google.gson.annotations.SerializedName;

public class FlickrResponse{

	@SerializedName("stat")
	private String stat;

	@SerializedName("photos")
	private Photos photos;

	public String getStat(){
		return stat;
	}

	public Photos getPhotos(){
		return photos;
	}
}