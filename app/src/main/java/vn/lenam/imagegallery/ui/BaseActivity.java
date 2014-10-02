package vn.lenam.imagegallery.ui;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.splunk.mint.Mint;

import javax.inject.Inject;

import vn.lenam.imagegallery.BuildConfig;
import vn.lenam.imagegallery.MPOFApp;
import vn.lenam.imagegallery.data.PrefService;
import vn.lenam.imagegallery.helper.LogUtils;
import vn.lenam.imagegallery.services.dropbox.DropboxAuthCallback;
import vn.lenam.imagegallery.ui.login.LoginActivity;

/**
 * Created by namlh on 9/29/14.
 */
public abstract class BaseActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener, Session.StatusCallback {

    private static final int RESOLVE_CONNECTION_REQUEST_CODE = 998;

    UiLifecycleHelper uiHelper;

    @Inject
    DropboxAuthCallback dropboxAuthCallback;
    @Inject
    DropboxAPI<AndroidAuthSession> dropboxAPI;
    @Inject
    PrefService prefService;
    @Inject
    GoogleApiClient googleApiClient;

    private boolean isFacebookOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!BuildConfig.DEBUG) {
            Mint.initAndStartSession(this, "2ebd5a77");
        }
        MPOFApp.get(this).inject(this);

        uiHelper = new UiLifecycleHelper(this, this);
        uiHelper.onCreate(savedInstanceState);

        //Activity will handle connection fail
        googleApiClient.registerConnectionFailedListener(this);
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

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
        dropboxResume();
        if (Session.getActiveSession() != null && !Session.getActiveSession().isOpened()) {
            startActivityForResult(new Intent(this, LoginActivity.class), 0);
        } else if (!isFacebookOpened) {
            isFacebookOpened = true;
            facebookSessionOpened();
        }
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
    public void call(Session session, SessionState state, Exception exception) {
        if (session != null && session.isOpened() && !isFacebookOpened) {
            isFacebookOpened = true;
            facebookSessionOpened();
        }
    }

    public abstract void facebookSessionOpened();
}
