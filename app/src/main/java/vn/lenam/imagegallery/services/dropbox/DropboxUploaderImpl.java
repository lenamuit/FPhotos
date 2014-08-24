package vn.lenam.imagegallery.services.dropbox;

import android.content.Context;

import javax.inject.Inject;

import vn.lenam.imagegallery.services.UploadCompletedListener;

/**
 * Created by Le Nam on 24-Aug-14.
 */
class DropboxUploaderImpl implements DropboxUploader, DropboxAuthCallback {

    @Inject
    Context context;

    private UploadCompletedListener listener;
    private String filePath;

    @Override
    public void upload(String filePath, UploadCompletedListener listener) {
        this.listener = listener;
        if (isAuthed()) {
            uploadFile();
        } else {
            listener.authError();
        }
    }

    @Override
    public void authSuccess(String token) {
        uploadFile();
    }

    @Override
    public void authFail() {
        listener.onUploadError("Authentication fail");
    }

    private void uploadFile() {

    }

    private boolean isAuthed() {
        return false;
    }
}
