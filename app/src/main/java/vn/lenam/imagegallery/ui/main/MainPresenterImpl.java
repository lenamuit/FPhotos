package vn.lenam.imagegallery.ui.main;

import android.util.Log;

import com.facebook.Session;
import com.facebook.SessionState;

import java.util.List;

import javax.inject.Inject;

import vn.lenam.imagegallery.api.RequestPhotos;
import vn.lenam.imagegallery.api.model.GraphPhotoInfo;

/**
 * Created by namlh on 8/7/14.
 */
public class MainPresenterImpl implements MainPresenter, Session.StatusCallback {

    @Inject
    RequestPhotos requestPhotos;
    private MainView mainView;
    private boolean isSessionOpened = false;

    @Override
    public void checkLoginStatus(MainView view) {
        mainView = view;
        if (isSessionOpened) {
            mainView.hideButtonFacebook();
            requestPhotos.request("photos", this);
        } else {
            mainView.showButtonFacebook();
        }
    }

    @Override
    public void onLoadPhotoFinnished(List<GraphPhotoInfo> photos) {
        mainView.addPhotos(photos);
    }


    @Override
    public void call(Session session, SessionState state, Exception exception) {
        if (exception != null)
            exception.printStackTrace();
        if (state.isClosed()) {
            Log.e("NamLH", "Session close");
            isSessionOpened = false;
        } else if (state.isOpened()) {
            isSessionOpened = true;
        }
        if (mainView != null) {
            checkLoginStatus(mainView);
        }
    }
}
