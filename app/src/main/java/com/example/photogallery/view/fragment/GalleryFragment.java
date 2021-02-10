package com.example.photogallery.view.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.photogallery.R;
import com.example.photogallery.adapter.RecyclerViewGalleryAdapter;
import com.example.photogallery.databinding.FragmentGalleryBinding;
import com.example.photogallery.viewmodel.GalleryItemViewModel;

import java.io.IOException;

public class GalleryFragment extends Fragment {

    FragmentGalleryBinding mFragmentGalleryBindingm;
    GalleryItemViewModel mGalleryItemViewModel;
    RecyclerViewGalleryAdapter mGalleryAdapter;

    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentGalleryBindingm = DataBindingUtil.inflate(inflater
                , R.layout.fragment_gallery, container
                , false);

        initial();

        return mFragmentGalleryBindingm.getRoot();
    }

    private void initial() {
        mFragmentGalleryBindingm.recyclerViewListGallery.setLayoutManager(
                new LinearLayoutManager(getContext()));


        mGalleryItemViewModel = new GalleryItemViewModel();

        FetchItem fetchItem=new FetchItem();
        fetchItem.execute();

    }

    public class FetchItem extends AsyncTask<Void, Void, RecyclerViewGalleryAdapter> {

        @Override
        protected RecyclerViewGalleryAdapter doInBackground(Void... voids) {

            String result = "";

           mGalleryAdapter=new RecyclerViewGalleryAdapter(getContext());
            Log.e("FF", result);
            return mGalleryAdapter;
        }

        @Override
        protected void onPostExecute(RecyclerViewGalleryAdapter s) {
            super.onPostExecute(s);
            mFragmentGalleryBindingm.recyclerViewListGallery.setAdapter(s);
        }
    }
}