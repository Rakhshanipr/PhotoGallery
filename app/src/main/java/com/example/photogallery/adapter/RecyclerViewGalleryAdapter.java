package com.example.photogallery.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photogallery.R;
import com.example.photogallery.databinding.GalleryItemBinding;
import com.example.photogallery.data.repository.GalleryItemRepository;
import com.example.photogallery.data.model.GalleryItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewGalleryAdapter extends RecyclerView.Adapter<RecyclerViewGalleryAdapter.ViewHolder> {

    //region defind variable
    List<GalleryItem> mList;
    Context mContext;
    GalleryItemRepository mGalleryItemRepository;

    android.os.Handler mHandler;

    //endregion


    public RecyclerViewGalleryAdapter(Context context) {
        mContext = context;
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

        }

        public void bind(GalleryItem galleryItem) {
            mGalleryItem = galleryItem;
            Picasso.get()
                    .load(mGalleryItem.getUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(mGalleryItemBinding.imageViewPhoto);
        }
    }
}