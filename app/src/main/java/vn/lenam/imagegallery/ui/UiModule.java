package vn.lenam.imagegallery.ui;

import com.facebook.Session;
import com.facebook.UiLifecycleHelper;

import dagger.Module;
import dagger.Provides;
import vn.lenam.imagegallery.AppModule;
import vn.lenam.imagegallery.api.SessionStatusCallback;
import vn.lenam.imagegallery.ui.main.MainActivity;
import vn.lenam.imagegallery.ui.main.MainPresenter;
import vn.lenam.imagegallery.ui.main.MainPresenterImpl;

/**
 * Created by Le Nam on 06-Aug-14.
 */
@Module(
        injects = MainActivity.class,
        addsTo = AppModule.class
)
public class UiModule {

    private final MainActivity activity;

    public UiModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    public UiLifecycleHelper provideUiLifecycleHelper(Session.StatusCallback callback) {
        return new UiLifecycleHelper(activity, callback);
    }

    @Provides
    public MainPresenter provideMainPresenter() {
        return new MainPresenterImpl(activity);
    }

    @Provides
    public Session.StatusCallback provideSessionStatusCallback(MainPresenter presenter) {
        return new SessionStatusCallback(presenter);
    }
}
