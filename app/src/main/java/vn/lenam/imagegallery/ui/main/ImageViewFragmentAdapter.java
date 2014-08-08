package vn.lenam.imagegallery.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Le Nam on 07-Aug-14.
 */
public class ImageViewFragmentAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragmentList = new ArrayList<Fragment>();

    public ImageViewFragmentAdapter(FragmentManager fm) {
        super(fm);
        for (int i = 0; i < 10; i++) {
            fragmentList.add(new ImageViewFragment());
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
