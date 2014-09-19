package vn.lenam.imagegallery.data.photos;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import vn.lenam.imagegallery.MPOFApp;

/**
 * Created by namlh on 9/19/14.
 */
@Module(
        injects = PhotosProviderImpl.class,
        library = true,
        complete = false
)
public class PhotosProviderModule {

    @Singleton
    @Provides
    public PhotosProvider providePhotosProvider(Application app) {
        PhotosProviderImpl photosProvider = new PhotosProviderImpl();
        MPOFApp.get(app).inject(photosProvider);
        return photosProvider;
    }
}
