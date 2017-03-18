package com.reena.jombaytest.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by RA on 3/18/2017.
 */

public class Utilities {

    private static ProgressDialog mProgressDialog;

    /**
     * Reference of activity instance
     *
     * @param context
     */
    public static void showProgress(Context context) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
    }

    /**
     * Dissmiss ProgressDialog
     */
    public static void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * Show Toast
     * @param message
     * @param context
     */
    public static void showToast(String message,Context context)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    public static void showLog(String message,String tag,int type)
    {
        switch (type)
        {
            case 1: // Display Error Log
                Log.e(tag,message);
                break;
            case 2: // Display Debug Log
                Log.d(tag,message);
                break;
            case 3: // Display Info Log
                Log.i(tag,message);
                break;

        }

    }
}
