package vn.lenam.imagegallery.api.downloader;


import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Le Nam on 23-Aug-14.
 */
public class FileDownloader extends Request<String> {

    public FileDownloader(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String path = "";
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

    }
}
