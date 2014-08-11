package vn.lenam.imagegallery.api;

import android.app.Application;
import android.net.http.AndroidHttpClient;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import vn.lenam.imagegallery.api.model.GraphAlbum;
import vn.lenam.imagegallery.api.model.GraphPhotoInfo;
import vn.lenam.imagegallery.helper.DiskLruImageCache;

/**
 * Created by namlh on 8/6/14.
 */
@Module(
        complete = false,
        library = true
)
public class ApiModule {

    @Provides
    @Singleton
    public RequestApi<GraphPhotoInfo> provideRequestPhoto() {
        return RequestPhotoInList.getInstance();
    }

    @Provides
    @Singleton
    public RequestApi<GraphAlbum> provideRequestAlbums() {
        return RequestAlbumInList.getInstance();
    }

    @Provides
    @Singleton
    public RequestQueue provideRequestQueue(Application app) {
        HttpClientStack stack = new HttpClientStack(AndroidHttpClient.newInstance("photos-facebook"));
        return Volley.newRequestQueue(app, stack);
    }

    @Provides
    @Singleton
    public ImageLoader provideImageLoader(Application app, RequestQueue requestQueue) {
        DiskLruImageCache mImageCache = new DiskLruImageCache(app);
        return new ImageLoader(requestQueue, mImageCache);
    }
}
