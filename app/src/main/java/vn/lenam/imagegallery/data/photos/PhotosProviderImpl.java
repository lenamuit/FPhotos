package vn.lenam.imagegallery.data.photos;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import vn.lenam.imagegallery.api.OnRequestApiCompleted;
import vn.lenam.imagegallery.api.RequestApi;
import vn.lenam.imagegallery.api.model.GraphPhotoInfo;
import vn.lenam.imagegallery.helper.LogUtils;

/**
 * Created by namlh on 9/19/14.
 */
class PhotosProviderImpl implements PhotosProvider, OnRequestApiCompleted<List<GraphPhotoInfo>> {

    @Inject
    RequestApi<List<GraphPhotoInfo>> requestPhotos;


    private List<GraphPhotoInfo> photos = new ArrayList<GraphPhotoInfo>();
    private List<PhotosProviderListener> listeners = new ArrayList<PhotosProviderListener>();
    private int page =-1;
    private int numPhotosInPage = 0;

    @Override
    public void onStart(String path) {
        LogUtils.w("getPage onstart path = "+path);
        photos.clear();
        requestPhotos.request(path, this);
    }

    @Override
    public void onStop() {
        //TODO handle on stop
    }

    @Override
    public void addListener(PhotosProviderListener listener) {
        listeners.add(listener);
    }


    @Override
    public GraphPhotoInfo getPhoto(int pos) {
        if (pos < 0 || pos >= photos.size()) {
            return null;
        }
        if (pos == photos.size() - 5) {
            requestPhotos.loadmore();
        }
        return photos.get(pos);
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public int getNumOfPagesRequested() {
        return page;
    }

    @Override
    public void onCompleted(List<GraphPhotoInfo> object, boolean fromCache) {
        page++;
        if (numPhotosInPage == 0) {
            numPhotosInPage = object.size();
        }
        LogUtils.w("getPage add photos "+page);
        photos.addAll(object);
        for (PhotosProviderListener listener : listeners) {
            listener.onRequestPhotosSuccess(page);
        }
    }
}
