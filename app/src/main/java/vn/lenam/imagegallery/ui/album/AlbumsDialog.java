package vn.lenam.imagegallery.ui.album;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import vn.lenam.imagegallery.MPOFApp;
import vn.lenam.imagegallery.R;
import vn.lenam.imagegallery.api.model.GraphAlbum;
import vn.lenam.imagegallery.data.JsonCache;
import vn.lenam.imagegallery.helper.NetworkHelper;
import vn.lenam.imagegallery.helper.ToastUtils;

/**
 * Created by Le Nam on 09-Aug-14.
 */
public class AlbumsDialog implements AlbumsView, AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    @Inject
    AlbumsPresenter albumsPresenter;

    @Inject
    OnAlbumSelected onAlbumSelected;


    @Inject
    JsonCache jsonCache;

    @InjectView(R.id.progress_bar)
    View progressBar;
    @InjectView(R.id.lst_main)
    ListView lstMain;


    private AlbumsAdapter adapter;
    private AlertDialog dialog;
    private Context context;

    public AlbumsDialog(Context context) {
        this.context = context;
        MPOFApp.get(context).inject(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.select_album));
        View view = View.inflate(context, R.layout.popup_albums, null);
        ButterKnife.inject(this, view);
        adapter = new AlbumsAdapter(context, R.layout.item_album);
        lstMain.setAdapter(adapter);
        lstMain.setOnItemClickListener(this);
        lstMain.setOnScrollListener(this);

        builder.setView(view);
        dialog = builder.create();

        albumsPresenter.loadAlbums(this);
    }

    public void show() {
        dialog.show();
    }

    @Override
    public void addAlbums(List<GraphAlbum> albums) {
        progressBar.setVisibility(View.GONE);
        for (GraphAlbum a : albums) {
            adapter.add(a);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GraphAlbum album = adapter.getItem(position);
        String path = album.getId() + "_photos";
        if (album.getCount() > 0) {
            if (NetworkHelper.isConnected(context) || jsonCache.get("photo_in_list" + path, 0) != null) {
                onAlbumSelected.onSelected(adapter.getItem(position));
                dialog.dismiss();
            } else {
                ToastUtils.showToast(context, R.string.msg_no_photos_in_cache);
            }
        } else {
            ToastUtils.showToast(context, R.string.msg_select_album_no_photo);
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (adapter.getCount() > 10 && firstVisibleItem + visibleItemCount > adapter.getCount() - 2) {
            albumsPresenter.onNeedLoadmore();
        }
    }
}
