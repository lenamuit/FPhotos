package vn.lenam.imagegallery.ui;

import com.facebook.Session;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import vn.lenam.imagegallery.ui.main.MainActivity;
import vn.lenam.imagegallery.ui.main.MainPresenter;
import vn.lenam.imagegallery.ui.main.MainPresenterImpl;
import vn.lenam.imagegallery.ui.main.MainViewImpl;

/**
 * Created by Le Nam on 06-Aug-14.
 */
@Module(
        injects = {MainActivity.class,
                MainViewImpl.class},
        complete = false,
        library = true
)
public class UiModule {

    private final MainPresenter presenter;

    public UiModule() {
        presenter = new MainPresenterImpl();
    }

//    @Provides
//    public UiLifecycleHelper provideUiLifecycleHelper(MainActivity activity,Session.StatusCallback callback) {
//        return new UiLifecycleHelper(activity, callback);
//    }

    @Provides
    @Singleton
    public MainPresenter provideMainPresenter() {
        return presenter;
    }

    @Provides
    @Singleton
    public Session.StatusCallback provideSessionStatusCallback() {
        return (Session.StatusCallback) presenter;
    }
}
