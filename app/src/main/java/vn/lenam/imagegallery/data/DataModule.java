package vn.lenam.imagegallery.data;

import android.app.Application;

import com.android.volley.toolbox.ImageLoader;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import vn.lenam.imagegallery.MPOFApp;

/**
 * Created by Le Nam on 06-Aug-14.
 */
@Module(
        injects = {StoreBitmapServiceImpl.class},
        complete = false,
        library = true
)
public class DataModule {

    @Provides
    @Singleton
    public ImageLoader.ImageCache provideImageCache(Application app) {
        return new DiskLruImageCache(app);
    }

    @Provides
    @Singleton
    public StoreBitmapService provideStoreBitmapService(Application app) {
        StoreBitmapServiceImpl storeBitmapService = new StoreBitmapServiceImpl();
        MPOFApp.get(app).inject(storeBitmapService);
        return storeBitmapService;
    }

    @Provides
    @Singleton
    public PrefService providePrefService(Application app) {
        return new PrefSeviceImpl(app);
    }

}
