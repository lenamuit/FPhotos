package vn.lenam.imagegallery.api;

import android.app.Application;
import android.content.Context;
import android.net.http.AndroidHttpClient;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import vn.lenam.imagegallery.MPOFApp;
import vn.lenam.imagegallery.api.model.GraphAlbum;
import vn.lenam.imagegallery.api.model.GraphPhotoInfo;

/**
 * Created by namlh on 8/6/14.
 */
@Module(
        injects = {RequestAlbumInList.class,
                RequestPhotoInList.class,
                RequestImageFile.class},
        complete = false,
        library = true
)
public class ApiModule {

    @Provides
    @Singleton
    public RequestApi<List<GraphPhotoInfo>> provideRequestPhoto(Context context) {
        RequestPhotoInList request = new RequestPhotoInList();
        MPOFApp.get(context).inject(request);
        return request;
    }

    @Provides
    @Singleton
    public RequestApi<List<GraphAlbum>> provideRequestAlbums(Context context) {
        RequestAlbumInList requestAlbumInList = new RequestAlbumInList();
        MPOFApp.get(context).inject(requestAlbumInList);
        return requestAlbumInList;
    }

    @Provides
    @Singleton
    public RequestApi<String> provideRequestImageFile(Context context) {
        RequestImageFile requestImageFile = new RequestImageFile();
        MPOFApp.get(context).inject(requestImageFile);
        return requestImageFile;
    }


    @Provides
    @Singleton
    public RequestQueue provideRequestQueue(Application app) {
        HttpClientStack stack = new HttpClientStack(AndroidHttpClient.newInstance("photos-facebook"));
        return Volley.newRequestQueue(app, stack);
    }

    @Provides
    @Singleton
    public ImageLoader provideImageLoader(Application app, RequestQueue requestQueue, ImageLoader.ImageCache imageCache) {
        return new ImageLoader(requestQueue, imageCache);
    }
}
