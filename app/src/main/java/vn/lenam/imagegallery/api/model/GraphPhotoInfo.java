package vn.lenam.imagegallery.api.model;

import com.facebook.model.GraphPlace;
import com.facebook.model.GraphUser;

import java.util.List;

/**
 * Created by Le Nam on 08-Aug-14.
 */
public interface GraphPhotoInfo extends GraphPhoto {
    String getId();

    GraphUser getFrom();

    String getName();

    String getPicture();

    List<GraphPhoto> getImages();

    String getLink();

    String getCreatedTime();

    GraphPlace getPlace();

}
