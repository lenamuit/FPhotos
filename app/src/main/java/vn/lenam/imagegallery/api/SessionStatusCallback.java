package vn.lenam.imagegallery.api;

/**
 * Created by namlh on 8/6/14.
 */
//public class SessionStatusCallback implements Session.StatusCallback {
//
//    private final MainPresenter mainPresenter;
//    private Session session;
//
//    @Inject
//    public SessionStatusCallback(MainPresenter presenter) {
//        mainPresenter = presenter;
//        session = Session.getActiveSession();
//        if (session.isOpened()){
//            Log.e("NamLH", "Session open");
//            mainPresenter.onSessionStatusChanged(true);
//        }
//    }
//
//    @Override
//    public void call(Session session, SessionState state, Exception exception) {
//        if (exception != null)
//            exception.printStackTrace();
//        if (state.isClosed()) {
//            Log.e("NamLH", "Session close");
//            mainPresenter.onSessionStatusChanged(false);
//        } else if (state.isOpened()) {
//            Log.e("NamLH", "Session open");
//            mainPresenter.onSessionStatusChanged(true);
//        }
//    }
//}
