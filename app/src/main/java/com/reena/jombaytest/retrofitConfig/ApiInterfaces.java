package com.reena.jombaytest.retrofitConfig;

import com.reena.jombaytest.models.PhotoGalleryModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by RA on 3/18/2017.
 * Interface to interact with client,views and api
 */

public interface ApiInterfaces {

    @GET("photos.json")
    Call<ArrayList<PhotoGalleryModel>> getPhotos();

}
