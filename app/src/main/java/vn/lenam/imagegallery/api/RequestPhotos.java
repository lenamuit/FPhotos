package vn.lenam.imagegallery.api;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObjectList;

import javax.inject.Singleton;

import vn.lenam.imagegallery.api.model.GraphPhotoInfo;
import vn.lenam.imagegallery.ui.main.MainPresenter;

/**
 * Created by Le Nam on 08-Aug-14.
 */
@Singleton
public class RequestPhotos implements Request.Callback {

    private MainPresenter presenter;

    public void request(String path, MainPresenter presenter) {
        Session session = Session.getActiveSession();
        this.presenter = presenter;
        if (session.isOpened()) {
            Request request = Request.newGraphPathRequest(Session.getActiveSession(), "me/photos", this);
            Request.executeBatchAsync(request);
        }
    }

    @Override
    public void onCompleted(Response response) {
        GraphObjectList<GraphPhotoInfo> listPhoto = response.getGraphObject().getPropertyAsList("data", GraphPhotoInfo.class);
        if (listPhoto != null) {
            presenter.onLoadPhotoFinnished(listPhoto);
        }
    }
}
