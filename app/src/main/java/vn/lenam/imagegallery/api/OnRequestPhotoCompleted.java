package vn.lenam.imagegallery.api;

import java.util.List;

import vn.lenam.imagegallery.api.model.GraphPhotoInfo;

/**
 * Created by namlh on 8/9/14.
 */
public interface OnRequestPhotoCompleted {

    void onCompleted(List<GraphPhotoInfo> photos);
}
