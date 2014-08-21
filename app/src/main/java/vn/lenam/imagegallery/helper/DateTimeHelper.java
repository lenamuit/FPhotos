package vn.lenam.imagegallery.helper;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import vn.lenam.imagegallery.BuildConfig;

/**
 * Created by namlh on 8/21/14.
 */
public class DateTimeHelper {

    public static Date getDateFromISO8601(String input) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        try {
            Date date = format.parse(input);
            return date;
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getStringDate(Context context, Date d) {
        final String format = Settings.System.getString(context.getContentResolver(), Settings.System.DATE_FORMAT);
        DateFormat dateFormat;
        if (TextUtils.isEmpty(format)) {
            dateFormat = android.text.format.DateFormat.getMediumDateFormat(context);
        } else {
            dateFormat = new SimpleDateFormat(format);
        }
        return dateFormat.format(d);
    }
}
