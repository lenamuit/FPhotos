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

/**
 * Created by Le Nam on 23-Aug-14.
 */
class RequestImageFile implements RequestApi<String>, Response.ErrorListener {

    private static final String GAL_PATH = Environment.
            getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).
            getAbsolutePath() + "/My Photos on Facebook";
    @Inject
    RequestQueue requestQueue;
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
            FileRequest request = new FileRequest(Request.Method.GET, path, this);
            requestQueue.add(request);
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
        String[] urls = url.split("/");
        String name = urls[urls.length - 1];
        if (name.contains(".jpg")) {
            int index = name.indexOf('?');
            if (index >= 0) {
                return name.substring(0, index);
            }
            return name;
        }
        return null;
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
