package vn.lenam.imagegallery.ui.share;

import javax.inject.Inject;

import vn.lenam.imagegallery.api.OnRequestApiCompleted;
import vn.lenam.imagegallery.api.RequestApi;
import vn.lenam.imagegallery.api.model.GraphPhotoInfo;

/**
 * Created by Le Nam on 23-Aug-14.
 */
class SharePresenterImpl implements SharePresenter, OnRequestApiCompleted<String> {

    @Inject
    RequestApi<String> requestFile;

    private ShareView shareView;

    @Override
    public void onStartDownloadFile(ShareView view, GraphPhotoInfo photo) {
        shareView = view;
        requestFile.request(photo.getSource(), this);
    }

    @Override
    public void onCompleted(String object, boolean fromCache) {
        if (shareView != null) {
            shareView.downloadSuccess(object);
        }
    }
}
