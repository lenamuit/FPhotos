package vn.lenam.imagegallery.ui.main;

import android.util.Log;

import com.facebook.Session;
import com.facebook.SessionState;

/**
 * Created by namlh on 8/7/14.
 */
public class MainPresenterImpl implements MainPresenter, Session.StatusCallback {

    private MainView mainView;

    private boolean isSessionOpened = false;

    @Override
    public void checkLoginStatus(MainView view) {
        mainView = view;
        if (isSessionOpened) {
            mainView.hideButtonFacebook();
        } else {
            mainView.showButtonFacebook();
        }
    }


    @Override
    public void call(Session session, SessionState state, Exception exception) {
        if (exception != null)
            exception.printStackTrace();
        if (state.isClosed()) {
            Log.e("NamLH", "Session close");
            isSessionOpened = false;
        } else if (state.isOpened()) {
            Log.e("NamLH", "Session open");
            isSessionOpened = true;
        }
        if (mainView != null) {
            checkLoginStatus(mainView);
        }
    }
}
