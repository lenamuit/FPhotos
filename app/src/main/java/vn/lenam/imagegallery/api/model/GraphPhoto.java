package vn.lenam.imagegallery.api.model;

import com.facebook.model.GraphObject;

/**
 * Created by Le Nam on 08-Aug-14.
 */
public interface GraphPhoto extends GraphObject {
    int getWidth();

    int getHeight();

    String getSource();

}
