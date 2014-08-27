package vn.lenam.imagegallery.ui.share;

/**
 * Created by Le Nam on 23-Aug-14.
 */
public interface ShareView {

    void sharedSuccess(SharePresenter.ShareType type, String filePath);

    void sharedError(SharePresenter.ShareType type, String message);

    void authError(SharePresenter.ShareType type);
}
