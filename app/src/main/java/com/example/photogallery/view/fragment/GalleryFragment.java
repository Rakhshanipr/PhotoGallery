package com.example.photogallery.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.photogallery.R;
import com.example.photogallery.adapter.RecyclerViewGalleryAdapter;
import com.example.photogallery.databinding.FragmentGalleryBinding;
import com.example.photogallery.services.ImageDownloader;
import com.example.photogallery.services.model.GalleryItem;
import com.example.photogallery.viewmodel.GalleryItemViewModel;
import com.example.photogallery.viewmodel.PhotoGalleryViewModel;

import java.util.List;

public class GalleryFragment extends Fragment {

    //region deinf static method and variable
    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    //endregion

    //region defind variable
    FragmentGalleryBinding mFragmentGalleryBindingm;
    GalleryItemViewModel mGalleryItemViewModel;
    RecyclerViewGalleryAdapter mGalleryAdapter;

    PhotoGalleryViewModel mPhotoGalleryViewModel;
    Handler mHandler;


    ImageDownloader<RecyclerViewGalleryAdapter.ViewHolder> mImageDownloader;
    //endregion

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageDownloader = new ImageDownloader();
        mPhotoGalleryViewModel = new ViewModelProvider(requireActivity()).get(PhotoGalleryViewModel.class);
        mImageDownloader.start();
        mImageDownloader.getLooper();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentGalleryBindingm = DataBindingUtil.inflate(inflater
                , R.layout.fragment_gallery, container
                , false);

        initial();
        mHandler = new Handler();
        return mFragmentGalleryBindingm.getRoot();
    }

    private void initial() {
        mFragmentGalleryBindingm.recyclerViewListGallery.setLayoutManager(
                new GridLayoutManager(getContext(), 3));
        mGalleryItemViewModel =new ViewModelProvider(requireActivity())
                .get(GalleryItemViewModel.class);
        mHandler=new Handler();
        setupAdapter();

        mPhotoGalleryViewModel.getLiveData().observe(getActivity(), new Observer<List<GalleryItem>>() {
            @Override
            public void onChanged(List<GalleryItem> list) {
                mGalleryAdapter.setList(list);
                mGalleryAdapter.notifyDataSetChanged();
            }
        });
    }

    void setupAdapter(){
        mGalleryAdapter=new RecyclerViewGalleryAdapter(getContext(),mImageDownloader);
        mGalleryAdapter.setHandler(mHandler);
        mFragmentGalleryBindingm.recyclerViewListGallery.setAdapter(mGalleryAdapter);

    }

}