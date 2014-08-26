package vn.lenam.imagegallery.ui.share;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;

import java.io.File;

import javax.inject.Inject;

import vn.lenam.imagegallery.R;
import vn.lenam.imagegallery.api.model.GraphPhotoInfo;
import vn.lenam.imagegallery.helper.DialogHelper;
import vn.lenam.imagegallery.helper.LogUtils;
import vn.lenam.imagegallery.helper.ToastUtils;
import vn.lenam.imagegallery.services.UploadCompletedListener;
import vn.lenam.imagegallery.services.dropbox.DropboxUploader;

/**
 * Created by Le Nam on 23-Aug-14.
 */
class ShareViewImpl implements ShareView, ShareHandler, UploadCompletedListener {

    private static final int TYPE_SHARE_SNS = 1;
    private static final int TYPE_SAVE_GALLERY = 2;
    private static final int TYPE_UPLOAD_DROPBOX = 3;

    @Inject
    SharePresenter presenter;
    @Inject
    DropboxUploader dropboxUploader;
    @Inject
    DropboxAPI<AndroidAuthSession> dropboxAPI;

    private int type;
    private Dialog dialog;
    private Context context;

    @Override
    public void startShareSns(Context context, GraphPhotoInfo photo) {
        this.context = context;
        type = TYPE_SHARE_SNS;
        this.dialog = ProgressDialog.show(context, "Downloading...", "Please wait...");
        presenter.onStartDownloadFile(this, photo);
    }

    @Override
    public void startSaveGallery(Context context, GraphPhotoInfo photo) {
        this.context = context;
        type = TYPE_SAVE_GALLERY;
        this.dialog = ProgressDialog.show(context, "Downloading...", "Please wait...");
        presenter.onStartDownloadFile(this, photo);
    }

    @Override
    public void startUploadDrpobox(Context context, GraphPhotoInfo photo) {
        this.type = TYPE_UPLOAD_DROPBOX;
        this.context = context;
        this.dialog = ProgressDialog.show(context, "Downloading...", "Please wait...");
        presenter.onStartDownloadFile(this, photo);
    }

    @Override
    public void downloadSuccess(String filePath) {
        dialog.dismiss();
        switch (type) {
            case TYPE_SHARE_SNS:
                doShareSns(filePath);
                break;
            case TYPE_SAVE_GALLERY:
                doSaveGallery(filePath);
                break;
            case TYPE_UPLOAD_DROPBOX:
                doUploadDropbox(filePath);
                break;
        }
    }

    private void doUploadDropbox(String filePath) {
        this.dialog = ProgressDialog.show(context, "Uploading...", "Please wait...");
        dropboxUploader.upload(filePath, this);
    }

    private void doSaveGallery(final String filePath) {
        ToastUtils.showToast(context, R.string.msg_save_gallery);
        DialogHelper.showConfirmDilog(context, R.string.msg_confirm_open_photo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                File file = new File(filePath);
                intent.setDataAndType(Uri.fromFile(file), "image/*");
                context.startActivity(intent);
            }
        });

    }

    /**
     * do share sns
     *
     * @param filePath
     */
    private void doShareSns(String filePath) {
        LogUtils.w("Share path:" + filePath);
        Uri imageUri = Uri.parse("file://" + filePath);

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        sharingIntent.setType("image/*");
        context.startActivity(Intent.createChooser(sharingIntent, context.getResources().getString(R.string.msg_share_image)));
    }

    @Override
    public void onUploadComplete(String url) {
        dialog.dismiss();
        ToastUtils.showToast(context, "Upload successful.");
    }

    @Override
    public void onUploadError(String message) {
        ToastUtils.showToast(context, message);
    }

    @Override
    public void authError() {
        switch (type) {
            case TYPE_UPLOAD_DROPBOX:
                dropboxAPI.getSession().startOAuth2Authentication(context);
                break;
        }
    }
}
