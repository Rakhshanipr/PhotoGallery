package com.example.photogallery.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.photogallery.R;
import com.example.photogallery.adapter.RecyclerViewGalleryAdapter;
import com.example.photogallery.databinding.FragmentGalleryBinding;
import com.example.photogallery.services.ImageDownloader;
import com.example.photogallery.viewmodel.GalleryItemViewModel;
import com.example.photogallery.viewmodel.MainViewModel;

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

    MainViewModel mMainViewModel;
    Handler mHandler;


    ImageDownloader<RecyclerViewGalleryAdapter.ViewHolder> mImageDownloader;
    //endregion

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageDownloader = new ImageDownloader();
        mMainViewModel = new MainViewModel();
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
        mGalleryItemViewModel = new GalleryItemViewModel();


        mHandler=new Handler();
        mGalleryAdapter=new RecyclerViewGalleryAdapter(getContext(),mImageDownloader);
        mGalleryAdapter.setHandler(mHandler);
        mFragmentGalleryBindingm.recyclerViewListGallery.setAdapter(mGalleryAdapter);

//
//        FetchItem fetchItem = new FetchItem();
//        fetchItem.execute();


    }

    /*public class FetchItem extends AsyncTask<Void, Void, RecyclerViewGalleryAdapter> {

        @Override
        protected RecyclerViewGalleryAdapter doInBackground(Void... voids) {

            String result = "";

            mGalleryAdapter = new RecyclerViewGalleryAdapter(getContext(), mImageDownloader);
            mGalleryAdapter.setHandler(mHandler);

            Log.e("FF", result);
            return mGalleryAdapter;
        }

        @Override
        protected void onPostExecute(RecyclerViewGalleryAdapter s) {
            super.onPostExecute(s);
            mFragmentGalleryBindingm.recyclerViewListGallery.setAdapter(s);
        }
    }*/
}