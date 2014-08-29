package vn.lenam.imagegallery.ui;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import vn.lenam.imagegallery.MPOFApp;
import vn.lenam.imagegallery.R;
import vn.lenam.imagegallery.data.PrefService;
import vn.lenam.imagegallery.helper.LogUtils;
import vn.lenam.imagegallery.services.dropbox.DropboxAuthCallback;
import vn.lenam.imagegallery.ui.album.AlbumsDialog;
import vn.lenam.imagegallery.ui.main.MainViewImpl;


/**
 * Created by Le Nam on 06-Aug-14.
 */
@Singleton
public class MainActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int RESOLVE_CONNECTION_REQUEST_CODE = 998;

    UiLifecycleHelper uiHelper;
    @Inject
    Session.StatusCallback sessionStatusCallback;
    @Inject
    DropboxAuthCallback dropboxAuthCallback;
    @Inject
    DropboxAPI<AndroidAuthSession> dropboxAPI;
    @Inject
    PrefService prefService;
    @Inject
    GoogleApiClient googleApiClient;

    @InjectView(R.id.container)
    MainViewImpl container;

    private AlbumsDialog albumsView;
    private GoogleApiClient.ConnectionCallbacks driveConnectionCallback;
    private GoogleApiClient.OnConnectionFailedListener driveOnConnectionFailed;

//    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        BugSenseHandler.initAndStartSession(this, "2ebd5a77");
        setContentView(R.layout.activity_main);

        MPOFApp.get(this).inject(this);

        ButterKnife.inject(this);

        uiHelper = new UiLifecycleHelper(this, sessionStatusCallback);
        uiHelper.onCreate(savedInstanceState);

        //Activity will handle connection fail
        googleApiClient.registerConnectionFailedListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
        dropboxResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESOLVE_CONNECTION_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    googleApiClient.connect();
                }
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @OnClick(R.id.btn_albums)
    void showAblumPopup() {
        if (albumsView == null) {
            albumsView = new AlbumsDialog(this);
        }
        albumsView.show();
    }

    @Override
    public void onBackPressed() {
        if (container.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    /**
     * handle dropbox on resume
     */
    private void dropboxResume() {
        //Auth with dropbox handle
        String dropbox_token = prefService.getString(PrefService.PrefKey.DROPBOX_TOKEN);
        if (dropbox_token != null) {
            return;
        }
        if (dropboxAPI.getSession().authenticationSuccessful()) {
            try {
                // Required to complete auth, sets the access token on the session
                dropboxAPI.getSession().finishAuthentication();
                String accessToken = dropboxAPI.getSession().getOAuth2AccessToken();
                dropboxAuthCallback.authSuccess(accessToken);
            } catch (IllegalStateException e) {
                LogUtils.w("DbAuthLog", "Error authenticating", e);
                dropboxAuthCallback.authFail();
            }
        } else {
            dropboxAuthCallback.authFail();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, RESOLVE_CONNECTION_REQUEST_CODE);
            } catch (IntentSender.SendIntentException e) {
                // Unable to resolve, message user appropriately
            }
        } else {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
        }
    }
}
