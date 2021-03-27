package com.example.photogallery.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.photogallery.R;
import com.example.photogallery.adapter.RecyclerViewGalleryAdapter;
import com.example.photogallery.databinding.FragmentGalleryBinding;
import com.example.photogallery.data.model.GalleryItem;
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
    FragmentGalleryBinding mFragmentGalleryBinding;
    RecyclerViewGalleryAdapter mGalleryAdapter;

    PhotoGalleryViewModel mPhotoGalleryViewModel;
    Handler mHandler;
    //endregion

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        mPhotoGalleryViewModel = new ViewModelProvider(requireActivity())
                .get(PhotoGalleryViewModel.class);

        mPhotoGalleryViewModel.fetchItems();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentGalleryBinding = DataBindingUtil.inflate(inflater
                , R.layout.fragment_gallery, container
                , false);

        initial();
        mHandler = new Handler();
        return mFragmentGalleryBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search_gallery_itemm,menu);


        MenuItem menuItemSearch=menu.findItem(R.id.app_bar_search);

        SearchView searchView=(SearchView)menuItemSearch.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPhotoGalleryViewModel.fetchSearchLiveData(query);
                mPhotoGalleryViewModel.saveQueryInPref(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initial() {
        mFragmentGalleryBinding.recyclerViewListGallery.setLayoutManager(
                new GridLayoutManager(getContext(), 3));
        mHandler=new Handler();
        setupAdapter();

        mPhotoGalleryViewModel.getLiveDataSearch().observe(getActivity(), new Observer<List<GalleryItem>>() {
            @Override
            public void onChanged(List<GalleryItem> galleryItems) {
                mGalleryAdapter.setList(galleryItems);
                mGalleryAdapter.notifyDataSetChanged();
            }
        });

        mPhotoGalleryViewModel.getLiveDataPopular().observe(getActivity(), new Observer<List<GalleryItem>>() {
            @Override
            public void onChanged(List<GalleryItem> list) {
                mGalleryAdapter.setList(list);
                mGalleryAdapter.notifyDataSetChanged();
            }
        });
    }

    void setupAdapter(){
        mGalleryAdapter=new RecyclerViewGalleryAdapter(getContext());
        mGalleryAdapter.setHandler(mHandler);
        mFragmentGalleryBinding.recyclerViewListGallery.setAdapter(mGalleryAdapter);
    }
}