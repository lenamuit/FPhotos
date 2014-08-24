package vn.lenam.imagegallery.services;

/**
 * Created by Le Nam on 24-Aug-14.
 */
public interface UploadCompletedListener {
    void onUploadComplete(String url);

    void onUploadError(String message);

    void authError();
}
