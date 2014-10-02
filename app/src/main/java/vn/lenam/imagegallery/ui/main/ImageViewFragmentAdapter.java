package vn.lenam.imagegallery.ui.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import javax.inject.Inject;

import vn.lenam.imagegallery.MPOFApp;
import vn.lenam.imagegallery.data.photos.PhotosProvider;
import vn.lenam.imagegallery.data.photos.PhotosProviderListener;

/**
 * Created by Le Nam on 07-Aug-14.
 */
class ImageViewFragmentAdapter extends FragmentStatePagerAdapter implements PhotosProviderListener {
    @Inject
    PhotosProvider photosProvider;

    public ImageViewFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        MPOFApp.get(context).inject(this);
        photosProvider.addListener(this);
    }

    @Override
    public Fragment getItem(int position) {
        return ImageViewFragment.getInstance(photosProvider.getPhoto(position));
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return photosProvider.getCount();
    }

    @Override
    public void onRequestPhotosSuccess(int page) {
        notifyDataSetChanged();
    }
}
