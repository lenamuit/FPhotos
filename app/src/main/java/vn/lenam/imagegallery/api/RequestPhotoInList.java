package vn.lenam.imagegallery.api;

import android.util.Log;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObjectList;

import vn.lenam.imagegallery.api.model.GraphPhotoInfo;

/**
 * Created by Le Nam on 08-Aug-14.
 */
class RequestPhotoInList implements RequestApi<GraphPhotoInfo> {

    private OnRequestListCompleted<GraphPhotoInfo> onRequestPhotoCompleted;
    private Request request;
    private boolean hasChanged = false;

    public static RequestApi<GraphPhotoInfo> getInstance() {
        return new RequestPhotoInList();
    }

    @Override
    public void onCompleted(Response response) {
        GraphObjectList<GraphPhotoInfo> listPhoto = response.getGraphObject().getPropertyAsList("data", GraphPhotoInfo.class);
        if (listPhoto != null) {
            onRequestPhotoCompleted.onCompleted(listPhoto);
            request = response.getRequestForPagedResults(Response.PagingDirection.NEXT);
            if (request != null) {
                request.setCallback(this);
                hasChanged = true;
            }
        }
    }

    @Override
    public void request(String path, OnRequestListCompleted<GraphPhotoInfo> callback) {
        Session session = Session.getActiveSession();
        this.onRequestPhotoCompleted = callback;
        if (session.isOpened()) {
            request = Request.newGraphPathRequest(Session.getActiveSession(), path, this);
            Request.executeBatchAsync(request);
        }
    }

    @Override
    public void loadmore() {
        Log.i("loadmore", "hasChanged = " + hasChanged);
        if (request != null && hasChanged) {
            hasChanged = false;
            Request.executeBatchAsync(request);
        }
    }
}
