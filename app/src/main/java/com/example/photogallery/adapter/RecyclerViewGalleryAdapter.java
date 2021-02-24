package com.example.photogallery.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photogallery.R;
import com.example.photogallery.databinding.GalleryItemBinding;
import com.example.photogallery.repository.GalleryItemRepository;
import com.example.photogallery.services.model.GalleryItem;
import com.example.photogallery.services.network.ImageDownloader;
import com.example.photogallery.viewmodel.GalleryItemViewModel;

import java.util.List;

public class RecyclerViewGalleryAdapter extends RecyclerView.Adapter<RecyclerViewGalleryAdapter.ViewHolder> {

    //region defind variable
    List<GalleryItem> mList;
    Context mContext;
    GalleryItemRepository mGalleryItemRepository;

    ImageDownloader<ViewHolder> mImageDownloader;

    android.os.Handler mHandler;
    //endregion


    public android.os.Handler getHandler() {
        return mHandler;
    }

    public void setHandler(Handler handler) {
        mHandler = handler;
    }

    public RecyclerViewGalleryAdapter(Context context, ImageDownloader imageDownloader) {
        mGalleryItemRepository = GalleryItemRepository.getInstance();
        mList = mGalleryItemRepository.getItems();
        mContext = context;

        mImageDownloader=imageDownloader;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        GalleryItemBinding galleryItemBinding = DataBindingUtil.inflate(inflater
                , R.layout.gallery_item
                , parent
                , false);

        return new ViewHolder(galleryItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       public GalleryItem mGalleryItem;
       public GalleryItemBinding mGalleryItemBinding;

        public ViewHolder(GalleryItemBinding galleryItemBinding) {
            super(galleryItemBinding.getRoot());


            mGalleryItemBinding = galleryItemBinding;

            mGalleryItemBinding.setGallertItemViewModel(new GalleryItemViewModel());
        }

        public void bind(GalleryItem galleryItem) {
            mGalleryItem = galleryItem;

            mGalleryItemBinding.imageViewPhoto.setImageResource(R.mipmap.ic_launcher);
            mImageDownloader.queueImageMessage(this,mGalleryItem.getUrl());

            mImageDownloader.setHandlerSetPhoto(mHandler);
            mImageDownloader.setCallBacks(new ImageDownloader.CallBacks() {
                @Override
                public void bindBitmap(ViewHolder viewHolder,Bitmap bitmap) {
                    viewHolder.mGalleryItemBinding.imageViewPhoto.setImageBitmap(bitmap);
                }
            });
        }

    }
}
