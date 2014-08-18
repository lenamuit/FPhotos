package vn.lenam.imagegallery.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import vn.lenam.imagegallery.api.model.GraphPhotoInfo;

/**
 * Created by Le Nam on 07-Aug-14.
 */
public class ImageViewFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<Fragment>();

    private List<GraphPhotoInfo> photos;

    public ImageViewFragmentAdapter(FragmentManager fm) {
        super(fm);
        photos = new ArrayList<GraphPhotoInfo>();
    }

    public void addPhotos(List<GraphPhotoInfo> photos) {
        for (GraphPhotoInfo p : photos) {
            fragmentList.add(ImageViewFragment.getInstance(p));
            this.photos.add(p);
        }
//        this.photos = photos;
        notifyDataSetChanged();
    }

    public GraphPhotoInfo getPhoto(int pos) {
        if (photos != null && photos.size() > pos) {
            return photos.get(pos);
        }
        return null;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void clear() {
        photos.clear();
        fragmentList.clear();
        notifyDataSetChanged();
    }
}
