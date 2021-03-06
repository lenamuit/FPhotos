package vn.lenam.imagegallery.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.senab.photoview.PhotoViewAttacher;
import vn.lenam.imagegallery.MPOFApp;
import vn.lenam.imagegallery.R;
import vn.lenam.imagegallery.api.model.GraphPhotoInfo;

/**
 * Created by Le Nam on 07-Aug-14.
 */
class ImageViewFragment extends Fragment {

    private static final String KEY_URL = "key_url";

    @Inject
    ImageLoader imageLoader;

    @InjectView(R.id.imgView)
    ImageView imageView;

    @InjectView(R.id.img_noimage)
    View noImage;

    public static ImageViewFragment getInstance(GraphPhotoInfo url) {
        ImageViewFragment fragment = new ImageViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_URL, url.getSource());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MPOFApp.get(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.imageview_fragment, null);
        ButterKnife.inject(this, view);
        String url = getArguments().getString(KEY_URL);
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                PhotoViewAttacher mAttacher = new PhotoViewAttacher(imageView);
                imageView.setImageBitmap(response.getBitmap());
                mAttacher.update();
                noImage.setVisibility(View.GONE);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                noImage.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
