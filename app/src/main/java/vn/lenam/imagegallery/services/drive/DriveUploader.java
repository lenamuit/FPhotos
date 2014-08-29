package vn.lenam.imagegallery.services.drive;

import vn.lenam.imagegallery.services.UploadCompletedListener;

/**
 * Created by namlh on 8/28/14.
 */
public interface DriveUploader {
    void upload(String filePath, UploadCompletedListener listener);
}
