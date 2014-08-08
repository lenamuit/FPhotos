package vn.lenam.imagegallery.ui.main;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.widget.LoginButton;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import vn.lenam.imagegallery.MPOFApp;
import vn.lenam.imagegallery.R;
import vn.lenam.imagegallery.api.model.GraphPhotoInfo;

/**
 * Created by Le Nam on 08-Aug-14.
 */
public class MainViewImpl extends LinearLayout implements MainView {

    @InjectView(R.id.authButton)
    LoginButton authButton;
    @InjectView(R.id.ln_login)
    View lnLogin;

    @Inject
    MainPresenter presenter;

    FragmentManager fragmentManager;
    private ImageViewFragmentAdapter imageFragAdapter;

    public MainViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
        MPOFApp.get(context).inject(this);
        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.inject(this);
        //TODO need inject permission list
        authButton.setReadPermissions(Arrays.asList("email", "user_friends", "user_photos"));
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(1);
        imageFragAdapter = new ImageViewFragmentAdapter(fragmentManager);
        viewPager.setAdapter(imageFragAdapter);
        presenter.checkLoginStatus(this);
    }

    @Override
    public void hideButtonFacebook() {
        if (lnLogin != null)
            lnLogin.setVisibility(View.GONE);
    }

    @Override
    public void showButtonFacebook() {
        if (lnLogin != null)
            lnLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void addPhotos(List<GraphPhotoInfo> photos) {
        imageFragAdapter.addPhotos(photos);
    }
}
