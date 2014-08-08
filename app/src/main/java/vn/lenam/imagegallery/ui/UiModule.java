package vn.lenam.imagegallery.ui;

import android.app.Application;

import com.facebook.Session;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import vn.lenam.imagegallery.MPOFApp;
import vn.lenam.imagegallery.ui.main.ImageViewFragment;
import vn.lenam.imagegallery.ui.main.MainActivity;
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
                ImageViewFragment.class},
        complete = false,
        library = true
)
public class UiModule {

    private MainPresenter presenter;

//    @Provides
//    public UiLifecycleHelper provideUiLifecycleHelper(MainActivity activity,Session.StatusCallback callback) {
//        return new UiLifecycleHelper(activity, callback);
//    }

    @Provides
    @Singleton
    public MainPresenter provideMainPresenter(Application app) {
        presenter = new MainPresenterImpl();
        MPOFApp.get(app).inject(presenter);
        return presenter;
    }

    @Provides
    @Singleton
    public Session.StatusCallback provideSessionStatusCallback() {
        return (Session.StatusCallback) presenter;
    }
}