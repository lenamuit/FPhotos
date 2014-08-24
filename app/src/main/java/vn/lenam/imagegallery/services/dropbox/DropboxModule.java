package vn.lenam.imagegallery.services.dropbox;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Le Nam on 24-Aug-14.
 */
@Module(
        injects = DropboxUploaderImpl.class,
        library = true,
        complete = false
)
public class DropboxModule {

    private static final String APP_KEY = "a2y7duyq1bqcggf";
    private static final String APP_SECRET = "gz3x8lz1m1sz4mx";


    private final DropboxUploaderImpl dropboxUploader;

    DropboxModule() {
        dropboxUploader = new DropboxUploaderImpl();
    }

    @Provides
    @Singleton
    public DropboxUploader provideDropboxUploader() {
        return dropboxUploader;
    }

    @Provides
    @Singleton
    public DropboxAuthCallback provideDropboxAuthCallback() {
        return dropboxUploader;
    }

    @Provides
    @Singleton
    public DropboxAPI<AndroidAuthSession> provideDropboxApi() {
        // In the class declaration section:
        DropboxAPI<AndroidAuthSession> mDBApi;

        // And later in some initialization function:
        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys);
        mDBApi = new DropboxAPI<AndroidAuthSession>(session);
        return mDBApi;
    }
}
