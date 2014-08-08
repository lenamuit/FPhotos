package vn.lenam.imagegallery.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import uk.co.senab.photoview.PhotoViewAttacher;
import vn.lenam.imagegallery.MPOFApp;
import vn.lenam.imagegallery.R;
import vn.lenam.imagegallery.api.model.GraphPhotoInfo;

/**
 * Created by Le Nam on 07-Aug-14.
 */
public class ImageViewFragment extends Fragment {

    private static final String KEY_URL = "key_url";
    @Inject
    Picasso picasso;
    private PhotoViewAttacher mAttacher;

    public static ImageViewFragment getInstance(GraphPhotoInfo url) {
        ImageViewFragment fragment = new ImageViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_URL, url.getImages().get(1).getSource());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.imageview_fragment, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imgView);
        String url = getArguments().getString(KEY_URL);

        MPOFApp.get(getActivity()).inject(this);
        mAttacher = new PhotoViewAttacher(imageView);
        picasso.load(url).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                mAttacher.update();
            }

            @Override
            public void onError() {

            }
        });
        return view;
    }
}
