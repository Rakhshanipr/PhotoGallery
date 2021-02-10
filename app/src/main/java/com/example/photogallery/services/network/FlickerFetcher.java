package com.example.photogallery.services.network;

import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PortUnreachableException;
import java.net.URL;

public class FlickerFetcher {

    public static final String BASE_PATH = "https://www.flickr.com/services/rest";
    public static final String METHOD_RECENT = "flickr.photos.getRecent";
    public static final String API_KEY = "a4ac80c364bd9e6517ec7d825b48c1f7";

    public static String generateUri(){
        Uri uri=Uri.parse(BASE_PATH)
                .buildUpon()
                .appendQueryParameter("method", METHOD_RECENT)
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("extras", "url_s")
                .appendQueryParameter("nojsoncallback", "1")
                .build();

        return uri.toString();
    }

    public byte[] getBytes(String urlString) throws IOException {
        URL url=new URL(urlString);
        HttpURLConnection connection= (HttpURLConnection) url.openConnection();

        byte[] resualt;
        try {
            InputStream input=connection.getInputStream();

            if (connection.getResponseCode()!=HttpURLConnection.HTTP_OK)
                throw  new IOException(connection.getResponseMessage()+"v");

            byte[] buffer=new byte[1024];
            ByteArrayOutputStream output=new ByteArrayOutputStream();

            int count=0;

            while ((count=input.read(buffer))!=-1){
                output.write(buffer,0,count);
            }

            resualt = output.toByteArray();
            output.close();
            input.close();
        } finally {
            connection.disconnect();
        }

        return resualt;
    }

    public String getString(String urlString) throws IOException {

        String result= new String(getBytes(urlString));
        return result;
    }

}
