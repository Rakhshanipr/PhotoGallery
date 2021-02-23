package com.example.photogallery.services.network;

import android.os.Handler;
import android.os.HandlerThread;

import java.util.concurrent.ConcurrentHashMap;

public class ImageDownloader<T> extends HandlerThread {

    //region defind static method and variable
    public static final String TAG = "ImageDownloader";
    public static final int MESSAGE_DOWNLOAD = 0;
    //endregion

    Handler mRequestHandler;
    ConcurrentHashMap<T, String> mTargetUri = new ConcurrentHashMap<>();

    public ImageDownloader() {
        super(TAG);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mRequestHandler = new Handler();
    }

    public void queueImageMessage(T target, String url) {
        if (url != null) {
            mTargetUri.put(target, url);
            mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD, url).sendToTarget();
        }

    }
}
