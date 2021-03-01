package com.example.photogallery.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photogallery.R;
import com.example.photogallery.databinding.GalleryItemBinding;
import com.example.photogallery.repository.GalleryItemRepository;
import com.example.photogallery.services.ImageDownloader;
import com.example.photogallery.services.model.GalleryItem;
import com.example.photogallery.viewmodel.GalleryItemViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewGalleryAdapter extends RecyclerView.Adapter<RecyclerViewGalleryAdapter.ViewHolder> {

    //region defind variable
    List<GalleryItem> mList;
    Context mContext;
    GalleryItemRepository mGalleryItemRepository;

    ImageDownloader<ViewHolder> mImageDownloader;

    android.os.Handler mHandler;

    //endregion


    public RecyclerViewGalleryAdapter(Context context, ImageDownloader imageDownloader) {

        mGalleryItemRepository = GalleryItemRepository.getInstance();
        mGalleryItemRepository.setListners(new GalleryItemRepository.Listners() {
            @Override
            public void onRetrofitResponse(List<GalleryItem> items) {
                mList=items;
                RecyclerViewGalleryAdapter.this.notifyDataSetChanged();
            }
        });
        mGalleryItemRepository.getItemsAsync();
        mContext = context;

        mImageDownloader=imageDownloader;
    }

    public List<GalleryItem> getList() {
        return mList;
    }

    public void setList(List<GalleryItem> list) {
        mList = list;
    }

    public android.os.Handler getHandler() {
        return mHandler;
    }

    public void setHandler(Handler handler) {
        mHandler = handler;
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
        if (mList==null){
            return 0;
        }
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

            mImageDownloader.setHandlerSetPhoto(mHandler);
            mGalleryItem = galleryItem;

//            Picasso.get().load(mGalleryItem.getUrlS()).into(mGalleryItemBinding.imageViewPhoto);
            mGalleryItemBinding.imageViewPhoto.setImageResource(R.mipmap.ic_launcher);
            mImageDownloader.queueImageMessage(this, mGalleryItem.getUrl());
            mImageDownloader.setCallBacks(new ImageDownloader.CallBacks() {
                @Override
                public void bindBitmap(ViewHolder viewHolder,Bitmap bitmap) {
                    viewHolder.mGalleryItemBinding.imageViewPhoto.setImageBitmap(bitmap);
                }
            });
        }
    }
}
