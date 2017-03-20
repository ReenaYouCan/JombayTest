package com.reena.jombaytest.retrofitConfig;

import android.widget.Toast;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reena.jombaytest.utils.UtilConstants;

import io.realm.RealmObject;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RA on 3/18/2017.
 * Configuration file of Retrofit
 */

public class ApiClient {

    private static Retrofit retrofit = null;
    /**
     * Singletone instance of API client
     * @return
     */

    static ApiClient  instance;

    /**
     * Making Class Singleton
     * @return
     */
    public static ApiClient getInstance()
    {
        if(instance==null)
        {
            instance = new ApiClient();
        }
        return instance;
    }

    /**
     * Private Constructor to avoid creating instances of this class
     */
    private  ApiClient()
    {}

    public static Retrofit getClient() {

        Gson gson = new GsonBuilder().setExclusionStrategies(new  ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getDeclaringClass().equals(RealmObject.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();



        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(UtilConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }


        return retrofit;
    }
}
