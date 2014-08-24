package vn.lenam.imagegallery.helper;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Le Nam on 18-Aug-14.
 */
public class ToastUtils {

    public static void showToast(Context context, int id) {
        Toast.makeText(context, id, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
