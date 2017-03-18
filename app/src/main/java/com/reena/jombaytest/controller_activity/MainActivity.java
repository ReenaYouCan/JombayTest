package com.reena.jombaytest.controller_activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.reena.jombaytest.R;
import com.reena.jombaytest.models.PhotoGalleryModel;
import com.reena.jombaytest.realmDbConfig.RealmController;
import com.reena.jombaytest.retrofitConfig.ApiClient;
import com.reena.jombaytest.retrofitConfig.ApiInterfaces;
import com.reena.jombaytest.utils.Utilities;
import com.reena.jombaytest.views.PhotosRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private final  static String TAG = "MainActivity";

    private RecyclerView mRvPhotos;
    private RecyclerView.LayoutManager mLayoutManager;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        initViews();
        initRealm();
        getPhotosApiCall();
    }

public void initRealm()
{
    this.realm = RealmController.with(this).getRealm();

}
    public void initViews()
    {
        mRvPhotos = (RecyclerView) findViewById(R.id.rvPhotoGrid);
        mLayoutManager = new GridLayoutManager(this,2);
        mRvPhotos.setLayoutManager(mLayoutManager);
    }

    /**
     * Set Adapter to RecyclerView
     * @param arrPhotos
     */
    public void setAdatpter(ArrayList<PhotoGalleryModel> arrPhotos)
    {
        if(null!=arrPhotos)
        if(arrPhotos.size()>0) {
            mRvPhotos.setAdapter(new PhotosRecyclerAdapter(mContext, arrPhotos));
        }
    }
    /**
     * Make ApiCall
     */
    public void getPhotosApiCall()
    {
        //Callback Interface
        ApiInterfaces apiInterfaces = ApiClient.getClient().create(ApiInterfaces.class);
        //Show ProgressDialog
        Utilities.showProgress(mContext);

        Call<ArrayList<PhotoGalleryModel>> modelCall = apiInterfaces.getPhotos();
        modelCall.enqueue(new Callback<ArrayList<PhotoGalleryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PhotoGalleryModel>> call, Response<ArrayList<PhotoGalleryModel>> response) {
                ArrayList<PhotoGalleryModel> ArrPhotos = response.body();
                setAdatpter(ArrPhotos);
                Utilities.showLog("Respone Ok:"+ArrPhotos.get(0).getTitle(),TAG,2);
                Utilities.dismissProgress();
            }

            @Override
            public void onFailure(Call<ArrayList<PhotoGalleryModel>> call, Throwable t) {
                Utilities.showLog("Respone Failure:"+t.toString(),TAG,1);
            }
        });

    }

}
