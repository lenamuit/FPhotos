package vn.lenam.imagegallery.ui.share;

import android.content.Context;

import vn.lenam.imagegallery.api.model.GraphPhotoInfo;

/**
 * Created by Le Nam on 23-Aug-14.
 */
public interface ShareHandler {
    void startShareSns(Context context, GraphPhotoInfo photo);

    void startSaveGallery(Context context, GraphPhotoInfo photo);

    void startUploadDropbox(Context context, GraphPhotoInfo photo);

    void startUploadDrive(Context context, GraphPhotoInfo photo);

    void startShareFacebook(Context context, GraphPhotoInfo photo);

    void startShareMessage(Context context, GraphPhotoInfo photo);

    void startShareInstagram(Context context, GraphPhotoInfo photo);

    void startShareTwitter(Context context, GraphPhotoInfo graphPhotoInfo);
}
