package vn.lenam.imagegallery.services;

/**
 * Created by Le Nam on 24-Aug-14.
 */
public interface AuthCompletedListener {
    void onAuthComplete(String token);

    void onAuthError();
}
