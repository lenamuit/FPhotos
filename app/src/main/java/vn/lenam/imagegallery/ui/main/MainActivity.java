package vn.lenam.imagegallery.ui.main;

import android.os.Bundle;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import vn.lenam.imagegallery.MPOFApp;
import vn.lenam.imagegallery.R;
import vn.lenam.imagegallery.data.photos.PhotosProvider;
import vn.lenam.imagegallery.ui.BaseActivity;
import vn.lenam.imagegallery.ui.album.AlbumsDialog;


/**
 * Created by Le Nam on 06-Aug-14.
 */
public class MainActivity extends BaseActivity {

    @Inject
    PhotosProvider photosProvider;

    @InjectView(R.id.container)
    MainViewImpl container;

    private AlbumsDialog albumsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MPOFApp.get(this).inject(this);
        ButterKnife.inject(this);
    }

    @Override
    public void facebookSessionOpened() {
        photosProvider.onStart("me/photos");
    }

    @OnClick(R.id.btn_albums)
    void showAblumPopup() {
        if (albumsView == null) {
            albumsView = new AlbumsDialog(this);
        }
        albumsView.show();
    }

    @Override
    public void onBackPressed() {
        if (container.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }


}
