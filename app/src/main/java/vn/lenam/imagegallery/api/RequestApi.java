package vn.lenam.imagegallery.api;

/**
 * Created by namlh on 8/9/14.
 */
public interface RequestApi<T> {
    void request(String path, OnRequestApiCompleted<T> callback);

    void loadmore();
}
