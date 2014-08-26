package vn.lenam.imagegallery.services.dropbox;

import android.app.Application;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import vn.lenam.imagegallery.MPOFApp;
import vn.lenam.imagegallery.data.PrefService;

/**
 * Created by Le Nam on 24-Aug-14.
 */
@Module(
        injects = DropboxUploaderImpl.class,
        library = true,
        complete = false
)
public class DropboxModule {

    protected static final String APP_KEY = "a2y7duyq1bqcggf";
    protected static final String APP_SECRET = "gz3x8lz1m1sz4mx";

    @Provides
    @Singleton
    public DropboxUploader provideDropboxUploader(Application application) {
        DropboxUploaderImpl dropboxUploader = new DropboxUploaderImpl();
        MPOFApp.get(application).inject(dropboxUploader);
        return dropboxUploader;
    }

    @Provides
    @Singleton
    public DropboxAuthCallback provideDropboxAuthCallback(DropboxUploader uploader) {
        return (DropboxAuthCallback) uploader;
    }

    @Provides
    @Singleton
    public DropboxAPI<AndroidAuthSession> provideDropboxApi(PrefService prefService) {
        // In the class declaration section:
        DropboxAPI<AndroidAuthSession> mDBApi;

        // And later in some initialization function:
        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys, prefService.getString(PrefService.PrefType.DROPBOX_TOKEN));
        mDBApi = new DropboxAPI<AndroidAuthSession>(session);
        return mDBApi;
    }
}
