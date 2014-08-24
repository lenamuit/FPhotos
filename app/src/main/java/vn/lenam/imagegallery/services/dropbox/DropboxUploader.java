package vn.lenam.imagegallery.services.dropbox;

import vn.lenam.imagegallery.services.UploadCompletedListener;

/**
 * Created by Le Nam on 24-Aug-14.
 */
public interface DropboxUploader {
    void upload(String filePath, UploadCompletedListener listener);
}
