package vn.lenam.imagegallery.ui.main;

import dagger.Module;

/**
 * Created by Le Nam on 23-Aug-14.
 */
@Module(
        injects = {MainViewImpl.class,
                ImageViewFragment.class,
                ImageViewFragmentAdapter.class},
        complete = false,
        library = true
)
public class MainModule {


}
