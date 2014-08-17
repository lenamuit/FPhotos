package vn.lenam.imagegallery.api;

import android.util.Log;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;

import javax.inject.Inject;

import vn.lenam.imagegallery.api.model.GraphAlbum;
import vn.lenam.imagegallery.data.JsonCache;
import vn.lenam.imagegallery.helper.LogUtils;

/**
 * Created by namlh on 8/9/14.
 */
class RequestAlbumInList implements RequestApi<GraphAlbum> {
    static final String KEY_CACHE = "album_in_list";

    @Inject
    JsonCache cache;

    private OnRequestListCompleted<GraphAlbum> callback;
    private Request request;
    private boolean hasChanged = false;
    private int countLoadmore = 0;

    public static RequestApi<GraphAlbum> getInstance() {
        return new RequestAlbumInList();
    }

    @Override
    public void request(String path, OnRequestListCompleted<GraphAlbum> callback) {
        this.callback = callback;
        Session session = Session.getActiveSession();
        if (session.isOpened()) {
            Request request = Request.newGraphPathRequest(Session.getActiveSession(), path, this);
            Request.executeBatchAsync(request);
        }
    }

    @Override
    public void loadmore() {
        Log.i("loadmore", "hasChanged = " + hasChanged);
        if (request != null && hasChanged) {
            hasChanged = false;
            Request.executeBatchAsync(request);
            countLoadmore++;
        }
    }

    @Override
    public void onCompleted(Response response) {
        GraphObjectList<GraphAlbum> listAlbums;
        GraphObject resObject;

        if (response.getGraphObject() != null) {
            resObject = response.getGraphObject();
            cache.save(KEY_CACHE, resObject.getInnerJSONObject(), countLoadmore);
            LogUtils.e("save-cache:" + countLoadmore);
        } else {
            resObject = GraphObject.Factory.create(cache.get(KEY_CACHE, countLoadmore));
            LogUtils.e("get-cache:" + countLoadmore);
        }
        listAlbums = resObject.getPropertyAsList("data", GraphAlbum.class);
        if (listAlbums != null) {
            callback.onCompleted(listAlbums);
            request = response.getRequestForPagedResults(Response.PagingDirection.NEXT);
            request.setCallback(this);
            hasChanged = true;
        }
    }
}
