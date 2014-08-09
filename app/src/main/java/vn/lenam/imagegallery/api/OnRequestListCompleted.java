package vn.lenam.imagegallery.api;

import java.util.List;

/**
 * Created by namlh on 8/9/14.
 */
public interface OnRequestListCompleted<T> {

    void onCompleted(List<T> photos);
}
