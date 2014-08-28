package vn.lenam.imagegallery.data;

/**
 * Created by namlh on 8/26/14.
 */
public interface PrefService {
    public void saveString(PrefType type, String value);

    public String getString(PrefType type);

    public void saveInt(PrefType type, int value);

    public int getInt(PrefType type);

    public enum PrefType {
        DROPBOX_TOKEN, IMG_COUNTER
    }
}
