package vn.lenam.imagegallery.ui.main;

import android.app.Application;

import com.facebook.Session;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import vn.lenam.imagegallery.ui.album.OnAlbumSelected;

/**
 * Created by Le Nam on 23-Aug-14.
 */
@Module(
        injects = {MainPresenterImpl.class,
                MainViewImpl.class,
                ImageViewFragment.class},
        complete = false,
        library = true
)
public class MainModule {


    private MainPresenter presenter;

    @Provides
    @Singleton
    public MainPresenter provideMainPresenter(Application app) {
        presenter = new MainPresenterImpl(app);
        return presenter;
    }

    @Provides
    @Singleton
    public Session.StatusCallback provideSessionStatusCallback() {
        return (Session.StatusCallback) presenter;
    }

    @Provides
    @Singleton
    public OnAlbumSelected provideOnAlbumSelected() {
        return (OnAlbumSelected) presenter;
    }

}
