package vn.lenam.imagegallery.ui.share;

import vn.lenam.imagegallery.api.model.GraphPhotoInfo;

/**
 * Created by Le Nam on 23-Aug-14.
 */
public interface SharePresenter {

    void onStartDownloadFile(ShareView view, GraphPhotoInfo photo);

}
