package vn.lenam.imagegallery.api;

import android.util.Log;

import com.facebook.Session;
import com.facebook.SessionState;

import javax.inject.Inject;

import vn.lenam.imagegallery.ui.main.MainPresenter;

/**
 * Created by namlh on 8/6/14.
 */
public class SessionStatusCallback implements Session.StatusCallback {

    private final MainPresenter mainPresenter;
    private Session session;

    @Inject
    public SessionStatusCallback(MainPresenter presenter) {
        mainPresenter = presenter;
        session = Session.getActiveSession();
        if (session != null &&
                (session.isOpened() || session.isClosed())) {
            call(session, session.getState(), null);
        }
    }

    @Override
    public void call(Session session, SessionState state, Exception exception) {
        if (exception != null)
            exception.printStackTrace();
        if (state.isClosed()) {
            Log.e("NamLH", "Session close");
            mainPresenter.onSessionClosed();
        } else if (state.isOpened()) {
            Log.e("NamLH", "Session open");
            mainPresenter.onSessionOpened();
        }
    }
}
