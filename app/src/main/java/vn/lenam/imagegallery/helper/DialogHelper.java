package vn.lenam.imagegallery.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Le Nam on 24-Aug-14.
 */
public class DialogHelper {

    public static void showConfirmDilog(Context context, int mesId, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(mesId);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("OK", listener);
        builder.show();
    }
}
