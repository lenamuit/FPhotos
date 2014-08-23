package vn.lenam.imagegallery.api;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;

import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import vn.lenam.imagegallery.api.model.GraphAlbum;
import vn.lenam.imagegallery.data.JsonCache;

/**
 * Created by namlh on 8/9/14.
 */
class RequestAlbumInList implements RequestApi<List<GraphAlbum>> {
    static final String KEY_CACHE = "album_in_list";

    @Inject
    JsonCache cache;

    private OnRequestApiCompleted<List<GraphAlbum>> callback;
    private Request request;
    private boolean canLoadmore = false;
    private int currentPage = 0;

    public static RequestApi<List<GraphAlbum>> getInstance() {
        return new RequestAlbumInList();
    }

    @Override
    public void request(String path, OnRequestApiCompleted<List<GraphAlbum>> callback) {
        this.callback = callback;
        Session session = Session.getActiveSession();
        currentPage = 0;
        if (session.isOpened()) {
            Request request = Request.newGraphPathRequest(Session.getActiveSession(), path, this);
            Request.executeBatchAsync(request);
        }
    }

    @Override
    public void loadmore() {
        if (!canLoadmore) {
            return;
        }
        currentPage++;
        canLoadmore = false;
        //when online
        if (request != null) {
            Request.executeBatchAsync(request);
        }
        //when offline, cache available or not
        else {
            JSONObject jsonObject = cache.get(KEY_CACHE, currentPage);
            if (jsonObject != null) {
                GraphObject object = GraphObject.Factory.create(jsonObject);
                if (object != null && object.getProperty("data") != null) {
                    GraphObjectList<GraphAlbum> listPhoto = object.getPropertyAsList("data", GraphAlbum.class);
                    callback.onCompleted(listPhoto, true);
                    canLoadmore = true;
                }
            }
        }

    }

    @Override
    public void onCompleted(Response response) {
        GraphObjectList<GraphAlbum> listAlbums;
        GraphObject resObject = null;
        boolean isOnline = true;
        if (response.getGraphObject() != null) {
            resObject = response.getGraphObject();
            cache.save(KEY_CACHE, resObject.getInnerJSONObject(), currentPage);
        } else {
            JSONObject jsonObject = cache.get(KEY_CACHE, currentPage);
            if (jsonObject != null) {
                isOnline = false;
                resObject = GraphObject.Factory.create(jsonObject);
            }
        }
        if (resObject != null) {
            listAlbums = resObject.getPropertyAsList("data", GraphAlbum.class);
            boolean fromCache = !isOnline;
            callback.onCompleted(listAlbums, fromCache);
            if (isOnline) {
                request = response.getRequestForPagedResults(Response.PagingDirection.NEXT);
                if (request != null) {
                    request.setCallback(this);
                }
                canLoadmore = true;
            } else {
                request = null;
            }
        } else {
            canLoadmore = false;
        }
    }
}
