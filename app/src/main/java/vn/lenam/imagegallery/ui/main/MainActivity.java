package vn.lenam.imagegallery.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import dagger.ObjectGraph;
import vn.lenam.imagegallery.MPOFApp;
import vn.lenam.imagegallery.R;
import vn.lenam.imagegallery.ui.UiModule;


/**
 * Created by Le Nam on 06-Aug-14.
 */
public class MainActivity extends Activity implements MainView {

    @Inject
    UiLifecycleHelper uiHelper;

    @InjectView(R.id.authButton)
    LoginButton authButton;
    @InjectView(R.id.ln_login)
    View lnLogin;

    private ObjectGraph objectGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Config graph
        objectGraph = MPOFApp.get(this).createScopedGraph(new UiModule(this));
        objectGraph.inject(this);

        ButterKnife.inject(this);
        uiHelper.onCreate(savedInstanceState);
        //TODO need inject permission list
        authButton.setReadPermissions(Arrays.asList("email", "user_friends", "user_photos"));

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
}
