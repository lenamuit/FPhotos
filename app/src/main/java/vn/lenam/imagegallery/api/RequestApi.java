package vn.lenam.imagegallery.api;

import com.facebook.Request;

/**
 * Created by namlh on 8/9/14.
 */
public interface RequestApi<T> extends Request.Callback {
    void request(String path, OnRequestListCompleted<T> callback);

    void loadmore();
}
