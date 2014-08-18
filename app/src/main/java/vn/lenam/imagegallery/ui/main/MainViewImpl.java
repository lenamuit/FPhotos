package vn.lenam.imagegallery.ui.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.widget.LoginButton;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import vn.lenam.imagegallery.MPOFApp;
import vn.lenam.imagegallery.R;
import vn.lenam.imagegallery.api.model.GraphPhotoInfo;
import vn.lenam.imagegallery.helper.LogUtils;

/**
 * Created by Le Nam on 08-Aug-14.
 */
public class MainViewImpl extends LinearLayout implements MainView, ViewPager.OnPageChangeListener {

    @InjectView(R.id.authButton)
    LoginButton authButton;
    @InjectView(R.id.ln_login)
    View lnLogin;

    @InjectView(R.id.ln_content)
    View lnContent;

    @InjectView(R.id.tv_no_internet)
    View tvNoInternet;

    @Inject
    MainPresenter presenter;


    FragmentManager fragmentManager;
    private ImageViewFragmentAdapter imageFragAdapter;
    private ViewPager viewPager;
    private ProgressDialog progressDialog;

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
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(1);
        imageFragAdapter = new ImageViewFragmentAdapter(fragmentManager);
        viewPager.setAdapter(imageFragAdapter);
        viewPager.setOnPageChangeListener(this);
        presenter.checkLoginStatus(this);
    }

    @Override
    public void hideButtonFacebook() {
        if (lnLogin != null) {
            lnLogin.setVisibility(View.GONE);
            lnContent.setVisibility(VISIBLE);
        }
    }

    @Override
    public void showButtonFacebook() {
        if (lnLogin != null) {
            lnLogin.setVisibility(View.VISIBLE);
            lnContent.setVisibility(GONE);
        }
    }

    @Override
    public void addPhotos(List<GraphPhotoInfo> photos) {
        imageFragAdapter.addPhotos(photos);
    }

    @Override
    public void clearPhotos() {
        viewPager.setCurrentItem(0);
        imageFragAdapter.clear();
    }

    @Override
    public void sharePhoto(String path) {
        hideProgressDialog();
        LogUtils.w("Share path:" + path);
        Uri imageUri = Uri.parse("file://" + path);

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        sharingIntent.setType("image/png");
        getContext().startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.msg_share_image)));
    }

    @Override
    public void savePhotoSuccess() {
        Toast toast = Toast.makeText(getContext(), getResources().getString(R.string.msg_save_gallery), Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void showNoticeNoNetwork() {
        tvNoInternet.setVisibility(VISIBLE);
    }

    @Override
    public void hideNoticeNoNetwork() {
        tvNoInternet.setVisibility(GONE);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position >= imageFragAdapter.getCount() - 5) {
            presenter.onNeedLoadmore();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick(R.id.btn_share)
    void shareBitmap() {
        showProgressDialog();
        int pos = viewPager.getCurrentItem();
        presenter.shareBitmap(imageFragAdapter.getPhoto(pos), this);

    }

    private void showProgressDialog() {
        progressDialog = ProgressDialog.show(getContext(), "Share photo", "Loading...");
        progressDialog.show();
    }

    private void hideProgressDialog() {
        progressDialog.dismiss();
    }
}
