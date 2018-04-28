package com.westwoodpu.splash.Webservices;

import com.westwoodpu.splash.Models.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by nxa16819 on 4/17/2018.
 */

public interface ApiInterface {
    @GET("photos")
    Call<List<Photo>> getPhotos();
}
