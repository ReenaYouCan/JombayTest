package com.reena.jombaytest.realmDbConfig;

import android.app.Activity;
import android.app.Application;

import com.reena.jombaytest.models.PhotoGalleryModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by RA on 3/18/2017.
 */

public class RealmController {
    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    /**
     *
     * @param activity
     * @return
     */
    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }


    public static RealmController getInstance() {
        return instance;
    }

    /**
     * Get Realm Instance
     * @return
     */
    public Realm getRealm() {
        return realm;
    }

    //Refresh the realm instance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from PhotoGalleryModel.class
    public void clearAll() {

        realm.beginTransaction();
//        realm.clear(PhotoGalleryModel.class);
        realm.commitTransaction();
    }

    public void addAll(ArrayList<PhotoGalleryModel> arrPhotos)
    {
        for(PhotoGalleryModel model : arrPhotos) {
            realm.beginTransaction();
//            realm.copyToRealm(model);
            realm.commitTransaction();
        }
    }
    //find all objects in the Book.class
//    public RealmResults<PhotoGalleryModel> getPhotos() {
//
//        return realm.where(PhotoGalleryModel.class).findAll();
//    }

}
