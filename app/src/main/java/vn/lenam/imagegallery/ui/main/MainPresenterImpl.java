package vn.lenam.imagegallery.ui.main;

import com.facebook.Session;
import com.facebook.SessionState;

import java.util.List;

import javax.inject.Inject;

import vn.lenam.imagegallery.api.OnRequestApiCompleted;
import vn.lenam.imagegallery.api.RequestApi;
import vn.lenam.imagegallery.api.model.GraphAlbum;
import vn.lenam.imagegallery.api.model.GraphPhotoInfo;
import vn.lenam.imagegallery.data.StoreBitmapService;
import vn.lenam.imagegallery.helper.LogUtils;
import vn.lenam.imagegallery.ui.album.OnAlbumSelected;

/**
 * Created by namlh on 8/7/14.
 */
class MainPresenterImpl implements MainPresenter, Session.StatusCallback, OnRequestApiCompleted<List<GraphPhotoInfo>>, OnAlbumSelected {

    @Inject
    RequestApi<List<GraphPhotoInfo>> requestPhotos;

    @Inject
    StoreBitmapService storeBitmapService;


    private MainView mainView;
    private boolean isSessionOpened = false;

    @Override
    public void checkLoginStatus(MainView view) {
        if (!isSessionOpened && Session.getActiveSession() != null) {
            isSessionOpened = Session.getActiveSession().isOpened();
        }
        mainView = view;
        LogUtils.w("Session opened = " + isSessionOpened);
        if (isSessionOpened) {
            mainView.hideButtonFacebook();
            requestPhotos.request("me/photos", this);
        } else {
            mainView.showButtonFacebook();
        }
    }

    @Override
    public void onNeedLoadmore() {
        requestPhotos.loadmore();
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

    @Override
    public void onCompleted(List<GraphPhotoInfo> photos, boolean fromCache) {
        mainView.addPhotos(photos);
        if (fromCache) {
            mainView.showNoticeNoNetwork();
        } else {
            mainView.hideNoticeNoNetwork();
        }
    }

    /**
     * on album selected
     *
     * @param album
     */
    @Override
    public void onSelected(GraphAlbum album) {
        String path = album.getId() + "/photos";

        mainView.clearPhotos();
        requestPhotos.request(path, this);
    }
}
