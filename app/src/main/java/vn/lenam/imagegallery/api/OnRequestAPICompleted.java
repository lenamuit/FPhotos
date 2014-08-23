package vn.lenam.imagegallery.api;

/**
 * Created by namlh on 8/9/14.
 */
public interface OnRequestApiCompleted<T> {

    void onCompleted(T photos, boolean fromCache);
}
