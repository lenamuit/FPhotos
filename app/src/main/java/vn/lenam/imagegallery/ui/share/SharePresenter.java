package vn.lenam.imagegallery.ui.share;

import vn.lenam.imagegallery.api.model.GraphPhotoInfo;

/**
 * Created by Le Nam on 23-Aug-14.
 */
public interface SharePresenter {

    void onStartDownloadFile(ShareType type, ShareView view, GraphPhotoInfo photo);

    public enum ShareType {
        DROPBOX(0),
        SNS(1),
        GALLERY(2),
        DRIVE(3),
        FACEBOOK(4),
        MESSAGE(5),
        INSTAGRAM(6),
        TWITTER(7);
        final int nativeInt;

        ShareType(int ni) {
            nativeInt = ni;
        }
    }

}
