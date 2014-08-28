package vn.lenam.imagegallery.data;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * Created by namlh on 8/26/14.
 */
class PrefSeviceImpl implements PrefService {

    private static final String PREF_NAME = "fbphotos_pref";
    private Context context;

    @Inject
    public PrefSeviceImpl(Context context) {
        this.context = context;
    }

    @Override
    public void saveString(PrefKey type, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(type.name(), value).apply();
    }

    @Override
    public String getString(PrefKey type) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(type.name(), null);
    }

    @Override
    public void saveInt(PrefKey type, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(type.name(), value).apply();
    }

    @Override
    public int getInt(PrefKey type) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(type.name(), 0);
    }
}
