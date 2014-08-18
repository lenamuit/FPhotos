package vn.lenam.imagegallery.ui.album;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
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
public class AlbumsDialog implements AlbumsView, AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    @Inject
    AlbumsPresenter albumsPresenter;

    @Inject
    OnAlbumSelected onAlbumSelected;

    private AlbumsAdapter adapter;
    private AlertDialog dialog;

    public AlbumsDialog(Context context) {
        MPOFApp.get(context).inject(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select album...");
        View view = View.inflate(context, R.layout.popup_albums, null);

        ListView lstView = (ListView) view.findViewById(R.id.lst_main);
        adapter = new AlbumsAdapter(context, R.layout.item_album);
        lstView.setAdapter(adapter);
        lstView.setOnItemClickListener(this);
        lstView.setOnScrollListener(this);

        builder.setView(view);
        dialog = builder.create();

        albumsPresenter.loadAlbums(this);
    }

    public void show() {
        dialog.show();
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
        dialog.dismiss();
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount > adapter.getCount() - 2) {
            albumsPresenter.onNeedLoadmore();
        }
    }
}
