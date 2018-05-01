package com.westwoodpu.splash.Fragments;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.annotations.SerializedName;
import com.westwoodpu.splash.Adapters.PhotosAdapter;
import com.westwoodpu.splash.Models.Photo;
import com.westwoodpu.splash.R;
import com.westwoodpu.splash.Webservices.ApiInterface;
import com.westwoodpu.splash.Webservices.ServiceGenerator;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nxa16819 on 4/16/2018.
 */

public class PhotosFragment extends Fragment{
    private final static String TAG = PhotosFragment.class.getSimpleName();

    @BindView(R.id.fragment_photos_recyclerview)
    RecyclerView photosRecyclerView;
    @BindView(R.id.fragment_photos_processbar)
    ProgressBar progressBar;

    private PhotosAdapter photosAdapter;
    private List<Photo> photos = new ArrayList<>();

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        unbinder = ButterKnife.bind(this, view);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        photosRecyclerView.setLayoutManager(lm);

        photosAdapter = new PhotosAdapter(getActivity(), photos);
        photosRecyclerView.setAdapter(photosAdapter);
        showProgressBar(true);
        getPhotos();
        return view;
    }

    private void getPhotos() {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<List<Photo>> call = apiInterface.getPhotos();
        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Loading successfully, size: " + response.body().size());
                    for (Photo photo : response.body()) {
                        photos.add(photo);
                        Log.d(TAG, photo.getUrl().getFull());
                    }

                    photosAdapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "Fail" + response.message());
                }
                showProgressBar(false);
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Log.d(TAG, "Fail: " + t.getMessage());
                showProgressBar(false);
            }
        });
    }

    private void showProgressBar(boolean isShow) {
        if (isShow) {
            progressBar.setVisibility(View.VISIBLE);
            photosRecyclerView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            photosRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
