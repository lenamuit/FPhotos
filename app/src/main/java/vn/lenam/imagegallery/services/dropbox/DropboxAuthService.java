package vn.lenam.imagegallery.services.dropbox;

import vn.lenam.imagegallery.services.AuthCompletedListener;

/**
 * Created by Le Nam on 24-Aug-14.
 */
public interface DropboxAuthService {
    void auth(AuthCompletedListener listener);
}
