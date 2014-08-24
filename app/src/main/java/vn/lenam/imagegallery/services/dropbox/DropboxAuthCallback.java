package vn.lenam.imagegallery.services.dropbox;

/**
 * Created by Le Nam on 24-Aug-14.
 */
public interface DropboxAuthCallback {
    void authSuccess(String token);

    void authFail();
}
