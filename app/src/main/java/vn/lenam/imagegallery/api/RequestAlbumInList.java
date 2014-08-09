package vn.lenam.imagegallery.api;

import android.util.Log;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObjectList;

import vn.lenam.imagegallery.api.model.GraphAlbum;

/**
 * Created by namlh on 8/9/14.
 */
class RequestAlbumInList implements RequestApi<GraphAlbum> {

    private OnRequestListCompleted<GraphAlbum> callback;
    private Request request;
    private boolean hasChanged = false;

    public static RequestApi<GraphAlbum> getInstance() {
        return new RequestAlbumInList();
    }

    @Override
    public void request(OnRequestListCompleted<GraphAlbum> callback) {
        this.callback = callback;
        Session session = Session.getActiveSession();
        if (session.isOpened()) {
            Request request = Request.newGraphPathRequest(Session.getActiveSession(), "me/albums", this);
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

    @Override
    public void onCompleted(Response response) {
        GraphObjectList<GraphAlbum> listAlbums = response.getGraphObject().getPropertyAsList("data", GraphAlbum.class);
        if (listAlbums != null) {
            callback.onCompleted(listAlbums);
            request = response.getRequestForPagedResults(Response.PagingDirection.NEXT);
            request.setCallback(this);
            hasChanged = true;
        }
    }
}
