package vn.lenam.imagegallery.ui.main;


import java.util.List;

import vn.lenam.imagegallery.api.model.GraphPhotoInfo;

/**
 * Created by namlh on 8/7/14.
 */
public interface MainView {
    void hideButtonFacebook();

    void showButtonFacebook();

    void addPhotos(List<GraphPhotoInfo> photos);

    void clearPhotos();

    void sharePhoto(String path);

    void savePhotoSuccess();

    void showNoticeNoNetwork();

    void hideNoticeNoNetwork();

}
