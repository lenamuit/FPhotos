package vn.lenam.imagegallery.ui.main;

import android.util.Log;

import com.facebook.Session;
import com.facebook.SessionState;

import java.util.List;

import javax.inject.Inject;

import vn.lenam.imagegallery.api.OnRequestListCompleted;
import vn.lenam.imagegallery.api.RequestApi;
import vn.lenam.imagegallery.api.model.GraphAlbum;
import vn.lenam.imagegallery.api.model.GraphPhotoInfo;
import vn.lenam.imagegallery.data.StoreBitmapService;
import vn.lenam.imagegallery.ui.album.OnAlbumSelected;

/**
 * Created by namlh on 8/7/14.
 */
public class MainPresenterImpl implements MainPresenter, Session.StatusCallback, OnRequestListCompleted<GraphPhotoInfo>, OnAlbumSelected {

    @Inject
    RequestApi<GraphPhotoInfo> requestPhotos;

    @Inject
    StoreBitmapService storeBitmapService;

    private MainView mainView;
    private boolean isSessionOpened = false;

    @Override
    public void checkLoginStatus(MainView view) {
        mainView = view;
        Log.i("checkLoginStatus", "Session opened = " + isSessionOpened);
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
    public void shareBitmap(GraphPhotoInfo photo, MainView view) {
        String file = storeBitmapService.save(photo);
        if (file != null) {
            view.sharePhoto(file);
        }
    }

    @Override
    public void saveBitmap(GraphPhotoInfo photo, MainView view) {
        String file = storeBitmapService.save(photo);
        if (file != null) {
            view.savePhotoSuccess();
        }
    }

    @Override
    public void call(Session session, SessionState state, Exception exception) {
        if (exception != null)
            exception.printStackTrace();
        if (state.isClosed()) {
            isSessionOpened = false;
        } else if (state.isOpened()) {
            isSessionOpened = true;
        }
        if (mainView != null) {
            checkLoginStatus(mainView);
        }
    }

    @Override
    public void onCompleted(List<GraphPhotoInfo> photos) {
        mainView.addPhotos(photos);
    }

    /**
     * on album selected
     *
     * @param album
     */
    @Override
    public void onSelected(GraphAlbum album) {
        Log.e("Namlh", "select album " + album.getName());
        mainView.clearPhotos();
        requestPhotos.request(album.getId() + "/photos", this);

    }
}
