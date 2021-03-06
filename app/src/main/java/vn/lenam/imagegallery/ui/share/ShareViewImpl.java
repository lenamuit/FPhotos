package vn.lenam.imagegallery.ui.share;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import vn.lenam.imagegallery.R;
import vn.lenam.imagegallery.api.model.GraphPhotoInfo;
import vn.lenam.imagegallery.helper.DialogHelper;
import vn.lenam.imagegallery.helper.LogUtils;
import vn.lenam.imagegallery.helper.ToastUtils;

/**
 * Created by Le Nam on 23-Aug-14.
 */
class ShareViewImpl implements ShareView, ShareHandler {


    private final String pgFacebook = "com.facebook.katana";
    private final String pgFbMessage = "com.facebook.orca";
    private final String pgInstagram = "com.instagram.android";
    private final String pgTwitter = "com.twitter.android";
    @Inject
    SharePresenter presenter;
    @Inject
    DropboxAPI<AndroidAuthSession> dropboxAPI;
    @Inject
    GoogleApiClient googleApiClient;
    private Dialog dialog;
    private Context context;

    @Override
    public void startShareSns(Context context, GraphPhotoInfo photo) {
        this.context = context;
        this.dialog = ProgressDialog.show(context, "Downloading...", "Please wait...");
        presenter.onStartDownloadFile(SharePresenter.ShareType.SNS, this, photo);
    }

    @Override
    public void startSaveGallery(Context context, GraphPhotoInfo photo) {
        this.context = context;
        this.dialog = ProgressDialog.show(context, "Downloading...", "Please wait...");
        presenter.onStartDownloadFile(SharePresenter.ShareType.GALLERY, this, photo);
    }

    @Override
    public void startUploadDropbox(Context context, GraphPhotoInfo photo) {
        this.context = context;
        this.dialog = ProgressDialog.show(context, "Uploading...", "Please wait...");
        presenter.onStartDownloadFile(SharePresenter.ShareType.DROPBOX, this, photo);
    }

    @Override
    public void startUploadDrive(Context context, GraphPhotoInfo photo) {
        this.context = context;
        this.dialog = ProgressDialog.show(context, "Uploading...", "Please wait...");
        presenter.onStartDownloadFile(SharePresenter.ShareType.DRIVE, this, photo);
    }

    @Override
    public void startShareFacebook(Context context, GraphPhotoInfo photo) {
        this.context = context;
        this.dialog = ProgressDialog.show(context, "Downloading...", "Please wait...");
        presenter.onStartDownloadFile(SharePresenter.ShareType.FACEBOOK, this, photo);
    }

    @Override
    public void startShareMessage(Context context, GraphPhotoInfo photo) {
        this.context = context;
        this.dialog = ProgressDialog.show(context, "Downloading...", "Please wait...");
        presenter.onStartDownloadFile(SharePresenter.ShareType.MESSAGE, this, photo);
    }

    @Override
    public void startShareInstagram(Context context, GraphPhotoInfo photo) {
        this.context = context;
        this.dialog = ProgressDialog.show(context, "Downloading...", "Please wait...");
        presenter.onStartDownloadFile(SharePresenter.ShareType.INSTAGRAM, this, photo);
    }

    @Override
    public void startShareTwitter(Context context, GraphPhotoInfo photo) {
        this.context = context;
        this.dialog = ProgressDialog.show(context, "Downloading...", "Please wait...");
        presenter.onStartDownloadFile(SharePresenter.ShareType.TWITTER, this, photo);
    }

    @Override
    public void sharedSuccess(SharePresenter.ShareType type, String filePath) {
        dialog.dismiss();
        switch (type) {
            case SNS:
                doShareSns(filePath);
                break;
            case GALLERY:
                doOpenGallery(filePath);
                break;
            case DROPBOX:
            case DRIVE:
                ToastUtils.showToast(context, "Upload file successful.");
                break;
            case FACEBOOK:
                share(pgFacebook, filePath);
                break;
            case MESSAGE:
                share(pgFbMessage, filePath);
                break;
            case INSTAGRAM:
                share(pgInstagram, filePath);
                break;
            case TWITTER:
                share(pgTwitter, filePath);
                break;
            default:
                break;
        }
    }

    @Override
    public void sharedError(SharePresenter.ShareType type, String message) {
        dialog.dismiss();
        ToastUtils.showToast(context, message);
    }

    @Override
    public void authError(SharePresenter.ShareType type) {
        dialog.dismiss();
        switch (type) {
            case DROPBOX:
                dropboxAPI.getSession().startOAuth2Authentication(context);
                break;
            case DRIVE:
                if (googleApiClient.isConnected() || googleApiClient.isConnecting()) {
                    break;
                }
                googleApiClient.connect();
                break;
        }
    }

//    private void doUploadDropbox(String filePath) {
//        this.dialog = ProgressDialog.show(context, "Uploading...", "Please wait...");
//        dropboxUploader.upload(filePath, this);
//    }

    private void doOpenGallery(final String filePath) {
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

    private void share(String nameApp, String imagePath) {
        LogUtils.w("share " + nameApp);
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("image/jpeg");
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(share, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(nameApp)
                        || info.activityInfo.name.toLowerCase().contains(nameApp)) {
                    share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imagePath)));
                    share.putExtra(Intent.EXTRA_TEXT, "Shared by #FPhotos (http://goo.gl/p3DwIp)");
                    share.setPackage(info.activityInfo.packageName);
                    context.startActivity(share);
                    LogUtils.w("share " + nameApp + " >>> OKIE");
                    return;
                }
            }
        }
        LogUtils.w("share " + nameApp + " >>> FAILED");
        Uri marketUri = Uri.parse("market://details?id=" + nameApp);
        Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
        context.startActivity(marketIntent);
    }

}
