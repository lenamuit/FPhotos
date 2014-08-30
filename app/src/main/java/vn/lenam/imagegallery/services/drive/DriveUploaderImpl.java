package vn.lenam.imagegallery.services.drive;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.inject.Inject;

import vn.lenam.imagegallery.MPOFApp;
import vn.lenam.imagegallery.data.PrefService;
import vn.lenam.imagegallery.helper.LogUtils;
import vn.lenam.imagegallery.services.UploadCompletedListener;

/**
 * Created by namlh on 8/28/14.
 */
class DriveUploaderImpl implements DriveUploader, ResultCallback<DriveApi.ContentsResult>, GoogleApiClient.ConnectionCallbacks {

    private static final String DRIVE_FOLDER_NAME = "FPhotos";
    @Inject
    GoogleApiClient mClient;
    @Inject
    PrefService prefService;
    private String filePath;
    private UploadCompletedListener listener;
    private DriveId driveId = null;
    /**
     * folder created callback
     */
    private ResultCallback<DriveFolder.DriveFolderResult> folderCreatedCallback = new
            ResultCallback<DriveFolder.DriveFolderResult>() {
                @Override
                public void onResult(DriveFolder.DriveFolderResult result) {
                    if (!result.getStatus().isSuccess()) {
                        listener.onUploadError("Problem while trying to create a folder");
                        return;
                    }
                    driveId = result.getDriveFolder().getDriveId();
                    prefService.saveString(PrefService.PrefKey.DRIVE_FOLDER_ID, driveId.encodeToString());
                    LogUtils.w("create folder success driverId: " + driveId);
                    if (mClient.isConnected()) {
                        Drive.DriveApi.newContents(mClient).setResultCallback(DriveUploaderImpl.this);
                    } else {
                        listener.authError();
                    }
                }
            };

    @Inject
    public DriveUploaderImpl(Context context) {
        MPOFApp.get(context).inject(this);
        mClient.registerConnectionCallbacks(this);
        String driveResId = prefService.getString(PrefService.PrefKey.DRIVE_FOLDER_ID);
        LogUtils.d("driverID=" + driveResId);
        if (driveResId != null) {
            driveId = DriveId.decodeFromString(driveResId);
        }
    }

    @Override
    public void upload(final String filePath, UploadCompletedListener listener) {
        this.filePath = filePath;
        this.listener = listener;
        if (mClient.isConnected()) {
            if (driveId == null) {
                createFolder();
            } else {
                Drive.DriveApi.newContents(mClient).setResultCallback(this);
            }
        } else {
            listener.authError();
        }
    }

    @Override
    public void onResult(DriveApi.ContentsResult result) {

        if (!result.getStatus().isSuccess()) {
            LogUtils.d("Failed to create new contents.");
            return;
        }
        // Otherwise, we can write our data to the new contents.
        LogUtils.d("New contents created.");
        // Get an output stream for the contents.
        OutputStream outputStream = result.getContents().getOutputStream();
        try {
            byte[] bytes = Files.toByteArray(new File(filePath));
            outputStream.write(bytes);
        } catch (IOException e1) {
            LogUtils.e("Unable to write file contents.", e1);
        }

        MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
                .setMimeType("image/jpeg").setTitle(getFileName()).build();

        Drive.DriveApi.getFolder(mClient, driveId).createFile(mClient, metadataChangeSet, result.getContents())
                .setResultCallback(new ResultCallback<DriveFolder.DriveFileResult>() {
                    @Override
                    public void onResult(DriveFolder.DriveFileResult driveFileResult) {
                        //TODO create file success
                        if (listener != null) {
                            listener.onUploadComplete(filePath);
                        }
                    }
                });
    }

    private String getFileName() {
        String[] fs = filePath.split("/");
        return fs[fs.length - 1];
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (driveId == null) {
            createFolder();
        } else {
            Drive.DriveApi.newContents(mClient).setResultCallback(this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    private void createFolder() {
        DriveFolder folder = Drive.DriveApi.getRootFolder(mClient);
        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                .setTitle(DRIVE_FOLDER_NAME).build();
        folder.createFolder(mClient, changeSet).setResultCallback(folderCreatedCallback);
    }
}
