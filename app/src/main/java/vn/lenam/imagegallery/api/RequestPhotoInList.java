package vn.lenam.imagegallery.api;

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

    public static RequestApi<GraphPhotoInfo> getInstance() {
        return new RequestPhotoInList();
    }

    @Override
    public void onCompleted(Response response) {
        GraphObjectList<GraphPhotoInfo> listPhoto = response.getGraphObject().getPropertyAsList("data", GraphPhotoInfo.class);
        if (listPhoto != null) {
            onRequestPhotoCompleted.onCompleted(listPhoto);
        }
    }

    @Override
    public void request(OnRequestListCompleted<GraphPhotoInfo> callback) {
        Session session = Session.getActiveSession();
        this.onRequestPhotoCompleted = callback;
        if (session.isOpened()) {
            Request request = Request.newGraphPathRequest(Session.getActiveSession(), "me/photos", this);
            Request.executeBatchAsync(request);
        }
    }

    @Override
    public void loadmore() {

    }
}
