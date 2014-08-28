package vn.lenam.imagegallery.data;

/**
 * Created by namlh on 8/26/14.
 */
public interface PrefService {
    public void saveString(PrefKey type, String value);

    public String getString(PrefKey type);

    public void saveInt(PrefKey type, int value);

    public int getInt(PrefKey type);

    public enum PrefKey {
        DROPBOX_TOKEN, IMG_COUNTER
    }
}
