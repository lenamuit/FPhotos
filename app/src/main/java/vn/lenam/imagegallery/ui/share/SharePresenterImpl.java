package vn.lenam.imagegallery.ui.share;

import javax.inject.Inject;

import vn.lenam.imagegallery.api.OnRequestApiCompleted;
import vn.lenam.imagegallery.api.RequestApi;
import vn.lenam.imagegallery.api.model.GraphPhotoInfo;
import vn.lenam.imagegallery.services.UploadCompletedListener;
import vn.lenam.imagegallery.services.drive.DriveUploader;
import vn.lenam.imagegallery.services.dropbox.DropboxUploader;

/**
 * Created by Le Nam on 23-Aug-14.
 */
class SharePresenterImpl implements SharePresenter, OnRequestApiCompleted<String>, UploadCompletedListener {

    @Inject
    RequestApi<String> requestFile;
    @Inject
    DropboxUploader dropboxUploader;
    @Inject
    DriveUploader driveUploader;

    private ShareView shareView;
    private ShareType type;

    @Override
    public void onStartDownloadFile(ShareType type, ShareView view, GraphPhotoInfo photo) {
        this.shareView = view;
        this.type = type;
        requestFile.request(photo.getSource(), this);
    }

    /**
     * @param object    : file path
     * @param fromCache
     */
    @Override
    public void onCompleted(String object, boolean fromCache) {
        switch (type) {
            case DROPBOX:
                dropboxUploader.upload(object, this);
                break;
            case DRIVE:
                driveUploader.upload(object, this);
                break;
            default:
                if (shareView != null) {
                    shareView.sharedSuccess(type, object);
                }
                break;
        }

    }

    @Override
    public void onUploadComplete(String url) {
        if (shareView != null) {
            shareView.sharedSuccess(type, url);
        }
    }

    @Override
    public void onUploadError(String message) {
        if (shareView != null) {
            shareView.sharedError(type, message);
        }
    }

    @Override
    public void authError() {
        if (shareView != null) {
            shareView.authError(type);
        }
    }
}
