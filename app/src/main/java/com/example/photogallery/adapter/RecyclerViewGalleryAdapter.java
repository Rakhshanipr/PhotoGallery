package com.example.photogallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photogallery.R;
import com.example.photogallery.databinding.GalleryItemBinding;
import com.example.photogallery.repository.GalleryItemRepository;
import com.example.photogallery.services.model.GalleryItem;
import com.example.photogallery.viewmodel.GalleryItemViewModel;

import java.util.List;

public class RecyclerViewGalleryAdapter extends RecyclerView.Adapter<RecyclerViewGalleryAdapter.ViewHolder> {

    List<GalleryItem> mList;
    Context mContext;
    GalleryItemRepository mGalleryItemRepository;

    public RecyclerViewGalleryAdapter(Context context) {
        mGalleryItemRepository=GalleryItemRepository.getInstance();
        mList=mGalleryItemRepository.getItems();
        mContext=context;
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

    class ViewHolder extends RecyclerView.ViewHolder {

        GalleryItem mGalleryItem;
        GalleryItemBinding mGalleryItemBinding;

        public ViewHolder(GalleryItemBinding galleryItemBinding) {
            super(galleryItemBinding.getRoot());



            mGalleryItemBinding=galleryItemBinding;

            mGalleryItemBinding.setGallertItemViewModel(new GalleryItemViewModel());
        }

        public void bind(GalleryItem galleryItem) {
            mGalleryItem = galleryItem;
            mGalleryItemBinding.getGallertItemViewModel().setGalleryItem(galleryItem);
        }

    }
}
