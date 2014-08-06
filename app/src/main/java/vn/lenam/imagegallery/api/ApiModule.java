package vn.lenam.imagegallery.api;

import com.facebook.Session;

import dagger.Module;
import dagger.Provides;

/**
 * Created by namlh on 8/6/14.
 */
@Module(
        complete = false,
        library = true
)
public class ApiModule {


    @Provides
    public Session provideSession() {
        return Session.getActiveSession();
    }
}
