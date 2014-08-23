package vn.lenam.imagegallery.ui.share;

import android.content.Context;

import vn.lenam.imagegallery.api.model.GraphPhotoInfo;

/**
 * Created by Le Nam on 23-Aug-14.
 */
public interface ShareHandler {
    void startShareSns(Context context, GraphPhotoInfo photo);

    void startSaveGallery(Context context, GraphPhotoInfo photo);
}
