package vn.lenam.imagegallery.data;

import android.content.Context;

import com.jakewharton.disklrucache.DiskLruCache;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import vn.lenam.imagegallery.BuildConfig;
import vn.lenam.imagegallery.helper.LogUtils;

/**
 * Created by Le Nam on 18-Aug-14.
 */
@Singleton
public class JsonCache {
    private static final int APP_VERSION = 1;
    private static final int VALUE_COUNT = 1;
    private final long diskCacheSize = 20 * 1024 * 1024;

    private DiskLruCache cache;

    @Inject
    public JsonCache(Context context) {
        try {
            final String cachePath = context.getCacheDir().getPath();
            File fileStore = new File(cachePath + File.separator + "json-cache");
            cache = DiskLruCache.open(fileStore, APP_VERSION, VALUE_COUNT, diskCacheSize);
        } catch (IOException e) {
            LogUtils.e("error", e);
        }
    }

    public void save(String key, JSONObject json) {
        save(key, json, 0);
    }

    public void save(String key, JSONObject json, int index) {
        DiskLruCache.Editor editor = null;
        try {
            editor = cache.edit(key + "_" + index);
            if (editor == null) {
                return;
            }
            String s = json.toString();
            editor.set(0, s);
            LogUtils.w("cache index: " + index);
            LogUtils.w("cache: " + s);
            editor.commit();
        } catch (IOException e) {
            if (BuildConfig.DEBUG) {
                LogUtils.d("cache_test_DISK_", "ERROR on: image put on disk cache " + key);
            }
            try {
                if (editor != null) {
                    editor.abort();
                }
            } catch (IOException ignored) {
            }
        }
    }

    public JSONObject get(String key) {
        return get(key, 0);
    }

    public JSONObject get(String key, int index) {
        try {
            LogUtils.w("load index: " + index);
            String s = cache.get(key + "_" + index).getString(0);
            JSONObject json = new JSONObject(s);
            LogUtils.w("load cache: " + json.toString());
            return json;
        } catch (Exception e) {
            LogUtils.e("error", e);
        }
        return null;
    }
}
