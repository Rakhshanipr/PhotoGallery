package com.example.photogallery.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import androidx.annotation.NonNull;

import com.example.photogallery.adapter.RecyclerViewGalleryAdapter;
import com.example.photogallery.services.network.FlickrFetcher;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class ImageDownloader<T> extends HandlerThread {

    //region defind static method and variable
    public static final String TAG = "ImageDownloader";
    public static final int MESSAGE_DOWNLOAD = 0;
    //endregion

    Handler mRequestHandler;
    ConcurrentHashMap<T, String> mTargetUri = new ConcurrentHashMap<>();
    CallBacks mCallBacks;

    Handler mHandlerSetPhoto;

    public Handler getHandlerSetPhoto() {
        return mHandlerSetPhoto;
    }

    public void setHandlerSetPhoto(Handler handlerSetPhoto) {
        mHandlerSetPhoto = handlerSetPhoto;
    }

    public CallBacks getCallBacks() {
        return mCallBacks;
    }

    public void setCallBacks(CallBacks callBacks) {
        mCallBacks = callBacks;
    }

    public ImageDownloader() {
        super(TAG);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mRequestHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == MESSAGE_DOWNLOAD) {
                    RecyclerViewGalleryAdapter.ViewHolder viewHolder= (RecyclerViewGalleryAdapter.ViewHolder) msg.obj;

                    try {
                        byte[] photoByteArray=new FlickrFetcher().getBytes(viewHolder.mGalleryItem.getUrl());

                        Bitmap bitmap= BitmapFactory
                                .decodeByteArray(photoByteArray,0,photoByteArray.length);

                        mHandlerSetPhoto.post(new Runnable() {
                            @Override
                            public void run() {
                                if (viewHolder.mGalleryItem.getUrl()!=mTargetUri.get(viewHolder))
                                    return;

                                mCallBacks.bindBitmap(viewHolder,bitmap);

                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    public void queueImageMessage(T target, String url) {
        if (url != null) {
            mTargetUri.put(target, url);
            mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD, target).sendToTarget();
        }

    }

    public interface CallBacks{
        void bindBitmap(RecyclerViewGalleryAdapter.ViewHolder target, Bitmap bitmap);
    }
}
