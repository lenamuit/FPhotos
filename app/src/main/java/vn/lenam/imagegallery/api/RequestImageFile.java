package vn.lenam.imagegallery.api;

import android.os.Environment;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import vn.lenam.imagegallery.data.PrefService;

/**
 * Created by Le Nam on 23-Aug-14.
 */
class RequestImageFile implements RequestApi<String>, Response.ErrorListener {

    private static final String GAL_PATH = Environment.
            getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).
            getAbsolutePath() + "/FPhotos";
    private static final String FILENAME_FORMAT = "FP%06d.jpg";

    @Inject
    RequestQueue requestQueue;

    @Inject
    PrefService prefService;

    private String fileName;
    private OnRequestApiCompleted<String> callback;

    @Inject
    public RequestImageFile() {
        File f = new File(GAL_PATH);
        if (!f.exists()) {
            f.mkdir();

        }
    }

    @Override
    public void request(String path, OnRequestApiCompleted<String> callback) {
        this.callback = callback;
        this.fileName = getFileName(path);
        if (this.fileName != null) {
            if (isFileExists(fileName)) {
                callback.onCompleted(GAL_PATH + "/" + fileName, false);
            } else {
                FileRequest request = new FileRequest(Request.Method.GET, path, this);
                requestQueue.add(request);
            }
        }
    }

    @Override
    public void loadmore() {

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        callback.onCompleted(null, false);
    }

    /**
     * get filename from ul
     *
     * @param url
     * @return
     */
    private String getFileName(String url) {
        int imgCounter = prefService.getInt(PrefService.PrefType.IMG_COUNTER);
        imgCounter++;
        prefService.saveInt(PrefService.PrefType.IMG_COUNTER, imgCounter);
        return String.format(FILENAME_FORMAT, imgCounter);
    }

    private boolean isFileExists(String fileName) {
        File file = new File(GAL_PATH + "/" + fileName);
        return file.exists();
    }


    /**
     * File request
     */
    private final class FileRequest extends Request<String> {

        public FileRequest(int method, String url, Response.ErrorListener listener) {
            super(method, url, listener);
        }


        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            String path = GAL_PATH + "/" + fileName;
            try {
                FileOutputStream stream = new FileOutputStream(path);
                stream.write(response.data);
                stream.flush();
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Response.success(path, null);
        }

        @Override
        protected void deliverResponse(String response) {
            callback.onCompleted(response, false);
        }
    }
}
