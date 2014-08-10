package vn.lenam.imagegallery.ui.album;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import vn.lenam.imagegallery.MPOFApp;
import vn.lenam.imagegallery.R;
import vn.lenam.imagegallery.api.model.GraphAlbum;

/**
 * Created by Le Nam on 09-Aug-14.
 */
public class AlbumsViewImpl extends AlertDialog implements AlbumsView, AdapterView.OnItemClickListener {

    @Inject
    AlbumsPresenter albumsPresenter;

    @Inject
    OnAlbumSelected onAlbumSelected;

    private AlbumsAdapter adapter;

    public AlbumsViewImpl(Context context) {
        super(context);
        MPOFApp.get(getContext()).inject(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_albums);
        ListView lstView = (ListView) findViewById(R.id.lst_main);
        adapter = new AlbumsAdapter(getContext(), R.layout.item_album);
        lstView.setAdapter(adapter);
        lstView.setOnItemClickListener(this);

        albumsPresenter.loadAlbums(this);
    }

    @Override
    public void addAlbums(List<GraphAlbum> albums) {
        for (GraphAlbum a : albums) {
            adapter.add(a);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        onAlbumSelected.onSelected(adapter.getItem(position));
        dismiss();
    }
}
