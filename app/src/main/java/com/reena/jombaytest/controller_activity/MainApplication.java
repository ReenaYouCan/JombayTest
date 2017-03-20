package com.reena.jombaytest.controller_activity;

import android.app.Application;

import com.reena.jombaytest.models.PhotoGalleryModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by RA on 3/18/2017.
 */

public class MainApplication extends Application {

    public static ArrayList<PhotoGalleryModel> mArrPhotos = new ArrayList();

    public static ArrayList<PhotoGalleryModel> getmArrPhotos() {
        return mArrPhotos;
    }

    public static void setmArrPhotos(ArrayList<PhotoGalleryModel> arrPhotos) {
        if(mArrPhotos.size()>0)
        {
            mArrPhotos.clear();
        }
        mArrPhotos = arrPhotos;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        // Configure Realm
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

}
