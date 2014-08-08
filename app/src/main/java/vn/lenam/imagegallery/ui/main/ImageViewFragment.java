package vn.lenam.imagegallery.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import vn.lenam.imagegallery.R;

/**
 * Created by Le Nam on 07-Aug-14.
 */
public class ImageViewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.imageview_fragment, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imgView);
        Picasso.with(getActivity()).load("http://i.imgur.com/DvpvklR.png").into(imageView);
        return view;
    }
}
