package vn.lenam.imagegallery.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by namlh on 8/18/14.
 */
public class NetworkHelper {

    /**
     * check network connected
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (null == ni)
            return false;
        return ni.isConnected();
    }
}
