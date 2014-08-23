package vn.lenam.imagegallery.api;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;

import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import vn.lenam.imagegallery.api.model.GraphPhotoInfo;
import vn.lenam.imagegallery.data.JsonCache;

/**
 * Created by Le Nam on 08-Aug-14.
 */
class RequestPhotoInList implements RequestApi<List<GraphPhotoInfo>>, Request.Callback {
    static final String KEY_CACHE = "photo_in_list";
    @Inject
    JsonCache cache;

    private int currentPage = 0;
    private boolean canLoadMore = true;
    private String keyCache;

    private OnRequestApiCompleted<List<GraphPhotoInfo>> onRequestPhotoCompleted;
    private Request request;

    @Override
    public void onCompleted(Response response) {
        GraphObject object = null;
        boolean isOnline = true;
        if (response.getGraphObject() != null) {
            object = response.getGraphObject();
            cache.save(keyCache, object.getInnerJSONObject(), currentPage);
        } else {
            isOnline = false;
            JSONObject jsonObject = cache.get(keyCache, currentPage);
            if (jsonObject != null) {
                object = GraphObject.Factory.create(jsonObject);
            }
        }
        //data available
        if (object != null) {
            GraphObjectList<GraphPhotoInfo> listPhoto = object.getPropertyAsList("data", GraphPhotoInfo.class);
            boolean fromCache = !isOnline;
            onRequestPhotoCompleted.onCompleted(listPhoto, fromCache);
            //if online >> request for new page
            if (isOnline) {
                request = response.getRequestForPagedResults(Response.PagingDirection.NEXT);
                if (request != null) {
                    request.setCallback(this);
                }
                canLoadMore = true;
            } else {
                request = null;
            }
        } else {
            canLoadMore = false;
        }

    }

    @Override
    public void request(String path, OnRequestApiCompleted<List<GraphPhotoInfo>> callback) {
        this.keyCache = KEY_CACHE + path.replace("/", "_");
        Session session = Session.getActiveSession();
        this.onRequestPhotoCompleted = callback;
        currentPage = 0;
        if (session.isOpened()) {
            request = Request.newGraphPathRequest(Session.getActiveSession(), path, this);
            Request.executeBatchAsync(request);
        }
    }

    @Override
    public void loadmore() {
        if (!canLoadMore) {
            return;
        }
        currentPage++;
        canLoadMore = false;
        //when online
        if (request != null) {
            Request.executeBatchAsync(request);
        }
        //when offline, cache available or not
        else {
            JSONObject jsonObject = cache.get(keyCache, currentPage);
            if (jsonObject != null) {
                GraphObject object = GraphObject.Factory.create(jsonObject);
                if (object != null && object.getProperty("data") != null) {
                    GraphObjectList<GraphPhotoInfo> listPhoto = object.getPropertyAsList("data", GraphPhotoInfo.class);
                    onRequestPhotoCompleted.onCompleted(listPhoto, true);
                    canLoadMore = true;
                }
            }
        }
    }
}
