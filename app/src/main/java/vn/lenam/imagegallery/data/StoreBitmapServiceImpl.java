package vn.lenam.imagegallery.data;

import android.graphics.Bitmap;
import android.os.Environment;

import com.android.volley.toolbox.ImageLoader;

import java.io.File;
import java.io.FileOutputStream;

import javax.inject.Inject;

import vn.lenam.imagegallery.api.model.GraphPhotoInfo;
import vn.lenam.imagegallery.helper.LogUtils;

/**
 * Created by Le Nam on 11-Aug-14.
 */
public class StoreBitmapServiceImpl implements StoreBitmapService {
    private static final String GAL_PATH = Environment.
            getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).
            getAbsolutePath() + "/FPhotos";
    private static final String GAL_EXT = ".png";

    @Inject
    ImageLoader.ImageCache imageCache;

    @Inject
    public StoreBitmapServiceImpl() {
        File f = new File(GAL_PATH);
        if (!f.exists()) {
            f.mkdir();
        }
    }

    @Override
    public String save(GraphPhotoInfo photoInfo) {
        try {
            String url = photoInfo.getImages().get(1).getSource();
            File file = new File(GAL_PATH, photoInfo.getId() + GAL_EXT);
            FileOutputStream out = new FileOutputStream(file);
            Bitmap bm = imageCache.getBitmap("#W0#H0" + url);
            if (bm == null) {
                return null;
            }
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            LogUtils.e("save image", "error", e);
        }
        return null;
    }

}
