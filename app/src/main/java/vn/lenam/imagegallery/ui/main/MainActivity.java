package vn.lenam.imagegallery.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.facebook.Session;
import com.facebook.UiLifecycleHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.ButterKnife;
import vn.lenam.imagegallery.MPOFApp;
import vn.lenam.imagegallery.R;


/**
 * Created by Le Nam on 06-Aug-14.
 */
@Singleton
public class MainActivity extends FragmentActivity {

    UiLifecycleHelper uiHelper;
    @Inject
    Session.StatusCallback sessionStatusCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MPOFApp.get(this).inject(this);

        ButterKnife.inject(this);

        uiHelper = new UiLifecycleHelper(this, sessionStatusCallback);
        uiHelper.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
}
