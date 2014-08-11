package vn.lenam.imagegallery.ui.main;

import vn.lenam.imagegallery.api.model.GraphPhotoInfo;

/**
 * Created by namlh on 8/7/14.
 */
public interface MainPresenter {
    void checkLoginStatus(MainView view);

    void onNeedLoadmore();

    void shareBitmap(GraphPhotoInfo photo, MainView view);

    void saveBitmap(GraphPhotoInfo photo, MainView view);
}
