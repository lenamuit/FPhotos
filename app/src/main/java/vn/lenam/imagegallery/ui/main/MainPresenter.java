package vn.lenam.imagegallery.ui.main;

import java.util.List;

import vn.lenam.imagegallery.api.model.GraphPhotoInfo;

/**
 * Created by namlh on 8/7/14.
 */
public interface MainPresenter {
    void checkLoginStatus(MainView view);

    List<GraphPhotoInfo> getMorePhotos();
}
