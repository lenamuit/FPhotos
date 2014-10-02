package vn.lenam.imagegallery.data.photos;

import vn.lenam.imagegallery.api.model.GraphPhotoInfo;

/**
 * Created by namlh on 9/19/14.
 */
public interface PhotosProvider {
    void onStart(String path);

    void onStop();

    void addListener(PhotosProviderListener listener);

//    List<GraphPhotoInfo> getPage(int page);

    GraphPhotoInfo getPhoto(int pos);

    int getCount();

    int getNumOfPagesRequested();
}
