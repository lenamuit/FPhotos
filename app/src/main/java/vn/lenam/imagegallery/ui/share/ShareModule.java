package vn.lenam.imagegallery.ui.share;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import vn.lenam.imagegallery.MPOFApp;

/**
 * Created by Le Nam on 23-Aug-14.
 */
@Module(
        injects = {ShareViewImpl.class, SharePresenterImpl.class},
        complete = false,
        library = true
)
public class ShareModule {
    @Provides
    public ShareHandler provideShareHandler(Application app) {
        ShareViewImpl dialog = new ShareViewImpl();
        MPOFApp.get(app).inject(dialog);
        return dialog;
    }

    @Provides
    @Singleton
    public SharePresenter provideSharePresenter(Application app) {
        SharePresenter presenter = new SharePresenterImpl();
        MPOFApp.get(app).inject(presenter);
        return presenter;
    }
}
