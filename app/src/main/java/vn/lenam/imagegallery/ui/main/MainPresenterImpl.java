package vn.lenam.imagegallery.ui.main;

import android.app.Application;

import com.facebook.Session;
import com.facebook.SessionState;

import java.util.List;

import javax.inject.Inject;

import vn.lenam.imagegallery.MPOFApp;
import vn.lenam.imagegallery.api.model.GraphPhotoInfo;
import vn.lenam.imagegallery.data.photos.PhotosProvider;
import vn.lenam.imagegallery.data.photos.PhotosProviderListener;
import vn.lenam.imagegallery.helper.LogUtils;

/**
 * Created by namlh on 8/7/14.
 */
class MainPresenterImpl implements MainPresenter, PhotosProviderListener {

    @Inject
    PhotosProvider photosProvider;

    private MainView mainView;
    private boolean isSessionOpened = false;
    private int maxPage = 0;
    private int currentPage = 0;

    @Inject
    public MainPresenterImpl(Application app) {
        MPOFApp.get(app).inject(this);
        photosProvider.addListener(this);
    }

    @Override
    public void checkLoginStatus(MainView view) {
//        if (!isSessionOpened && Session.getActiveSession() != null) {
//            isSessionOpened = Session.getActiveSession().isOpened();
//        }
        mainView = view;
//        LogUtils.w("Session opened = " + isSessionOpened);
//        if (isSessionOpened) {
            mainView.hideButtonFacebook();
            photosProvider.onStart("me/photos");
//        } else {
//            mainView.showButtonFacebook();
//        }
    }

    @Override
    public List<GraphPhotoInfo> getMorePhotos() {
        LogUtils.w("getPage morephoto "+currentPage);
        currentPage++;
        if (currentPage<=maxPage){
            return photosProvider.getPage(currentPage);
        }
        return null;
    }

//    @Override
//    public void call(Session session, SessionState state, Exception exception) {
//        if (exception != null)
//            exception.printStackTrace();
//        if (state.isClosed()) {
//            isSessionOpened = false;
//        } else if (state.isOpened()) {
//            if (isSessionOpened) {
//                return;
//            }
//            isSessionOpened = true;
//        }
//        if (mainView != null) {
//            checkLoginStatus(mainView);
//        }
//    }


    @Override
    public void onRequestPhotosSuccess(int page) {
        maxPage = page;
        if (page == 0){
            currentPage=0;
            mainView.addPhotos(photosProvider.getPage(page));
        }
    }
}
