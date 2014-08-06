package vn.lenam.imagegallery.ui.main;

/**
 * Created by namlh on 8/7/14.
 */
public class MainPresenterImpl implements MainPresenter {

    private final MainView mainView;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }


    @Override
    public void onSessionClosed() {
        mainView.showButtonFacebook();
    }

    @Override
    public void onSessionOpened() {
        mainView.hideButtonFacebook();
    }
}
