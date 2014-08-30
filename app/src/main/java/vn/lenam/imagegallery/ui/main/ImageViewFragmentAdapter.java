package vn.lenam.imagegallery.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bugsense.trace.BugSenseHandler;

import java.util.ArrayList;
import java.util.List;

import vn.lenam.imagegallery.api.model.GraphPhotoInfo;

/**
 * Created by Le Nam on 07-Aug-14.
 */
class ImageViewFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<Fragment>();

    public ImageViewFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addPhotos(List<GraphPhotoInfo> photos) {
        for (GraphPhotoInfo p : photos) {
            fragmentList.add(ImageViewFragment.getInstance(p));
        }
//        this.photos = photos;
        try {
            notifyDataSetChanged();
        } catch (Exception e) {
            BugSenseHandler.addCrashExtraData("ImageViewFragmentAdapter", "addPhotos");
        }
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
        fragmentList.clear();
        notifyDataSetChanged();
    }
}
