package vn.lenam.imagegallery.ui.album;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import vn.lenam.imagegallery.MPOFApp;

/**
 * Created by Le Nam on 23-Aug-14.
 */
@Module(
        injects = {AlbumsPresenterImpl.class,
                AlbumsDialog.class},
        library = true,
        complete = false
)
public class AlbumModule {

    @Provides
    @Singleton
    public AlbumsPresenter provideAlbumPresenter(Application app) {
        AlbumsPresenterImpl albumsPresenter = new AlbumsPresenterImpl();
        MPOFApp.get(app).inject(albumsPresenter);
        return albumsPresenter;
    }
}
