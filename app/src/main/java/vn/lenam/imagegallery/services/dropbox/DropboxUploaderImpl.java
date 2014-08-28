package vn.lenam.imagegallery.services.dropbox;

import android.os.AsyncTask;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;

import java.io.File;
import java.io.FileInputStream;

import javax.inject.Inject;

import vn.lenam.imagegallery.data.PrefService;
import vn.lenam.imagegallery.helper.LogUtils;
import vn.lenam.imagegallery.services.UploadCompletedListener;

/**
 * Created by Le Nam on 24-Aug-14.
 */
class DropboxUploaderImpl implements DropboxUploader, DropboxAuthCallback {


    @Inject
    DropboxAPI<AndroidAuthSession> mDBApi;
    @Inject
    PrefService mPref;
    private UploadCompletedListener listener;
    private String filePath;

    @Override
    public void upload(String filePath, UploadCompletedListener listener) {
        this.listener = listener;
        this.filePath = filePath;
        if (isAuthed()) {
            uploadFile();
        } else {
            listener.authError();
        }
    }

    @Override
    public void authSuccess(String token) {
        mPref.saveString(PrefService.PrefKey.DROPBOX_TOKEN, token);
        // And later in some initialization function:
        AppKeyPair appKeys = new AppKeyPair(DropboxModule.APP_KEY, DropboxModule.APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys, token);
        mDBApi = new DropboxAPI<AndroidAuthSession>(session);
        uploadFile();
    }

    @Override
    public void authFail() {
        if (listener != null) {
            listener.onUploadError("Authentication fail");
        }
    }

    private void uploadFile() {
        if (listener != null) {
            (new UploadTask()).execute(filePath);
        }
    }

    private boolean isAuthed() {
        String token = mPref.getString(PrefService.PrefKey.DROPBOX_TOKEN);
        if (token != null) {
            return true;
        }
        return false;
    }

    private final class UploadTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                String filePath = strings[0];
                File file = new File(filePath);
                FileInputStream inputStream = null;
                inputStream = new FileInputStream(file);
                DropboxAPI.Entry response = mDBApi.putFile(getFileName(filePath), inputStream,
                        file.length(), null, null);
                LogUtils.d("The uploaded file's rev is: " + response.rev);
                LogUtils.d("The uploaded file's path is: " + response.path);
                LogUtils.d("The uploaded file's root is: " + response.root);

            } catch (Exception e) {
                LogUtils.e("NamLH", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            LogUtils.e("upload success");
            listener.onUploadComplete("http://asdasd.com");
            super.onPostExecute(aVoid);
        }

        private String getFileName(String path) {
            String[] parts = path.split("/");
            return parts[parts.length - 1];
        }
    }
}
