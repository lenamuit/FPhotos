package vn.lenam.imagegallery.api;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObjectList;

import javax.inject.Singleton;

import vn.lenam.imagegallery.api.model.GraphPhotoInfo;

/**
 * Created by Le Nam on 08-Aug-14.
 */
@Singleton
public class RequestPhotos implements Request.Callback {

    private OnRequestPhotoCompleted onRequestPhotoCompleted;

    public void request(String path, OnRequestPhotoCompleted callback) {
        Session session = Session.getActiveSession();
        this.onRequestPhotoCompleted = callback;
        if (session.isOpened()) {
            Request request = Request.newGraphPathRequest(Session.getActiveSession(), path, this);
            Request.executeBatchAsync(request);
        }
    }

    @Override
    public void onCompleted(Response response) {
        GraphObjectList<GraphPhotoInfo> listPhoto = response.getGraphObject().getPropertyAsList("data", GraphPhotoInfo.class);
        if (listPhoto != null) {
            onRequestPhotoCompleted.onCompleted(listPhoto);
        }
    }
}
