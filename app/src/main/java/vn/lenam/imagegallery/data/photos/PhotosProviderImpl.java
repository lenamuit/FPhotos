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
        LogUtils.w("getPage = "+page);
        if (page > this.page) {
            return null;
        }
        int start = page * numPhotosInPage;
        int end = start + numPhotosInPage - 1;
        if (end >= photos.size()) {
            end = photos.size() - 1;
        } else if (page == this.page){
            requestPhotos.loadmore();
        }
        LogUtils.w("getPage from "+start + " -> end: "+end + " photos length="+photos.size());
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
        LogUtils.w("getPage add photos "+page);
        photos.addAll(object);
        for (PhotosProviderListener listener : listeners) {
            listener.onRequestPhotosSuccess(page);
        }

    }
}
