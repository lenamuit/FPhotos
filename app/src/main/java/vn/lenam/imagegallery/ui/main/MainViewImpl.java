package vn.lenam.imagegallery.ui.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

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
import vn.lenam.imagegallery.helper.DateTimeHelper;
import vn.lenam.imagegallery.ui.share.ShareHandler;

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

    @InjectView(R.id.btn_info)
    View btnInfo;

    @InjectView(R.id.tv_message)
    TextView tvMessage;

    @Inject
    MainPresenter presenter;
    @Inject
    ShareHandler shareHandler;

    FragmentManager fragmentManager;
    private PhotoInfoPopupWindow photoInfoPopupWindow;
    private ImageViewFragmentAdapter imageFragAdapter;
    private ViewPager viewPager;
    private ProgressDialog progressDialog;

    public MainViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
        MPOFApp.get(context).inject(this);
        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        photoInfoPopupWindow = new PhotoInfoPopupWindow(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.inject(this);
        //TODO need inject permission list
        authButton.setReadPermissions(Arrays.asList("email", "user_photos"));
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(1);
        imageFragAdapter = new ImageViewFragmentAdapter(fragmentManager);
        viewPager.setAdapter(imageFragAdapter);
        viewPager.setOnPageChangeListener(this);
        presenter.checkLoginStatus(this);
        tvMessage.setText(getContext().getString(R.string.loading_photo_you_are_tagged_in));
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
        if (photos.size() == 0 && imageFragAdapter.getCount() == 0) {
            tvMessage.setVisibility(VISIBLE);
            tvMessage.setText(getContext().getString(R.string.no_photo_found));
        } else {
            tvMessage.setVisibility(GONE);
            imageFragAdapter.addPhotos(photos);
        }

    }

    @Override
    public void clearPhotos() {
        viewPager.setCurrentItem(0);
        imageFragAdapter.clear();
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
        int pos = viewPager.getCurrentItem();
        shareHandler.startShareSns(getContext(), imageFragAdapter.getPhoto(pos));

    }

    @OnClick(R.id.btn_info)
    void showInfo() {
        if (imageFragAdapter.getCount() > 0) {
            photoInfoPopupWindow.show(btnInfo, imageFragAdapter.getPhoto(viewPager.getCurrentItem()));
        }
    }

    @OnClick(R.id.btn_save)
    void saveBitmap() {
        int pos = viewPager.getCurrentItem();
        shareHandler.startSaveGallery(getContext(), imageFragAdapter.getPhoto(pos));
    }

    public boolean onBackPressed() {
        return photoInfoPopupWindow.dismiss();
    }

    private void hideProgressDialog() {
        progressDialog.dismiss();
    }

    /**
     * Class for show popup detail photo
     */
    static class PhotoInfoPopupWindow {

        private final PopupWindow popupWindow;
        private final View popupView;
        @InjectView(R.id.tv_uploaded_by)
        TextView tvUploaded;
        @InjectView(R.id.tv_at_time)
        TextView tvAtTime;
        @InjectView(R.id.tv_place)
        TextView tvPlace;
        private Context context;

        private View achor;

        public PhotoInfoPopupWindow(Context context) {
            this.context = context;
            popupView = View.inflate(context, R.layout.popup_photo_info, null);
            popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable());
            popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        return dismiss();
                    }
                    return false;
                }
            });
            ButterKnife.inject(this, popupView);
        }

        public void show(View view, GraphPhotoInfo photo) {
            tvUploaded.setText(photo.getFrom().getName());
            String date = DateTimeHelper.getStringDate(context, DateTimeHelper.getDateFromISO8601(photo.getCreatedTime()));
            tvAtTime.setText(date);
            if (photo.getPlace() != null) {
                tvPlace.setText(photo.getPlace().getName());
            } else {
                tvPlace.setText("Unknown");
            }
            achor = view;
            popupWindow.showAsDropDown(view, 0, -context.getResources().getDimensionPixelOffset(R.dimen.photo_popup_height));
            achor.setVisibility(GONE);
        }

        public boolean dismiss() {
            if (popupWindow.isShowing()) {
                if (achor != null) {
                    achor.setVisibility(VISIBLE);
                }
                popupWindow.dismiss();
                return true;
            }
            return false;
        }
    }
}
