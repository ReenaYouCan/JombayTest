package com.reena.jombaytest.controller_activity;

import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.reena.jombaytest.R;
import com.reena.jombaytest.models.PhotoGalleryModel;
import com.reena.jombaytest.realmDbConfig.RealmController;
import com.reena.jombaytest.retrofitConfig.ApiClient;
import com.reena.jombaytest.retrofitConfig.ApiInterfaces;
import com.reena.jombaytest.service.MyIntentService;
import com.reena.jombaytest.utils.UtilConstants;
import com.reena.jombaytest.utils.Utilities;
import com.reena.jombaytest.views.PhotosRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    private final static String TAG = "MainActivity";

    //Views
    private RecyclerView mRvPhotos;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mTvEmptyView;

    //Onjects
    private MyResultReceiver mResultReceiver;

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        mResultReceiver = new MyResultReceiver();
        initViews();

        loadData();
    }

    public void loadData()
    {
        //Check if internet is available
        if (Utilities.isNetworkAvailable(mContext)) {
            getPhotosApiCall();
        } else {
            Utilities.showProgress(mContext);
            getAllLocalData();
        }
    }

    /**
     * InitRealm
     */
    public void initRealm() {
        mRealm = RealmController.with(this).getRealm();
    }

    public void getAllLocalData() {
        initRealm();
        MainApplication.setmArrPhotos(new ArrayList(RealmController.getInstance().getPhotos()));
        Bundle bundle = new Bundle();
        if(MainApplication.getmArrPhotos().size()>0) {
            mResultReceiver.send(UtilConstants.RESULT_SUCCESS, bundle);
        }
        else {
            mResultReceiver.send(UtilConstants.RESULT_FAILURE, bundle);
        }
    }

    /**
     * Initilize All Views
     */
    public void initViews() {
        mRvPhotos = (RecyclerView) findViewById(R.id.rvPhotoGrid);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRvPhotos.setLayoutManager(mLayoutManager);
        mTvEmptyView = (TextView) findViewById(R.id.tvEmptyView);
    }

    /**
     * Set Adapter to RecyclerView
     *
     * @param arrPhotos
     */
    public void setAdatpter(ArrayList<PhotoGalleryModel> arrPhotos) {
        if (null != arrPhotos)
            if (arrPhotos.size() > 0) {
                mRvPhotos.setAdapter(new PhotosRecyclerAdapter(mContext, arrPhotos));
            }

        Utilities.dismissProgress();
    }

    /**
     * Make ApiCall
     */
    public void getPhotosApiCall() {
        //Callback Interface
        ApiInterfaces apiInterfaces = ApiClient.getClient().create(ApiInterfaces.class);
        //Show ProgressDialog
        Utilities.showProgress(mContext);

        Call<ArrayList<PhotoGalleryModel>> modelCall = apiInterfaces.getPhotos();
        modelCall.enqueue(new Callback<ArrayList<PhotoGalleryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PhotoGalleryModel>> call, Response<ArrayList<PhotoGalleryModel>> response) {
                MainApplication.setmArrPhotos(response.body());
                startResultService(false);
                Utilities.showLog("Respone Ok:" + MainApplication.getmArrPhotos().get(0).getTitle(), TAG, 2);
            }

            @Override
            public void onFailure(Call<ArrayList<PhotoGalleryModel>> call, Throwable t) {
                Utilities.showLog("Respone Failure:" + t.toString(), TAG, 1);
                Utilities.showToast("Error in loading data", mContext);
            }
        });

    }

    // start Intent Service

    public void startResultService(boolean fetchLocalCopy) {
        Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra(UtilConstants.RESULT_RECEIVER, mResultReceiver);
        intent.putExtra(UtilConstants.IS_LOCAL_DATA, fetchLocalCopy);
        startService(intent);
    }



    // Thread to update main UI
    class UpdateUI implements Runnable {
        ArrayList<PhotoGalleryModel> arrPhotos;

        public UpdateUI(ArrayList<PhotoGalleryModel> arrPhotos) {
            this.arrPhotos = arrPhotos;
        }

        public void run() {
            if(arrPhotos!=null) {
                setAdatpter(arrPhotos);
                mTvEmptyView.setVisibility(View.GONE);
                mRvPhotos.setVisibility(View.VISIBLE);
            } else {
                //Error Message
                mTvEmptyView.setVisibility(View.VISIBLE);
                mRvPhotos.setVisibility(View.GONE);

            }
        }
    }

    // Result Receiver to get update from Service
    private class MyResultReceiver extends ResultReceiver {
        MyResultReceiver() {
            super(null);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == 100) {
                ArrayList<PhotoGalleryModel> arrayList = MainApplication.getmArrPhotos();
                runOnUiThread(new UpdateUI(arrayList));
            } else {
                Utilities.showToast("Error in loading data", mContext);
                runOnUiThread(new UpdateUI(null));
            }
        }
    }
}
