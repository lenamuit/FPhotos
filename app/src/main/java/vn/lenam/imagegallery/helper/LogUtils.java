package vn.lenam.imagegallery.helper;

import android.util.Log;

import vn.lenam.imagegallery.BuildConfig;

/**
 * Created by Le Nam on 17-Aug-14.
 */
public class LogUtils {
    static final String TAG = "NamLH";

    public static void e(String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static void e(String msg, Exception e) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, msg, e);
        }
    }

    public static void w(String msg) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, msg);
        }
    }


}
