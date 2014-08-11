package vn.lenam.imagegallery.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import vn.lenam.imagegallery.BuildConfig;

/**
 * Created by Le Nam on 11-Aug-14.
 */
public class DiskLruImageCache implements ImageLoader.ImageCache {
    private static final int APP_VERSION = 1;
    private static final int VALUE_COUNT = 1;
    private static int IO_BUFFER_SIZE = 8 * 1024;
    private final long diskCacheSize = 20 * 1024 * 1024;
    private File diskCacheDir;
    private DiskLruCache mDiskCache;
    private Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.JPEG;
    private int mCompressQuality = 80;

    public DiskLruImageCache(Context context) {
        try {
            diskCacheDir = getDiskCacheDir(context, "my-photo-cacher");
            mDiskCache = DiskLruCache.open(diskCacheDir, APP_VERSION, VALUE_COUNT, diskCacheSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openCache() {
        try {
            mDiskCache = DiskLruCache.open(diskCacheDir, APP_VERSION, VALUE_COUNT, diskCacheSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean writeBitmapToFile(Bitmap bitmap, DiskLruCache.Editor editor)
            throws IOException, FileNotFoundException {
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(editor.newOutputStream(0), IO_BUFFER_SIZE);
            return bitmap.compress(mCompressFormat, mCompressQuality, out);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private File getDiskCacheDir(Context context, String uniqueName) {

        final String cachePath = context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    @Override
    public void putBitmap(String key2, Bitmap data) {
        String key = createKey(key2);
        DiskLruCache.Editor editor = null;
        try {
            editor = mDiskCache.edit(key);
            if (editor == null) {
                return;
            }

            if (writeBitmapToFile(data, editor)) {
                mDiskCache.flush();
                editor.commit();
                if (BuildConfig.DEBUG) {
                    Log.d("cache_test_DISK_", "image put on disk cache " + key);
                }
            } else {
                editor.abort();
                if (BuildConfig.DEBUG) {
                    Log.d("cache_test_DISK_", "ERROR on: image put on disk cache " + key);
                }
            }
        } catch (IOException e) {
            if (BuildConfig.DEBUG) {
                Log.d("cache_test_DISK_", "ERROR on: image put on disk cache " + key);
            }
            try {
                if (editor != null) {
                    editor.abort();
                }
            } catch (IOException ignored) {
            }
        }

    }

    @Override
    public Bitmap getBitmap(String key2) {
        String key = createKey(key2);
        Bitmap bitmap = null;
        DiskLruCache.Snapshot snapshot = null;
        try {

            snapshot = mDiskCache.get(key);
            if (snapshot == null) {
                return null;
            }
            final InputStream in = snapshot.getInputStream(0);
            if (in != null) {
                final BufferedInputStream buffIn =
                        new BufferedInputStream(in, IO_BUFFER_SIZE);
                bitmap = BitmapFactory.decodeStream(buffIn);
            }
        } catch (IOException e) {
            Log.e("ImageCache", "IOException", e);
        } finally {
            if (snapshot != null) {
                snapshot.close();
            }
        }

        if (BuildConfig.DEBUG) {
            Log.d("cache_test_DISK_", bitmap == null ? "" : "image read from disk " + key);
        }

        return bitmap;

    }

    public boolean containsKey(String key2) {
        String key = createKey(key2);
        boolean contained = false;
        DiskLruCache.Snapshot snapshot = null;
        try {
            snapshot = mDiskCache.get(key);
            contained = snapshot != null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (snapshot != null) {
                snapshot.close();
            }
        }

        return contained;

    }

    public void clearCache() {
        if (BuildConfig.DEBUG) {
            Log.d("cache_test_DISK_", "disk cache CLEARED");
        }
        try {
            mDiskCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getCacheFolder() {
        return mDiskCache.getDirectory();
    }


    /**
     * Creates a unique cache key based on a url value
     *
     * @param url url to be used in key creation
     * @return cache key value
     */
    private String createKey(String url) {
        return String.valueOf(url.hashCode());
    }
}
