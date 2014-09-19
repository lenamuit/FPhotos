package vn.lenam.imagegallery.data.photos;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import vn.lenam.imagegallery.api.OnRequestApiCompleted;
import vn.lenam.imagegallery.api.RequestApi;
import vn.lenam.imagegallery.api.model.GraphPhotoInfo;

/**
 * Created by namlh on 9/19/14.
 */
class PhotosProviderImpl implements PhotosProvider, OnRequestApiCompleted<List<GraphPhotoInfo>> {

    @Inject
    RequestApi<List<GraphPhotoInfo>> requestPhotos;


    private List<GraphPhotoInfo> photos = new ArrayList<GraphPhotoInfo>();
    private List<PhotosProviderListener> listeners = new ArrayList<PhotosProviderListener>();
    private int page;
    private int numPhotosInPage = 0;

    @Override
    public void onStart(String path) {
        page = 0;
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
    public List<GraphPhotoInfo> getPage(int page) {
        if (page > this.page) {
            return null;
        }
        int start = (page - 1) * numPhotosInPage;
        int end = start + numPhotosInPage - 1;
        if (end >= photos.size()) {
            end = photos.size() - 1;
        } else {
            requestPhotos.loadmore();
        }
        return photos.subList(start, end);
    }

    @Override
    public GraphPhotoInfo getPhoto(int pos) {
        if (pos < 0 || pos >= photos.size()) {
            return null;
        }
        return photos.get(pos);
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
        photos.addAll(object);
        for (PhotosProviderListener listener : listeners) {
            listener.onRequestPhotosSuccess(page);
        }
    }
}
