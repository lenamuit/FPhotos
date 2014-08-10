package vn.lenam.imagegallery.ui.album;

import java.util.List;

import javax.inject.Inject;

import vn.lenam.imagegallery.api.OnRequestListCompleted;
import vn.lenam.imagegallery.api.RequestApi;
import vn.lenam.imagegallery.api.model.GraphAlbum;

/**
 * Created by Le Nam on 09-Aug-14.
 */
public class AlbumsPresenterImpl implements AlbumsPresenter, OnRequestListCompleted<GraphAlbum> {

    @Inject
    RequestApi<GraphAlbum> requestAlbums;

    private AlbumsView albumsView;

    @Override
    public void loadAlbums(AlbumsView albumsView) {
        this.albumsView = albumsView;
        requestAlbums.request("me/albums", this);
    }

    @Override
    public void onCompleted(List<GraphAlbum> photos) {
        if (albumsView != null) {
            albumsView.addAlbums(photos);
        }
    }
}
