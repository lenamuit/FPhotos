package vn.lenam.imagegallery.data;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * Created by namlh on 8/26/14.
 */
class PrefSeviceImpl implements PrefService {

    private static final String PREF_NAME = "my_photos_on_facebook";
    private Context context;

    @Inject
    public PrefSeviceImpl(Context context) {
        this.context = context;
    }

    @Override
    public void saveString(PrefType type, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(type.name(), value).apply();
    }

    @Override
    public String getString(PrefType type) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(type.name(), null);
    }
}
