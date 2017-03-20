package com.reena.jombaytest.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.reena.jombaytest.CallbackInterface;
import com.reena.jombaytest.controller_activity.MainApplication;
import com.reena.jombaytest.models.PhotoGalleryModel;
import com.reena.jombaytest.realmDbConfig.RealmController;
import com.reena.jombaytest.utils.UtilConstants;

import java.util.ArrayList;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class MyIntentService extends IntentService {

    private ArrayList<PhotoGalleryModel> mArrPhotos;

    private ResultReceiver mResultReceiver;

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        RealmController.with(this).getRealm();
        if (intent != null) {
            mArrPhotos = MainApplication.getmArrPhotos();
            mResultReceiver = intent.getParcelableExtra(UtilConstants.RESULT_RECEIVER);
        }
             handleDBOperations();
    }


    /**
     * Handle Database Operations
     */
    public void handleDBOperations() {
//        RealmController.getInstance().clearAll();
        RealmController.getInstance().addAll(mArrPhotos, new CallbackInterface() {
            @Override
            public void addedSuccessFully() {

                if (mResultReceiver != null) {
                    Bundle bundle = new Bundle();
                    mResultReceiver.send(100, bundle);
                }
            }

            @Override
            public void faildToAdd() {

            }
        });

    }


}
