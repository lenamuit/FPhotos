package vn.lenam.imagegallery.ui.main;

import android.app.Application;

import com.facebook.Session;
import com.facebook.SessionState;

import javax.inject.Inject;

import vn.lenam.imagegallery.MPOFApp;
import vn.lenam.imagegallery.api.model.GraphAlbum;
import vn.lenam.imagegallery.data.StoreBitmapService;
import vn.lenam.imagegallery.data.photos.PhotosProvider;
import vn.lenam.imagegallery.data.photos.PhotosProviderListener;
import vn.lenam.imagegallery.helper.LogUtils;
import vn.lenam.imagegallery.ui.album.OnAlbumSelected;

/**
 * Created by namlh on 8/7/14.
 */
class MainPresenterImpl implements MainPresenter, Session.StatusCallback, OnAlbumSelected, PhotosProviderListener {

    @Inject
    PhotosProvider photosProvider;

    @Inject
    StoreBitmapService storeBitmapService;


    private MainView mainView;
    private boolean isSessionOpened = false;

    @Inject
    public MainPresenterImpl(Application app) {
        MPOFApp.get(app).inject(this);
        photosProvider.addListener(this);
    }

    @Override
    public void checkLoginStatus(MainView view) {
        if (!isSessionOpened && Session.getActiveSession() != null) {
            isSessionOpened = Session.getActiveSession().isOpened();
        }
        mainView = view;
        LogUtils.w("Session opened = " + isSessionOpened);
        if (isSessionOpened) {
            mainView.hideButtonFacebook();
            photosProvider.onStart("me/photos");
        } else {
            mainView.showButtonFacebook();
        }
    }

    @Override
    public void onNeedLoadmore() {
//        requestPhotos.loadmore();
    }

    @Override
    public void call(Session session, SessionState state, Exception exception) {
        if (exception != null)
            exception.printStackTrace();
        if (state.isClosed()) {
            isSessionOpened = false;
        } else if (state.isOpened()) {
            if (isSessionOpened) {
                return;
            }
            isSessionOpened = true;
        }
        if (mainView != null) {
            checkLoginStatus(mainView);
        }
    }

    /**
     * on album selected
     *
     * @param album
     */
    @Override
    public void onSelected(GraphAlbum album) {
//        String path = album.getId() + "/photos";
//        mainView.clearPhotos();
//        requestPhotos.request(path, this);
    }

    @Override
    public void onRequestPhotosSuccess(int page) {
        mainView.addPhotos(photosProvider.getPage(page));
    }
}
