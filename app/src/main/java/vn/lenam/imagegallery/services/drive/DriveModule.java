package vn.lenam.imagegallery.services.drive;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by namlh on 8/28/14.
 */
@Module(
        injects = DriveUploaderImpl.class,
        library = true,
        complete = false
)
public class DriveModule {

    @Provides
    @Singleton
    public DriveUploader provideDriveUploader(Application app) {
        return new DriveUploaderImpl(app);
    }

    @Provides
    @Singleton
    public GoogleApiClient provideGoogleApiClient(Context context) {
        return new GoogleApiClient.Builder(context)
                .addApi(Drive.API)
                .addScope(Drive.SCOPE_FILE)
                .build();
    }

//    @Provides
//    public DriveFolder provideDriveFolder(GoogleApiClient client){
//        if (client.isConnected()){
//            return Drive.DriveApi.getRootFolder(client);
//        }
//        return null;
//    }

}
