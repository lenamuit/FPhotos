package vn.lenam.imagegallery.api.model;

import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;

/**
 * Created by namlh on 8/9/14.
 */
public interface GraphAlbum extends GraphObject {
    String getId();

    GraphUser getFrom();

    String getName();

    int getCount();

    String getType();

}
