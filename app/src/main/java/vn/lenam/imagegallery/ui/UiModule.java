package vn.lenam.imagegallery.ui;

import android.app.Application;

import com.facebook.Session;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import vn.lenam.imagegallery.MPOFApp;
import vn.lenam.imagegallery.ui.album.AlbumsDialog;
import vn.lenam.imagegallery.ui.album.AlbumsPresenter;
import vn.lenam.imagegallery.ui.album.AlbumsPresenterImpl;
import vn.lenam.imagegallery.ui.album.OnAlbumSelected;
import vn.lenam.imagegallery.ui.main.ImageViewFragment;
import vn.lenam.imagegallery.ui.main.MainPresenter;
import vn.lenam.imagegallery.ui.main.MainPresenterImpl;
import vn.lenam.imagegallery.ui.main.MainViewImpl;

/**
 * Created by Le Nam on 06-Aug-14.
 */
@Module(
        injects = {MainActivity.class,
                MainViewImpl.class,
                MainPresenterImpl.class,
                ImageViewFragment.class,
                AlbumsPresenterImpl.class,
                AlbumsDialog.class},
        complete = false,
        library = true
)
public class UiModule {

    private MainPresenter presenter;

    @Provides
    @Singleton
    public MainPresenter provideMainPresenter(Application app) {
        presenter = new MainPresenterImpl();
        MPOFApp.get(app).inject(presenter);
        return presenter;
    }

    @Provides
    @Singleton
    public AlbumsPresenter provideAlbumPresenter(Application app) {
        AlbumsPresenterImpl albumsPresenter = new AlbumsPresenterImpl();
        MPOFApp.get(app).inject(albumsPresenter);
        return albumsPresenter;
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
