package vn.lenam.imagegallery.data;

import vn.lenam.imagegallery.api.model.GraphPhotoInfo;

/**
 * Created by Le Nam on 11-Aug-14.
 */
public interface StoreBitmapService {
    String save(GraphPhotoInfo photoInfo);
}
